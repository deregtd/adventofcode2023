package day11

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val board1a = parseBoard(input1a, 2)
    println("board1a: $board1a")
//    val board1b = parsePt1(input1b)
//    println("board1b1: $board1b")
    val board1Mine = parseBoard(inputMine, 2)

    val sol1a = calcPt1(board1a)
    println("part1a: $sol1a, Answer: $answer1a, Correct: ${answer1a == sol1a}")
//    val sol1b = calcPt1(board1b)
//    println("part1b: $sol1b, Answer: $answer1b, Correct: ${answer1b == sol1b}")
    println("part1Mine: ${calcPt1(board1Mine)}")

    val board2a = parseBoard(input1a, 10)
    val board2b = parseBoard(input1a, 100)
//    val board2b = parsePt2(input2b)
//    val board2c = parsePt2(input2c)
    val board2Mine = parseBoard(inputMine, 1000000)

    val sol2a = calcPt2(board2a)
    println("part2a: $sol2a, Answer: $answer2a, Correct: ${answer2a == sol2a}")
    val sol2b = calcPt2(board2b)
    println("part2b: $sol2b, Answer: $answer2b, Correct: ${answer2b == sol2b}")
//    val sol2c = calcPt2(board2c)
//    println("part2c: $sol2c, Answer: $answer2c, Correct: ${answer2c == sol2c}")
    println("part2Mine: ${calcPt2(board2Mine)}")
}

fun calcPt1(inp: Board): Long {
    val g = findGalaxies(inp)

    val lengths = ArrayList<Long>()
    for (o in 0..<g.size) {
        for (inner in o+1..<g.size) {
            lengths.add(shortestDist(inp, g[o], g[inner]))
        }
    }

    return lengths.sum()
}

fun shortestDist(inp: Board, p1: Point, p2: Point): Long {
    val ys = if (p2.y > p1.y) { Pair(p1.y, p2.y) } else { Pair(p2.y, p1.y) }
    val xs = if (p2.x > p1.x) { Pair(p1.x, p2.x) } else { Pair(p2.x, p1.x) }
    var count = 0L
    for (y in ys.first+1..ys.second) {
        count += inp.rowExpenses[y]
    }
    for (x in xs.first+1..xs.second) {
        count += inp.colExpenses[x]
    }
    return count
}

data class Point(val x: Int, val y: Int)

fun findGalaxies(inp: Board): List<Point> {
    var s = ArrayList<Point>()
    for (y in 0..<inp.m.size) {
        for (x in 0..<inp.m[0].size) {
            if (inp.m[y][x]) {
                s.add(Point(x,y))
            }
        }
    }
    return s
}

fun calcPt2(inp: Board): Long {
    return calcPt1(inp)
}

data class Board(var m: List<List<Boolean>>, val colExpenses: List<Long>, val rowExpenses: List<Long>)

fun parseRawBoard(inp: String): MutableList<MutableList<Boolean>> {
    return inp.split("\n").map { l -> l.map { c -> c == '#' }.toMutableList() }.toMutableList()
}

fun parseBoard(inp: String, expansion: Long): Board {
    val m = parseRawBoard(inp)

    val colExpenses = m[0].mapIndexed { x, _ -> if (m.all { r -> !r[x] }) { expansion } else { 1L } }
    val rowExpenses = m.mapIndexed { y, _ -> if (m[y].all { v -> !v }) { expansion } else { 1L } }

    return Board(m, colExpenses, rowExpenses)
}
