package day16

import kotlin.math.max
import kotlin.math.min

data class Point(var x: Int, var y: Int)

fun main() {
    println("Part 1:")
    for (i in inputs1.indices) {
        val sample = inputs1[i]

        val board = parsePt1(sample.input)
//        println("board: $board")
        val sol = calcPt1(board)
        println("calced: $sol, Answer: ${sample.answer}, Correct: ${sample.answer == sol}")
    }

    val board1Mine = parsePt1(inputMine)
    println("Mine: ${calcPt1(board1Mine)}")

    println()
    println("Part 2:")

    for (i in inputs2.indices) {
        val sample = inputs2[i]

        val board = parsePt2(sample.input)
//        println("board: $board")
        val sol = calcPt2(board)
        println("calced: $sol, Answer: ${sample.answer}, Correct: ${sample.answer == sol}")
    }

    val board2Mine = parsePt2(inputMine)
    println("Mine: ${calcPt2(board2Mine)}")
}

data class Beam (val pt: Point, val dir: Dir)

fun calcPt1(inp: Board): Long {
    return calc(inp, Beam(Point(-1,0), Dir.Right))
}

fun calc(inp: Board, initialBeam: Beam): Long {
    val energized = inp.c.map { r -> r.map { c -> HashSet<Dir>() }.toMutableList()}.toMutableList()
    val ptCheck = listOf(initialBeam).toMutableList()
    while (ptCheck.isNotEmpty()) {
        val pt = ptCheck.removeAt(0)

        if (pt.pt.x >= 0 && pt.pt.x <= inp.c[0].size-1 && pt.pt.y >= 0 && pt.pt.y <= inp.c.size-1) {
            if (energized[pt.pt.y][pt.pt.x].contains(pt.dir)) {
                continue
            }
            energized[pt.pt.y][pt.pt.x].add(pt.dir)
        }

        var ptNew: Point
        when (pt.dir) {
            Dir.Right -> ptNew = Point(pt.pt.x + 1, pt.pt.y)
            Dir.Left -> ptNew = Point(pt.pt.x - 1, pt.pt.y)
            Dir.Up -> ptNew = Point(pt.pt.x, pt.pt.y - 1)
            Dir.Down -> ptNew = Point(pt.pt.x, pt.pt.y + 1)
        }
        if (ptNew.x < 0 || ptNew.y < 0 || ptNew.x > inp.c[0].size-1 || ptNew.y > inp.c.size-1) {
            continue
        }
        val cell = inp.c[ptNew.y][ptNew.x]
        when (cell) {
            Cell.Empty -> {
                ptCheck.add(Beam(ptNew, pt.dir))
            }
            Cell.MirrorNWSE -> {
                val newDir = when (pt.dir) {
                    Dir.Right -> Dir.Down
                    Dir.Up -> Dir.Left
                    Dir.Down -> Dir.Right
                    Dir.Left -> Dir.Up
                }
                ptCheck.add(Beam(ptNew, newDir))
            }
            Cell.MirrorNESW -> {
                val newDir = when (pt.dir) {
                    Dir.Right -> Dir.Up
                    Dir.Up -> Dir.Right
                    Dir.Down -> Dir.Left
                    Dir.Left -> Dir.Down
                }
                ptCheck.add(Beam(ptNew, newDir))
            }
            Cell.SplitterNS -> {
                when (pt.dir) {
                    Dir.Up, Dir.Down -> {
                        ptCheck.add(Beam(ptNew, pt.dir))
                    }
                    Dir.Left, Dir.Right -> {
                        ptCheck.add(Beam(ptNew, Dir.Up))
                        ptCheck.add(Beam(ptNew, Dir.Down))
                    }
                }
            }
            Cell.SplitterEW -> {
                when (pt.dir) {
                    Dir.Left, Dir.Right -> {
                        ptCheck.add(Beam(ptNew, pt.dir))
                    }
                    Dir.Up, Dir.Down -> {
                        ptCheck.add(Beam(ptNew, Dir.Left))
                        ptCheck.add(Beam(ptNew, Dir.Right))
                    }
                }
            }
        }
    }
    return energized.map { r -> r.filter { c -> c.isNotEmpty() }.size}.sum().toLong()
}

fun calcPt2(inp: Board): Long {
    val ns = (0..<inp.c.size).map { y -> listOf(
        calc(inp, Beam(Point(-1,y), Dir.Right)),
        calc(inp, Beam(Point(inp.c[0].size,y), Dir.Left)),
    )}
    val ew = (0..<inp.c[0].size).map { x -> listOf(
        calc(inp, Beam(Point(x,-1), Dir.Down)),
        calc(inp, Beam(Point(x, inp.c.size), Dir.Up)),
    )}

    return max(ns.flatten().max(), ew.flatten().max())
}

enum class Cell {
    Empty,
    MirrorNESW,
    MirrorNWSE,
    SplitterNS,
    SplitterEW,
}

enum class Dir {
    Up,
    Right,
    Down,
    Left,
}

data class Board(val c: List<List<Cell>>)

fun parsePt1(inp: String): Board {
    return Board(inp.split("\n").map { r -> r.map { c ->
        when(c) {
            '.' -> Cell.Empty
            '|' -> Cell.SplitterNS
            '-' -> Cell.SplitterEW
            '\\' -> Cell.MirrorNWSE
            '/' -> Cell.MirrorNESW
            else -> throw Exception()
        }
    }})
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
