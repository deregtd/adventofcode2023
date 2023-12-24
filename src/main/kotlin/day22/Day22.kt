package day22

import common.Point3
import common.extents

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
    calcCommon(inp)

    // see which we can remove
    var cleanRemoves = 0
    for ((i, brick) in inp.bricks.withIndex()) {
        brick.forAllPoints { p ->
            inp.set(p, -1)
            true
        }

        if (whichBricksCanMoveDown(inp, false).size == 0) {
            cleanRemoves++
        }

        brick.forAllPoints { p ->
            inp.set(p, i)
            true
        }
    }

    return cleanRemoves.toLong()
}

fun calcCommon(inp: Board) {
    // fill in board in mid air
    for ((i, brick) in inp.bricks.withIndex()) {
        brick.forAllPoints { p ->
            inp.set(p, i)
            true
        }
    }

    // get the bricks to settle
    var moveCount = 0
    while (whichBricksCanMoveDown(inp, true).size > 0) {
        moveCount++
    }
//    println("Moves: $moveCount")
//    inp.print()
}

fun whichBricksCanMoveDown(inp: Board, doAllValidMoves: Boolean): HashSet<Int> {
    var canFall = HashSet<Int>()

    for ((i, brick) in inp.bricks.withIndex()) {
        // check if brick can move down
        var foundBlock = false
        brick.forAllPoints { p ->
            if ((p.z == 1L) || (inp.get(Point3(p.x, p.y, p.z-1)) != -1)) {
                foundBlock = true
            }
            brick.axis != Axis.Z
        }
        if (foundBlock) {
            continue
        }

        canFall.add(i)

        if (!doAllValidMoves) {
            continue
        }

        // can drop!  do so
        brick.forAllPoints { p ->
            inp.set(Point3(p.x, p.y, p.z-1), i)
            inp.set(p, -1)
            true
        }

        brick.ends.first.z--
        brick.ends.second.z--
    }

    return canFall
}

fun calcPt2(inp: Board): Long {
    calcCommon(inp)

    val savedBoard = inp.save()

    // see which we can remove
    val counts = inp.bricks.mapIndexed { i, brick ->
        inp.restore(savedBoard)

        brick.forAllPoints { p ->
            inp.set(p, -1)
            true
        }

        var moved = HashSet<Int>()
        do {
            val sm = whichBricksCanMoveDown(inp, true)
            moved.addAll(sm)
        } while (sm.isNotEmpty())

        moved.size
    }

    return counts.sum().toLong()
}

enum class Axis {
    X,
    Y,
    Z,
    Single,
}

data class Brick(val ends: Pair<Point3, Point3>, val axis: Axis) {
    fun clone(): Brick {
        return Brick(Pair(ends.first.clone(), ends.second.clone()), axis)
    }

    fun forAllPoints(f: (Point3) -> Boolean) {
        when (axis) {
            Axis.X, Axis.Single -> {
                for (x in ends.first.x..ends.second.x) {
                    if (!f(Point3(x, ends.first.y, ends.first.z))) {
                        return
                    }
                }
            }
            Axis.Y -> {
                for (y in ends.first.y..ends.second.y) {
                    if (!f(Point3(ends.first.x, y, ends.first.z))) {
                        return
                    }
                }
            }
            Axis.Z -> {
                for (z in ends.first.z..ends.second.z) {
                    if (!f(Point3(ends.first.x, ends.first.y, z))) {
                        return
                    }
                }
            }
        }
    }
}

data class Board(var bricks: List<Brick>) {
    val bounds: Pair<Point3, Point3> = extents(bricks.map { r -> r.ends.toList() }.flatten())
    private val xMult = 1L
    private val yMult = (bounds.second.x-bounds.first.x+1)
    private val zMult = yMult * (bounds.second.y-bounds.first.y+1)
    private var board = MutableList((zMult*(bounds.second.z-bounds.first.z+1L)).toInt()) { -1L }

    fun save(): Pair<List<Brick>,MutableList<Long>> {
        return Pair(ArrayList(bricks.map { br -> br.clone()}),ArrayList(board))
    }

    fun restore(b: Pair<List<Brick>,MutableList<Long>>) {
        bricks = ArrayList(b.first.map { br -> br.clone() })
        board = ArrayList(b.second)
    }

    fun get(p: Point3): Int {
        return board[((p.x - bounds.first.x)*xMult + (p.y-bounds.first.y)*yMult + (p.z-bounds.first.z)*zMult).toInt()].toInt()
    }

    fun set(p: Point3, v: Int) {
        board[((p.x - bounds.first.x)*xMult + (p.y-bounds.first.y)*yMult + (p.z-bounds.first.z)*zMult).toInt()] = v.toLong()
    }

    fun print() {
        board.chunked(zMult.toInt()).forEachIndexed { z, zr ->
            println("z $z:")
            zr.chunked(yMult.toInt()).forEach { y -> println(y) }
        }
    }
}

fun parsePt1(inp: String): Board {
    return Board(inp.split("\n").map{ r ->
        val sp = r.split("~").map { v -> v.split(",").map { vv -> vv.toLong() }}.map { vi -> Point3(vi[0], vi[1], vi[2]) }
        val axis = when {
            sp[0].x != sp[1].x -> Axis.X
            sp[0].y != sp[1].y -> Axis.Y
            sp[0].z != sp[1].z -> Axis.Z
            else -> Axis.Single
        }
        Brick(Pair(sp[0], sp[1]), axis)
    })
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
