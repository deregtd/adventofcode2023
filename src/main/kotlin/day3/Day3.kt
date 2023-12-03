package day3

import kotlin.math.max
import kotlin.math.min

data class NumLoc(val y: Int, val xStart: Int, val xEnd: Int, val partNum: Int)

fun main() {
    val lines1 = input1.split("\n")
    val partNums1 = getPartNumbers(lines1)

    val part1Nums1 = filterPartNumbersForAnySymbol(partNums1, lines1)
    println("part1Nums1Sum: ${part1Nums1.map { n -> n.partNum }.sum() }")

    val linesMine = inputMine.split("\n")
    val partNumsMine = getPartNumbers(linesMine)

    val part1NumsMine = filterPartNumbersForAnySymbol(partNumsMine, linesMine)
    println("part1NumsMineSum: ${part1NumsMine.map { n -> n.partNum }.sum() }")

    val part2Nums1 = getGears(partNums1, lines1)
    println("part2Nums1Sum: ${part2Nums1.sum() }")

    val part2NumsMine = getGears(partNumsMine, linesMine)
    println("part2NumsMineSum: ${part2NumsMine.sum() }")
}

fun getGears(parts: List<NumLoc>, lines: List<String>): List<Int> {
    val gears = ArrayList<Int>()

    for (y in 0..<lines.size) {
        val line = lines[y]
        for (x in 0..<line.length) {
            if (line[x] == '*') {
                val adjParts = parts.filter { p ->
                    val (minY, maxY, minX, maxX) = getAdjBox(p, lines)
                    x in minX..maxX && y in minY..maxY
                }
                if (adjParts.size == 2) {
                    gears.add(adjParts[0].partNum * adjParts[1].partNum)
                }
            }
        }
    }

    return gears
}

fun filterPartNumbersForAnySymbol(parts: List<NumLoc>, lines: List<String>): List<NumLoc> {
    return parts.filter { n -> getAdjacentSymbols(n, lines).isNotEmpty() }
}

fun getPartNumbers(lines: List<String>): List<NumLoc> {
    val numLocs = ArrayList<NumLoc>()
    for (y in lines.indices) {
        val line = lines[y]
        var x = 0
        while (x < lines[y].length) {
            if (line[x].isDigit()) {
                var xEnd = -1
                for (h in x+1..<line.length) {
                    if (!line[h].isDigit()) {
                        xEnd = h-1
                        break
                    }
                }
                if (xEnd == -1) {
                    xEnd = line.length - 1
                }
                val num = line.substring(x, xEnd+1).toInt()
                numLocs.add(NumLoc(y, x, xEnd, num))
                x = xEnd
            }
            x++
        }
    }
    return numLocs
}

fun getAdjBox(num: NumLoc, lines: List<String>): List<Int> {
    val minY = max(0, num.y - 1)
    val maxY = min(lines.size-1, num.y + 1)
    val minX = max(0, num.xStart - 1)
    val maxX = min(lines[0].length - 1, num.xEnd + 1)
    return listOf(minY, maxY, minX, maxX)
}

fun getAdjacentSymbols(num: NumLoc, lines: List<String>): Set<Char> {
    val adjSyms = HashSet<Char>()
    val (minY, maxY, minX, maxX) = getAdjBox(num, lines)
    for (y in minY..maxY) {
        for (x in minX..maxX) {
            val c = lines[y][x]
            if (isSymbol(c)) {
                adjSyms.add(c)
            }
        }
    }
    return adjSyms
}

fun isSymbol(c: Char): Boolean {
    if (c.isDigit()) {
        return false
    }
    if (c == '.') {
        return false
    }
    return true
}
