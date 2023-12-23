package day21

import common.Cycle
import common.Point2
import common.findCycle
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

fun main() {
    println("Part 1:")
    for (i in inputs1.indices) {
        val sample = inputs1[i]

        val board = parsePt1(sample.input)
//        println("board: $board")
        val sol = calcPt1(board, sample.steps)
        println("calced: $sol, Answer: ${sample.answer}, Correct: ${sample.answer == sol}")
    }

    val board1Mine = parsePt1(inputMine)
    println("Mine: ${calcPt1(board1Mine, 64)}")

    println()
    println("Part 2:")

    for (i in inputs2.indices) {
        val sample = inputs2[i]

        val board = parsePt2(sample.input)
//        println("board: $board")
        val sol = calcPt2(board, sample.steps)
        println("calced: $sol, Answer: ${sample.answer}, Correct: ${sample.answer == sol}")
    }

    val board2Mine = parsePt2(inputMine)
    println("Mine: ${calcPt2(board2Mine, 26501365)}")
}

fun calcPt1(inp: Board, steps: Int): Long {
    var set = hashSetOf<Point2>(inp.start)
    for (i in 1..steps) {
        val nextStep = HashSet<Point2>()
        for (pt in set) {
            val ptc = listOf(Point2(pt.x-1, pt.y), Point2(pt.x+1, pt.y), Point2(pt.x, pt.y-1), Point2(pt.x, pt.y+1))
            for (ptn in ptc) {
                if (ptn.x < 0 || ptn.x >= inp.b[0].size || ptn.y < 0 || ptn.y >= inp.b.size || inp.b[ptn.y][ptn.x]) {
                    continue
                }
                nextStep.add(ptn)
            }
        }
        set = nextStep
    }
    return set.size.toLong()
}

class PredictiveBoard(val gridSize: Int, val firstNums: List<Int>, val preCycles: List<List<Int>>, val cycles: List<Cycle>) {
    private val halfGrid = gridSize/2

    private val tlCorn = firstNums[(-2 + halfGrid) * gridSize + (-2 + halfGrid)]
    private val leftStep = tlCorn - firstNums[(-2 + halfGrid) * gridSize + (-1 + halfGrid)]
    private val upStep = tlCorn - firstNums[(-1 + halfGrid) * gridSize + (-2 + halfGrid)]

    private val brCorn = firstNums[(2 + halfGrid) * gridSize + (2 + halfGrid)]
    private val rightStep = brCorn - firstNums[(2 + halfGrid) * gridSize + (1 + halfGrid)]
    private val downStep = brCorn - firstNums[(1 + halfGrid) * gridSize + (2 + halfGrid)]

    init {
        println(firstNums.chunked(gridSize).map { r -> r.joinToString("\t")}.joinToString("\n"))
        println(preCycles.chunked(gridSize).map { r -> r.joinToString("\t")}.joinToString("\n"))
        println(cycles.chunked(gridSize).map { r -> r.joinToString("\t")}.joinToString("\n"))

        println("left: $leftStep, up: $upStep, right: $rightStep, down: $downStep")
    }

    private fun getCycleForBoard(board: Point2): Cycle {
        val basePt = Point2(max(-3,min(3, board.x)), max(-3,min(3, board.y)))
        return cycles[(basePt.y+halfGrid) * gridSize + (basePt.x+halfGrid)]
    }

    private fun getPreCycleForBoard(board: Point2): List<Int> {
        val basePt = Point2(max(-3,min(3, board.x)), max(-3,min(3, board.y)))
        return preCycles[(basePt.y+halfGrid) * gridSize + (basePt.x+halfGrid)]
    }

    private fun getFirstNumForBoard(board: Point2): Int {
        if (board.x in -2..2 && board.y in -2..2) {
            return firstNums[(board.y+halfGrid) * gridSize + (board.x+halfGrid)]
        }

        val basePt = Point2(max(-2,min(2, board.x)), max(-2,min(2, board.y)))
        var base = getFirstNumForBoard(basePt)

        if (board.x > basePt.x) {
            base += rightStep*(board.x - basePt.x)
        } else if (board.x < basePt.x) {
            base += leftStep*(basePt.x - board.x)
        }

        if (board.y > basePt.y) {
            base += downStep*(board.y - basePt.y)
        } else if (board.y < basePt.y) {
            base += upStep*(basePt.y - board.y)
        }

        return base
    }

    fun getCountForBoard(board: Point2, steps: Int): Int {
        val firstNum = getFirstNumForBoard(board)
        if (steps < firstNum) {
            return 0
        }
        val cycle = getCycleForBoard(board)
        if (steps < firstNum + cycle.firstIndex) {
            val preCycle = getPreCycleForBoard(board)
            return preCycle[steps-firstNum]
        }
        return cycle.vals[(steps - firstNum - cycle.firstIndex) % cycle.vals.size]
    }

    // Min X/Y, Max X/Y
    fun getBoardExtent(steps: Int): Pair<Point2, Point2> {
        val tl = Point2(-3,-3)

        val tBase = getFirstNumForBoard(Point2(0,-3))
        if (steps >= tBase) {
            val boards = ceil((steps - tBase).toFloat() / upStep).toInt()
            tl.y -= boards
        }
        val lBase = getFirstNumForBoard(Point2(-3,0))
        if (steps >= lBase) {
            val boards = ceil((steps - lBase).toFloat() / leftStep).toInt()
            tl.x -= boards
        }

        val br = Point2(3,3)

        val bBase = getFirstNumForBoard(Point2(0,3))
        if (steps >= bBase) {
            val boards = ceil((steps - bBase).toFloat() / downStep).toInt()
            br.y += boards
        }
        val rBase = getFirstNumForBoard(Point2(3,0))
        if (steps >= rBase) {
            val boards = ceil((steps - rBase).toFloat() / rightStep).toInt()
            br.x += boards
        }

        return Pair(tl, br)
    }

    fun getCountForSteps(steps: Int): Long {
        val (tl, br) = getBoardExtent(steps)
        println("Extents: $tl , $br")
        var count = 0L
        for (y in tl.y..br.y) {
            for (x in tl.x..br.x) {
                val subCount = getCountForBoard(Point2(x,y), steps)
                count += subCount
            }
        }
        return count
    }
}

fun calcPt2(inp: Board, steps: Int): Long {
    val gridSize = 7
    val halfGrid = gridSize/2
    val overallTotals = MutableList<Int>(1) { 0 }
    val ring = MutableList<MutableList<Int>>(gridSize*gridSize) { MutableList<Int>(1) { 0 } }
    val firstNums = MutableList<Int>(gridSize*gridSize) { 0 }
    val preCycles = MutableList<List<Int>?>(gridSize*gridSize) { null }
    val cycles = MutableList<Cycle?>(gridSize*gridSize) { null }

    var set = hashSetOf<Point2>(inp.start)
    for (i in 1..steps) {
        val nextStep = HashSet<Point2>()
        for (pt in set) {
            val ptc = listOf(Point2(pt.x-1, pt.y), Point2(pt.x+1, pt.y), Point2(pt.x, pt.y-1), Point2(pt.x, pt.y+1))
            for (ptn in ptc) {
                val xc = smod(ptn.x, inp.b[0].size)
                val yc = smod(ptn.y, inp.b.size)
                if (inp.b[yc][xc]) {
                    continue
                }
                nextStep.add(ptn)
            }
        }
        set = nextStep
        overallTotals.add(set.size)
        if (((i - 65) % 131) == 0) {
            println("Step,Count: $i,${set.size}")
        }

        for (y in -halfGrid..halfGrid) {
            for (x in -halfGrid..halfGrid) {
                val idx = (y+halfGrid)*gridSize + (x + halfGrid)
                val cnt = set.filter { p -> p.x >= x*inp.b[0].size && p.x < (x+1)*inp.b[0].size && p.y >= y*inp.b.size && p.y < (y+1)*inp.b.size }.size
                ring[idx].add(cnt)
                if (cnt > 0) {
                    if (firstNums[idx] == 0) {
                        firstNums[idx] = i
                    }
                    if (cycles[idx] == null) {
                        // Cheating with maxcycle = 2 to try to speed this up
                        val cycle = findCycle(ring[idx].slice(firstNums[idx]..<ring[idx].size), 2)
                        if (cycle != null) {
                            cycles[idx] = cycle
                            preCycles[idx] = ring[idx].slice(firstNums[idx]..<firstNums[idx]+cycle.firstIndex)
                            println("Found cycle: $x, $y : Step $i, $cycle")
                        }
                    }
                }
            }
        }

        if (cycles.all { v -> v != null }) {
            println("Filled out chart after step $i")

            val pb = PredictiveBoard(gridSize, firstNums, preCycles.map { r -> r!! }, cycles.map { r -> r!! })

            return pb.getCountForSteps(steps)
        }
    }

    return set.size.toLong()
}

fun smod(v: Int, mod: Int): Int {
    if (v >= 0) {
        return v % mod
    }
    val mp = ((-v) % mod)
    return if (mp == 0) { 0 } else { mod - mp }
}

data class Board(val b: List<List<Boolean>>, val start: Point2)

fun parsePt1(inp: String): Board {
    var start = Point2(-1,-1)
    val b = inp.split("\n").mapIndexed { y, r -> r.mapIndexed { x, c ->
        if (c == 'S') {
            start = Point2(x, y)
        }
        c == '#'
    }}
    return Board(b, start)
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
