package day23

import common.Dir
import common.Point2

fun main() {
    println("Part 1:")
    for (i in inputs1.indices) {
        val sample = inputs1[i]

        val board = parsePt1(sample.input)
//        println("board: $board")
        val sol = calcPt(board)
        println("calced: $sol, Answer: ${sample.answer}, Correct: ${sample.answer == sol}")
    }

    val board1Mine = parsePt1(inputMine)
    println("Mine: ${calcPt(board1Mine)}")

    println()
    println("Part 2:")

    for (i in inputs2.indices) {
        val sample = inputs2[i]

        val board = parsePt2(sample.input)
//        println("board: $board")
        val sol = calcPt(board, true)
        println("calced: $sol, Answer: ${sample.answer}, Correct: ${sample.answer == sol}")
    }

    val board2Mine = parsePt2(inputMine)
    println("Mine: ${calcPt(board2Mine, true)}")
}

data class Walk(var pt: Point2, var dir: Dir, var steps: Int, val intersections: HashSet<Point2>)

fun calcPt(inp: Board, pt2: Boolean = false): Long {
    val start = Point2(inp.b[0].indexOf(Cell.Space), 0)
    val finish = Point2(inp.b.last().indexOf(Cell.Space), inp.b.size-1)

    val walkLooks = HashMap<Pair<Point2,Dir>,Pair<List<Point2>,List<Dir>>>()

    var bestFinish = -1

    val toWalk = mutableListOf(Walk(start, Dir.Down, 0, HashSet()))
    while (toWalk.isNotEmpty()) {
        val walk = toWalk.removeLast()

        val listPts: List<Point2>
        val listDirs: List<Dir>

        val wl = walkLooks.get(Pair(walk.pt, walk.dir))
        if (wl != null) {
            listPts = wl.first
            listDirs = wl.second
        } else {
            val (listPtsN, listDirsN) = findNextIntersection(inp, walk.pt, finish, walk.dir, pt2)
            listPts = listPtsN
            listDirs = listDirsN
            walkLooks.set(Pair(walk.pt, walk.dir), Pair(listPts, listDirs))
        }

        val lastPt = listPts.last()
        if (walk.intersections.contains(lastPt)) {
            // hit our own snake
            continue
        }

        walk.intersections.add(lastPt)
        walk.steps += listPts.size

        if (lastPt == finish) {
            if (walk.steps > bestFinish) {
                bestFinish = walk.steps
                println("Finish: ${walk.steps}, Cache: ${walkLooks.size}")
            }
            continue
        }

        if (listDirs.isEmpty()) {
            // no outlet
            continue
        }

        for (dir in listDirs) {
            val ni = HashSet(walk.intersections)
            toWalk.add(Walk(lastPt, dir, walk.steps, ni))
        }
    }

    println("Final cache: ${walkLooks.size}")

    return bestFinish.toLong()
}

fun findNextIntersection(inp: Board, startPt: Point2, finishPt: Point2, startDir: Dir, pt2: Boolean): Pair<List<Point2>,List<Dir>> {
    var pt = startPt.travel(startDir)
    val ptHist = ArrayList<Point2>()
    while (true) {
        ptHist.add(pt)
        if (pt == finishPt) {
            return Pair(ptHist, listOf())
        }

        val c = inp.b[pt.y][pt.x]
        val checks = ArrayList<Dir>()
        when {
            c == Cell.Wall -> throw Exception()
            (c == Cell.Space) || pt2 -> {
                checks.add(Dir.Up)
                checks.add(Dir.Down)
                checks.add(Dir.Left)
                checks.add(Dir.Right)
            }

            c == Cell.SlopeRight -> checks.add(Dir.Right)
            c == Cell.SlopeLeft -> checks.add(Dir.Left)
            c == Cell.SlopeUp -> checks.add(Dir.Up)
            c == Cell.SlopeDown -> checks.add(Dir.Down)
        }

        val cf = checks.filter { dir ->
            val npt = pt.travel(dir)
            if (ptHist.size == 1 && npt == startPt) {
                return@filter false
            }
            if ((ptHist.size > 1) && (ptHist[ptHist.size-2] == npt)) {
                return@filter false
            }
            if (npt.x < 0 || npt.x >= inp.b[0].size || npt.y < 0 || npt.y >= inp.b.size) {
                return@filter false
            }
            inp.b[npt.y][npt.x] != Cell.Wall
        }

        if (cf.size != 1) {
            return Pair(ptHist, cf)
        }

        pt = pt.travel(cf[0])
    }
}

enum class Cell {
    Space,
    Wall,
    SlopeUp,
    SlopeRight,
    SlopeDown,
    SlopeLeft,
}

data class Board(var b: List<List<Cell>>)

fun parsePt1(inp: String): Board {
    return Board(inp.split("\n").map{ r -> r.map { v ->
        when (v) {
            '.' -> Cell.Space
            '#' -> Cell.Wall
            '>' -> Cell.SlopeRight
            '<' -> Cell.SlopeLeft
            '^' -> Cell.SlopeUp
            'v' -> Cell.SlopeDown
            else -> throw Exception()
        }}
    })
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
