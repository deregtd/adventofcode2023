package day12

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
    val arrs = inp.rows.map(::arrangements)
    println(arrs)
    return arrs.sum().toLong()
}

fun arrangements(inp: Row): Long {
    var ms = mutableMapOf<Row, Long>()
    val arr = arrangementsInner(ms, inp)
    println("Inp: ${inp.r} -> $arr")
    return arr
}

fun arrangementsInner(ms: MutableMap<Row, Long>, inp: Row): Long {
    val r = ms.get(inp)
    if (r != null) {
        return r
    }

    if (inp.r.isEmpty()) {
        if (inp.blocks.size == 0) {
            if (inp.currentRun == 0) {
                return 1
            }
            return 0
        }
        if (inp.blocks.size == 1 && inp.currentRun == inp.blocks[0]) {
            return 1
        }
        return 0
    }

    val subArr = inp.r.slice(1..<inp.r.size)
    var results = 0L
    if (inp.r[0] == Cell.Works || inp.r[0] == Cell.Unknown) {
        // Either way, let's add it as a "works" to the count
        if (inp.currentRun == 0) {
            // No pending block, so just move over a char
            results += arrangementsInner(ms, Row(subArr, inp.blocks, 0))
        } else if (inp.blocks.size > 0 && inp.currentRun == inp.blocks[0]) {
            // Closed out the block and had a block to close out
            results += arrangementsInner(ms, Row(subArr, inp.blocks.slice(1..<inp.blocks.size), 0))
        }
    }
    if ((inp.r[0] == Cell.Unknown || inp.r[0] == Cell.Damaged)) {
        // Either way, let's add it to the "damaged" to the count
        results += arrangementsInner(ms, Row(subArr, inp.blocks, inp.currentRun+1))
    }

    ms.set(inp, results)
    return results
}

fun calcPt2(inp: Board): Long {
    return calcPt1(inp)
}

enum class Cell {
    Works,
    Damaged,
    Unknown,
}

data class Row(var r: List<Cell>, val blocks: List<Int>, val currentRun: Int)

data class Board(val rows: List<Row>)

fun parsePt1(inp: String): Board {
    val rows = inp.split("\n").map{ l ->
        val sp = l.split(" ")
        val r = sp[0].map { c ->
            when(c) {
                '?' -> Cell.Unknown
                '#', -> Cell.Damaged
                '.' -> Cell.Works
                else -> throw Exception()
            }
        }.toMutableList()
        Row(r, sp[1].split(",").map { c -> c.toInt() }, 0)
    }

    return Board(rows)
}

fun parsePt2(inp: String): Board {
    val b1 = parsePt1(inp)
    return Board(b1.rows.map { r ->
        val l = ArrayList<Cell>()
        for (i in 1..5) {
            if (i != 1) {
                l.add(Cell.Unknown)
            }
            l.addAll(r.r)
        }

        val b = ArrayList<Int>()
        for (i in 1..5) {
            b.addAll(r.blocks)
        }
        Row(l, b, 0)
    })
}
