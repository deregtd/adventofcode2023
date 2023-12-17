package day17

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

fun calcPt1(inp: Board): Long {
    return calc(inp, 0, 3)
}

fun calcPt2(inp: Board): Long {
    return calc(inp, 4, 10)
}

data class PtState(val pt: Point, val dir: Dir, val dirCount: Int)

data class Run(val pt: Point, val dir: Dir, val dirCount: Int = 0, val score: Int = 0)

fun calc(inp: Board, minMovesBeforeTurn: Int, maxMovesBeforeTurn: Int): Long {
    val toRun = ArrayList<Run>()
    toRun.add(Run(Point(0,0), Dir.Right))
    toRun.add(Run(Point(0,0), Dir.Down))

    val bestStates = HashMap<PtState, Int>()
    var bestFinish = Int.MAX_VALUE

    while (toRun.isNotEmpty()) {
        val run = toRun.removeLast()

        if (run.score >= bestFinish) {
            continue
        }

        if (run.dirCount >= minMovesBeforeTurn && run.pt.x == inp.c[0].size - 1 && run.pt.y == inp.c.size - 1) {
            bestFinish = run.score
            println("Finish: ${run}")
            continue
        }

        val validDirs = run.dir.dirsWithin90().filter { d ->
            (d == run.dir && run.dirCount < maxMovesBeforeTurn) || (d != run.dir && run.dirCount >= minMovesBeforeTurn)
        }
        for (vd in validDirs) {
            val newPt = vd.continuePoint(run.pt)
            if (newPt.x >= 0 && newPt.x < inp.c[0].size && newPt.y >= 0 && newPt.y < inp.c.size) {
                val newCount = if (run.dir == vd) { run.dirCount + 1 } else { 1 }
                val newScore = run.score + inp.c[newPt.y][newPt.x]

                val curState = bestStates.get(PtState(newPt, vd, newCount))
                if (curState != null) {
                    if (curState <= newScore) {
                        continue
                    }
                }
                val newRun = Run(newPt, vd, newCount, newScore)
                bestStates.set(PtState(newRun.pt, newRun.dir, newRun.dirCount), newRun.score)
                toRun.add(newRun)
            }
        }
    }

    return bestFinish.toLong()
}

enum class Dir {
    Up {
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Left, Dir.Up, Dir.Right) }
        override fun continuePoint(pt: Point): Point { return Point(pt.x, pt.y-1) }
    },
    Right {
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Up, Dir.Right, Dir.Down) }
        override fun continuePoint(pt: Point): Point { return Point(pt.x + 1, pt.y) }
    },
    Down {
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Right, Dir.Down, Dir.Left) }
        override fun continuePoint(pt: Point): Point { return Point(pt.x, pt.y+1) }
    },
    Left {
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Down, Dir.Left, Dir.Up) }
        override fun continuePoint(pt: Point): Point { return Point(pt.x-1, pt.y) }
    };

    abstract fun continuePoint(pt: Point): Point
    abstract fun dirsWithin90(): List<Dir>
}

data class Board(val c: List<List<Int>>)

fun parsePt1(inp: String): Board {
    return Board(inp.split("\n").map { r -> r.map { c -> c - '0' } })
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
