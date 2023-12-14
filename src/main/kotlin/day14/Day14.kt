package day14

import kotlin.math.max
import kotlin.math.min

data class Point(val x: Int, val y: Int)

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

fun calcPt1(inp: Board): Long {
    val b = rollUntilStops(inp,Point(0,-1))
    return calcLoad(b)
}

fun rollUntilStops(inp: Board, dir: Point): Board {
    var b = inp
    while (true) {
        val b2 = roll(b, dir)
        if (b == b2) {
            return b2
        }
        b = b2
    }
}

fun calcLoad(inp: Board): Long {
    var weight = 0L
    for (y in 0..<inp.c.size) {
        for (x in 0..<inp.c[0].size) {
            if (inp.c[y][x] == Cell.RoundRock) {
                weight += inp.c.size - y
            }
        }
    }
    return weight
}

fun roll(inp: Board, dir: Point): Board {
    val nm = inp.c.map { r -> r.map { c -> c }.toMutableList()}.toMutableList()

    if (dir.y < 0) {
        for (y in 1..<inp.c.size) {
            for (x in 0..<inp.c[0].size) {
                if (nm[y][x] == Cell.RoundRock) {
                    if (nm[y-1][x] == Cell.Empty) {
                        nm[y-1][x] = Cell.RoundRock
                        nm[y][x] = Cell.Empty
                    }
                }
            }
        }
    } else if (dir.y > 0) {
        for (y in inp.c.size-2 downTo 0) {
            for (x in 0..<inp.c[0].size) {
                if (nm[y][x] == Cell.RoundRock) {
                    if (nm[y+1][x] == Cell.Empty) {
                        nm[y+1][x] = Cell.RoundRock
                        nm[y][x] = Cell.Empty
                    }
                }
            }
        }
    } else if (dir.x < 0) {
        for (y in 0..<inp.c.size) {
            for (x in 1..<inp.c[0].size) {
                if (nm[y][x] == Cell.RoundRock) {
                    if (nm[y][x-1] == Cell.Empty) {
                        nm[y][x-1] = Cell.RoundRock
                        nm[y][x] = Cell.Empty
                    }
                }
            }
        }
    } else if (dir.x > 0) {
        for (y in 0..<inp.c.size) {
            for (x in inp.c[0].size-2 downTo 0) {
                if (nm[y][x] == Cell.RoundRock) {
                    if (nm[y][x+1] == Cell.Empty) {
                        nm[y][x+1] = Cell.RoundRock
                        nm[y][x] = Cell.Empty
                    }
                }
            }
        }
    } else {
        throw Exception("nodir")
    }

    return Board(nm)
}

fun calcPt2(inp: Board): Long {
    val lu = mutableMapOf<Board, List<Int>>()
    var boards = ArrayList<Board>()

    var b = inp
    boards.add(b)
ml@ for (i in 1..1000000000) {
        b = doCycle(b)
        boards.add(b)

        val k = lu.get(b)
        if (k == null) {
            lu.set(b, listOf(i))
        } else {
            val ns = ArrayList<Int>(k)
            ns.add(i)
            lu.set(b, ns)

            // look for cycle
            val diff = ns[ns.size-1] - ns[ns.size-2]
            for (h in 1..<diff) {
                if (boards[i-h] != boards[i-diff-h]) {
                    continue@ml
                }
            }

            // found it!
            val toRun = 1000000000 - i
            val cyclePart = toRun % diff
            println("Found cycle at $i, cycle length is $diff")
            val fb = boards[i-diff+cyclePart]
            return calcLoad(fb)
        }
    }

    return calcLoad(b)
}

fun doCycle(inp: Board): Board {
    var o = rollUntilStops(inp, Point(0, -1))
    o = rollUntilStops(o, Point(-1, 0))
    o = rollUntilStops(o, Point(0, 1))
    o = rollUntilStops(o, Point(1, 0))
    return o
}

enum class Cell {
    RoundRock,
    CubeRock,
    Empty,
}

data class Board(val c: List<List<Cell>>)

fun parsePt1(inp: String): Board {
    val imgs = inp.split("\n").map{ b -> b.map { v ->
        when (v) {
            'O' -> Cell.RoundRock
            '#' -> Cell.CubeRock
            '.' -> Cell.Empty
            else -> throw Exception()
        }
    } }

    return Board(imgs)
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
