package day15

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
    val res = inp.strs.map(::calcHash)
    return res.sum()
}

fun calcHash(inp: String): Long {
    var hash = 0L
    for (c in inp) {
        hash += c.toLong()
        hash *= 17
        hash = hash and 255L
    }
    return hash
}

data class Lens(var label: String, var focalLength: Int)
data class Box(var lenses: MutableList<Lens>)

fun calcPt2(inp: Board2): Long {
    var boxes = ArrayList<Box>(256)
    for (i in 1..256) {
        boxes.add(Box(ArrayList<Lens>()))
    }

    for (op in inp.ops) {
        val boxNum = calcHash(op.label).toInt()
        val idx = boxes[boxNum].lenses.indexOfFirst { l -> l.label == op.label }
        if (op.opEquals) {
            if (idx == -1) {
                boxes[boxNum].lenses.add(Lens(op.label, op.num))
            } else {
                boxes[boxNum].lenses[idx].focalLength = op.num
            }
        } else {
            if (idx != -1) {
                boxes[boxNum].lenses.removeAt(idx)
            }
        }
    }

    val powers = boxes.mapIndexed { boxNum, box ->
        box.lenses.mapIndexed { slotNum, l -> (boxNum+1)*(slotNum+1)*l.focalLength }.sum()
    }

    return powers.sum().toLong()
}

data class Board(val strs: List<String>)

data class Op(val label: String, val opEquals: Boolean, val num: Int)
data class Board2(val ops: List<Op>)

fun parsePt1(inp: String): Board {
    return Board(inp.split(","))
}

fun parsePt2(inp: String): Board2 {
    return Board2(inp.split(",").map { v ->
        val sp = v.split("-","=")
        Op(sp[0], v.contains("="), if (sp[1] == "") { -1 } else {sp[1].toInt() })
    })
}
