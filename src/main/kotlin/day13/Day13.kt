package day13

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
    val reflects = inp.images.map { img -> calcReflect(img, Pair(-1,-1)) }
    return reflects.map { p ->
        if (p.first > 0) {
            // col
            p.first.toLong()
        } else {
            p.second * 100L
        }
    }.sum()
}

// Pair<Column, Row> of divider
fun calcReflect(inp: Img, ignoreReflect: Pair<Int, Int>): Pair<Int, Int> {
cols@ for (x in 1..inp.m[0].size-1) {
        val cols = min(inp.m[0].size - x,x)
        for (xm in 0..<cols) {
            for (y in 0..<inp.m.size) {
                if (inp.m[y][x-1-xm] != inp.m[y][x+xm]) {
                    continue@cols
                }
            }
        }
        val ret = Pair(x, 0)
        if (ret != ignoreReflect) {
            return ret
        }
    }

rows@ for (y in 1..inp.m.size-1) {
        val rows = min(inp.m.size - y,y)
        for (ym in 0..<rows) {
            for (x in 0..<inp.m[0].size) {
                if (inp.m[y-1-ym][x] != inp.m[y+ym][x]) {
                    continue@rows
                }
            }
        }
        val ret = Pair(0, y)
        if (ret != ignoreReflect) {
            return ret
        }
    }

    return Pair(-1, -1)
}

fun calcPt2(inp: Board): Long {
    val reflects1 = inp.images.map { img -> calcReflect(img, Pair(-1,-1)) }
    val reflects = inp.images.mapIndexed { i, img -> calcAlternateReflect(img, reflects1[i]) }
    return reflects.map { p ->
        if (p.first > 0) {
            // col
            p.first.toLong()
        } else {
            p.second * 100L
        }
    }.sum()
}

fun calcAlternateReflect(img: Img, oldReflect: Pair<Int, Int>): Pair<Int, Int> {
    for (y in 0..<img.m.size) {
        for (x in 0..<img.m[0].size) {
            val img2 = Img(img.m.mapIndexed { yi, r -> r.mapIndexed { xi, v -> if (xi == x && yi == y) { !v } else { v }} })
            val newReflect = calcReflect(img2, oldReflect)
            if (newReflect != Pair(-1,-1)) {
                return newReflect
            }
        }
    }
    throw Exception("no alternate reflect")
}

data class Img(val m: List<List<Boolean>>)
data class Board(val images: List<Img>)

fun parsePt1(inp: String): Board {
    val imgs = inp.split("\n\n").map{ b ->
        Img(b.split("\n").map { c -> c.map { v -> v == '#'} })
    }

    return Board(imgs)
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
