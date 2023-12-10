package day10

import kotlin.math.max
import kotlin.math.min

fun main() {
    val board11 = parsePt1(input1)
    println("board11: $board11")
    val board1b1 = parsePt1(input1b)
//    println("board1b1: $board1b1")
    val board1Mine = parsePt1(inputMine)

    val sol11 = calcPt1(board11)
    println("part11: $sol11, Answer: $answer1, Correct: ${answer1 == sol11}")
    val sol1b1 = calcPt1(board1b1)
    println("part1b1: $sol1b1, Answer: $answer1b, Correct: ${answer1b == sol1b1}")
    println("part1Mine: ${calcPt1(board1Mine)}")

    val board21 = parsePt2(input2)
    val board2b1 = parsePt2(input2b)
    val board2c1 = parsePt2(input2c)
    val board2Mine = parsePt2(inputMine)

    val sol21 = calcPt2(board21)
    println("part21: $sol21, Answer: $answer2, Correct: ${answer2 == sol21}")
    val sol2b1 = calcPt2(board2b1)
    println("part2b1: $sol2b1, Answer: $answer2b, Correct: ${answer2b == sol2b1}")
    val sol2c1 = calcPt2(board2c1)
    println("part2c1: $sol2c1, Answer: $answer2c, Correct: ${answer2c == sol2c1}")
    println("part2Mine: ${calcPt2(board2Mine)}")
}

fun calcPt1(inp: Board): Int {
    val loop = findLoop(inp)

    return loop.walks.size / 2
}

class LoopMid(val walks: ArrayList<Point>, val walked: Set<Point>) {
    fun add(pt: Point): LoopMid {
        val nw = ArrayList(walks)
        nw.add(pt)
        val nwd = HashSet(walked)
        nwd.add(pt)
        return LoopMid(nw, nwd)
    }
}

inline fun boardPt(inp: Board, pt: Point): PipeType { return inp.m[pt.y][pt.x] }

fun findLoop(inp: Board): LoopMid {
    var walks = ArrayList<LoopMid>()

    val fLoop = LoopMid(ArrayList<Point>(), HashSet<Point>())
    walks.add(fLoop.add(inp.start))

    while (true) {
        val nextWalks = ArrayList<LoopMid>()
        for (walk in walks) {
            // find all options
            val lastPt = walk.walks.last()
            val tpt = boardPt(inp, lastPt)

            if (lastPt.x > 0) {
                if (tpt == PipeType.Start || tpt == PipeType.HorPipe || tpt == PipeType.SWBend || tpt == PipeType.NWBend) {
                    val nextPt = Point(lastPt.x-1, lastPt.y)
                    if (nextPt == inp.start && walk.walks.size > 2) {
                        return walk
                    }
                    if (nextPt !in walk.walked) {
                        val npt = boardPt(inp, nextPt)
                        if (npt == PipeType.HorPipe || npt == PipeType.SEBend || npt == PipeType.NEBend) {
                            nextWalks.add(walk.add(nextPt))
                        }
                    }
                }
            }

            if (lastPt.x < inp.width - 1) {
                if (tpt == PipeType.Start || tpt == PipeType.HorPipe || tpt == PipeType.SEBend || tpt == PipeType.NEBend) {
                    val nextPt = Point(lastPt.x+1, lastPt.y)
                    if (nextPt == inp.start && walk.walks.size > 2) {
                        return walk
                    }
                    if (nextPt !in walk.walked) {
                        val npt = boardPt(inp, nextPt)
                        if (npt == PipeType.HorPipe || npt == PipeType.SWBend || npt == PipeType.NWBend) {
                            nextWalks.add(walk.add(nextPt))
                        }
                    }
                }
            }

            if (lastPt.y > 0) {
                if (tpt == PipeType.Start || tpt == PipeType.VertPipe || tpt == PipeType.NEBend || tpt == PipeType.NWBend) {
                    val nextPt = Point(lastPt.x, lastPt.y-1)
                    if (nextPt == inp.start && walk.walks.size > 2) {
                        return walk
                    }
                    if (nextPt !in walk.walked) {
                        val npt = boardPt(inp, nextPt)
                        if (npt == PipeType.VertPipe || npt == PipeType.SEBend || npt == PipeType.SWBend) {
                            nextWalks.add(walk.add(nextPt))
                        }
                    }
                }
            }

            if (lastPt.y < inp.height - 1) {
                if (tpt == PipeType.Start || tpt == PipeType.VertPipe || tpt == PipeType.SEBend || tpt == PipeType.SWBend) {
                    val nextPt = Point(lastPt.x, lastPt.y+1)
                    if (nextPt == inp.start && walk.walks.size > 2) {
                        return walk
                    }
                    if (nextPt !in walk.walked) {
                        val npt = boardPt(inp, nextPt)
                        if (npt == PipeType.VertPipe || npt == PipeType.NEBend || npt == PipeType.NWBend) {
                            nextWalks.add(walk.add(nextPt))
                        }
                    }
                }
            }
        }

        walks = nextWalks
    }
}

enum class Direction {
    North,
    South,
    East,
    West,
}

fun calcPt2(inp: Board): Int {
    val loop = findLoop(inp)

    // Only pipes from the loop count as pipe
    var flood = inp.m.map { r -> r.map { v -> FloodType.Unknown }.toTypedArray() }.toTypedArray()

    for (p in loop.walks) {
        flood[p.y][p.x] = FloodType.Pipe
    }

    // Overwrite start with proper pipe type
    val firstPipe = loop.walks[1]
    val lastPipe = loop.walks.last()
    val firstDir: Direction
    if (firstPipe.x + 1 == inp.start.x) {
        firstDir = Direction.West
    } else if (firstPipe.x - 1 == inp.start.x) {
        firstDir = Direction.East
    } else if (firstPipe.y + 1 == inp.start.y) {
        firstDir = Direction.North
    } else {
        firstDir = Direction.South
    }
    val lastDir: Direction
    if (lastPipe.x + 1 == inp.start.x) {
        lastDir = Direction.West
    } else if (lastPipe.x - 1 == inp.start.x) {
        lastDir = Direction.East
    } else if (lastPipe.y + 1 == inp.start.y) {
        lastDir = Direction.North
    } else {
        lastDir = Direction.South
    }

    val dirsAre = { dir: Direction, dir2: Direction, comp1: Direction, comp2: Direction ->
        (dir == comp1 && dir2 == comp2) || (dir == comp2 && dir2 == comp1)
    }

    val startPipe: PipeType
    if (dirsAre(firstDir, lastDir, Direction.West, Direction.East)) {
        startPipe = PipeType.HorPipe
    } else if (dirsAre(firstDir, lastDir, Direction.North, Direction.South)) {
        startPipe = PipeType.VertPipe
    } else if (dirsAre(firstDir, lastDir, Direction.North, Direction.West)) {
        startPipe = PipeType.NWBend
    } else if (dirsAre(firstDir, lastDir, Direction.North, Direction.East)) {
        startPipe = PipeType.NEBend
    } else if (dirsAre(firstDir, lastDir, Direction.South, Direction.West)) {
        startPipe = PipeType.SWBend
    } else if (dirsAre(firstDir, lastDir, Direction.South, Direction.East)) {
        startPipe = PipeType.SEBend
    } else {
        throw Exception("nooooo")
    }
    inp.m[inp.start.y][inp.start.x] = startPipe

    for (y in 0..<inp.height) {
        for (x in 0..<inp.width) {
            val pt = Point(x, y)
            if (flood[pt.y][pt.x] != FloodType.Unknown) {
                continue
            }

            // Found an unfilled point.  We're going to start a floodfill, but need to know if it's a fill of "inside"
            // or "outside.  Walk in from the left edge and count as we pass through edges of the pipe, calculating
            // kinda sub-pixelly of whether the north and south halves of the pixel are inside vs outside.
            var nInside = false
            var sInside = false
            for (ptx in (0..pt.x)) {
                val ptT = Point(ptx, pt.y)
                val isPipe = ptT in loop.walked
                if (!isPipe) {
                    continue
                }
                val pipeType = inp.m[pt.y][ptx]
                when (pipeType) {
                    PipeType.VertPipe -> {
                        nInside = !nInside
                        sInside = !sInside
                    }
                    PipeType.HorPipe -> {
                        // noop
                    }
                    PipeType.NEBend -> {
                        if (nInside != sInside) {
                            throw Exception("ne")
                        }
                        nInside = !nInside
                    }
                    PipeType.SEBend -> {
                        if (nInside != sInside) {
                            throw Exception("se")
                        }
                        sInside = !sInside
                    }
                    PipeType.NWBend -> {
                        nInside = !nInside
                        if (nInside != sInside) {
                            throw Exception("nw")
                        }
                    }
                    PipeType.SWBend -> {
                        sInside = !sInside
                        if (nInside != sInside) {
                            throw Exception("sw")
                        }
                    }
                    PipeType.None -> {
                        // noop
                    }
                    PipeType.Start -> {
                        throw Exception("start")
                    }
                }
            }
            if (nInside != sInside) {
                throw Exception("noequalend")
            }

            // Run a basic flood fill from that point
            val floodType = if (nInside && sInside) { FloodType.Inside } else { FloodType.Outside }
            val toFill = listOf(pt).toMutableList()
            while (toFill.isNotEmpty()) {
                val fillFrom = toFill.removeFirst()
                if (flood[fillFrom.y][fillFrom.x] != FloodType.Unknown) {
                    continue
                }
                flood[fillFrom.y][fillFrom.x] = floodType

                val xM = max(0, fillFrom.x-1)
                val xMax = min(inp.width-1, fillFrom.x+1)
                val yM = max(0, fillFrom.y-1)
                val yMax = min(inp.height-1, fillFrom.y+1)
                for (yi in yM..yMax) {
                    for (xi in xM..xMax) {
                        toFill.add(Point(xi, yi))
                    }
                }
            }
        }
    }

    return flood.sumOf { r -> r.filter { v -> v == FloodType.Inside }.size }
}

enum class FloodType {
    Unknown,
    Pipe,
    Inside,
    Outside,
}

enum class PipeType {
    VertPipe,
    HorPipe,
    NEBend,
    NWBend,
    SWBend,
    SEBend,
    None,
    Start,
}

data class Point(val x: Int, val y: Int)
data class Board(val m: MutableList<MutableList<PipeType>>, val start: Point, val width: Int, val height: Int)

fun parsePt1(inp: String): Board {
    val m = inp.split("\n").map{ l -> l.map { c ->
        when (c) {
            '|' -> PipeType.VertPipe
            '-' -> PipeType.HorPipe
            'L' -> PipeType.NEBend
            'J' -> PipeType.NWBend
            '7' -> PipeType.SWBend
            'F' -> PipeType.SEBend
            '.' -> PipeType.None
            'S' -> PipeType.Start
            else -> throw Exception("no")
        }
    }.toMutableList()
    }.toMutableList()

    var st = Point(-1, -1)
    m.forEachIndexed{ y, r -> r.forEachIndexed { x, v -> if (v == PipeType.Start) { st = Point(x, y) } }}

    return Board(m, st, m[0].size, m.size)
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
