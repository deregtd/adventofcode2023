package day18

import kotlin.math.max
import kotlin.math.min

data class Point(var x: Int, var y: Int)

fun main() {
    println("Part 1:")
    for (i in inputs1.indices) {
        val sample = inputs1[i]

        val board = parsePt1(sample.input)
//        println("board: $board")
        val sol = calcPt2(board)
        println("calced: $sol, Answer: ${sample.answer}, Correct: ${sample.answer == sol}")
    }

    val board1Mine = parsePt1(inputMine)
    println("Mine: ${calcPt2(board1Mine)}")

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

fun calcPt2(inp: Board): Long {
    var inside = 0L
    val vertPending = HashSet<Int>()
    for (y in inp.minDim.y..inp.maxDim.y) {
        val pts = ArrayList<WalkCell>()
        val toNuke = ArrayList<Int>()
        for (vpx in vertPending) {
            val pt = Point(vpx, y)
            val w = inp.walk[pt]
            if (w != null) {
                if (w.dirFrom == Dir.Up || w.dirOut == Dir.Up) {
                    toNuke.add(w.pt.x)
                } else {
                    throw Exception()
                }
            } else {
                pts.add(WalkCell(pt, Dir.Up, Dir.Down))
            }
        }
        for (vpx in toNuke) {
            vertPending.remove(vpx)
        }
        for (w in inp.walk) {
            if (w.key.y == y) {
                pts.add(w.value)

                if (w.value.dirFrom == Dir.Down || w.value.dirOut == Dir.Down) {
                    vertPending.add(w.key.x)
                }
            }
        }
        pts.sortBy { w -> w.pt.x }

        var lastExit = inp.minDim.x
        var isOutside = true
        var cameFrom: Dir? = null
        for (w in pts) {
            if (cameFrom != null) {
                // Inside a pipe row
                if ((w.dirFrom == Dir.Up || w.dirOut == Dir.Up) && (w.dirFrom == Dir.Down || w.dirOut == Dir.Down)) {
                    // should never find a vert pipe inside a pipe
                    throw Exception()
                } else if ((w.dirFrom == Dir.Left || w.dirOut == Dir.Left) && (w.dirFrom == Dir.Right || w.dirOut == Dir.Right)) {
                    // should never be in a hor pipe, they don't exist anymore
                    throw Exception()
                } else if (w.dirFrom == Dir.Up || w.dirOut == Dir.Up) {
                    if (cameFrom == Dir.Up) {
                        // in and out the same way, don't change inside-ness
                    } else {
                        isOutside = !isOutside
                    }
                    // exiting up
                    inside += w.pt.x - lastExit + 1
                    cameFrom = null
                } else if (w.dirFrom == Dir.Down || w.dirOut == Dir.Down) {
                    if (cameFrom == Dir.Down) {
                        // in and out the same way, don't change inside-ness
                    } else {
                        isOutside = !isOutside
                    }
                    // exiting down
                    inside += w.pt.x - lastExit + 1
                    cameFrom = null
                }
            } else {
                if (!isOutside) {
                    // include the pipe since next x chunk won't include this cell
                    inside += (w.pt.x - lastExit) + 1
                } else {
                    inside++
                }

                if ((w.dirFrom == Dir.Up || w.dirOut == Dir.Up) && (w.dirFrom == Dir.Down || w.dirOut == Dir.Down)) {
                    // vertical pipe
                    isOutside = !isOutside
                } else if ((w.dirFrom == Dir.Left || w.dirOut == Dir.Left) && (w.dirFrom == Dir.Right || w.dirOut == Dir.Right)) {
                    // should never be in a hor pipe while not inside something
                    throw Exception()
                } else if (w.dirFrom == Dir.Up || w.dirOut == Dir.Up) {
                    // exiting/entering up
                    cameFrom = Dir.Up
                } else if (w.dirFrom == Dir.Down || w.dirOut == Dir.Down) {
                    // exiting/entering down
                    cameFrom = Dir.Down
                }
            }
            lastExit = w.pt.x+1
        }
    }
    return inside
}

enum class Dir {
    Up {
        override fun opposite(): Dir { return Dir.Down }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Left, Dir.Up, Dir.Right) }
        override fun continuePoint(pt: Point): Point { return Point(pt.x, pt.y-1) }
    },
    Right {
        override fun opposite(): Dir { return Dir.Left }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Up, Dir.Right, Dir.Down) }
        override fun continuePoint(pt: Point): Point { return Point(pt.x + 1, pt.y) }
    },
    Down {
        override fun opposite(): Dir { return Dir.Up }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Right, Dir.Down, Dir.Left) }
        override fun continuePoint(pt: Point): Point { return Point(pt.x, pt.y+1) }
    },
    Left {
        override fun opposite(): Dir { return Dir.Right }
        override fun dirsWithin90(): List<Dir> { return listOf(Dir.Down, Dir.Left, Dir.Up) }
        override fun continuePoint(pt: Point): Point { return Point(pt.x-1, pt.y) }
    };

    abstract fun opposite(): Dir
    abstract fun continuePoint(pt: Point): Point
    abstract fun dirsWithin90(): List<Dir>
}

data class WalkCell(val pt: Point, val dirFrom: Dir, val dirOut: Dir)

data class Board(val walk: HashMap<Point, WalkCell>, val minDim: Point, val maxDim: Point)

// L 2 (#5713f0)

fun parsePt1(inp: String): Board {
    val raw = inp.split("\n").map { r ->
        val sp = r.split(" ")
        val dir = when (sp[0][0]) {
            'L' -> Dir.Left
            'U' -> Dir.Up
            'D' -> Dir.Down
            'R' -> Dir.Right
            else -> throw Exception()
        }
        val count = sp[1].toInt()
        Pair(dir, count)
    }

    return parseCom(raw)
}

fun parseCom(raw: List<Pair<Dir, Int>>): Board {
    var pos = Point(0,0)
    var minDim = Point(0,0)
    var maxDim = Point(0,0)
    val walks = HashMap<Point, WalkCell>()
    raw.forEachIndexed { i, w ->
        (1..w.second).map { wi ->
            val dirOut = when {
                wi < w.second -> w.first
                i < raw.size - 1 -> raw[i+1].first
                else -> raw[0].first
            }
            pos = w.first.continuePoint(pos)

            if ((w.first == Dir.Up || dirOut == Dir.Up || w.first == Dir.Down || dirOut == Dir.Down) &&
                (w.first == Dir.Left || dirOut == Dir.Left || w.first == Dir.Right || dirOut == Dir.Right)) {
                // Only save points with a vertical component and a horizontal component
                walks[pos] = WalkCell(pos, w.first.opposite(), dirOut)
            }
            minDim = Point(min(minDim.x, pos.x), min(minDim.y, pos.y))
            maxDim = Point(max(maxDim.x, pos.x), max(maxDim.y, pos.y))
        }
    }

    return Board(walks, minDim, maxDim)
}

fun parsePt2(inp: String): Board {
    val raw = inp.split("\n").map { r ->
        val sp = r.split(" ")
        val col = sp[2].substring(2..7)
        val dir = when (col[5]) {
            '2' -> Dir.Left
            '3' -> Dir.Up
            '1' -> Dir.Down
            '0' -> Dir.Right
            else -> throw Exception()
        }
        val count = col.substring(0,5).toInt(radix = 16)
        Pair(dir, count)
    }

    return parseCom(raw)
}
