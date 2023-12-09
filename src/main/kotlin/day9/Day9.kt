package day9

fun main() {
    val board11 = parsePt1(input1)
    println("board11: $board11")
    val board1Mine = parsePt1(inputMine)

    val sol11 = calcPt1(board11)
    println("part11: $sol11, Answer: $answer1, Correct: ${answer1 == sol11}")
    println("part1Mine: ${calcPt1(board1Mine)}")

    val board21 = parsePt2(input2)
    val board2Mine = parsePt2(inputMine)

    val sol21 = calcPt2(board21)
    println("part21: $sol21, Answer: $answer2, Correct: ${answer2 == sol21}")
    println("part2Mine: ${calcPt2(board2Mine)}")
}

fun calcPt1(inp: Board): Long {
    return inp.boards.map(::predictNextVal).sum()
}

fun predictNextVal(v: List<Long>): Long {
    var ls = ArrayList<ArrayList<Long>>()

    ls.add(ArrayList(v))
    var lastList = v
    while (lastList.any { vv -> vv != 0L}) {
        lastList = calcDeltas(lastList)
        ls.add(ArrayList(lastList))
    }

    ls[ls.size - 1].add(0)
    for (i in (ls.size - 2) downTo 0) {
        ls[i].add(ls[i][ls[i].size - 1] + ls[i+1][ls[i+1].size - 1])
    }

    return ls[0][ls[0].size - 1]
}

fun predictPrevVal(v: List<Long>): Long {
    var ls = ArrayList<ArrayList<Long>>()

    ls.add(ArrayList(v))
    var lastList = v
    while (lastList.any { vv -> vv != 0L}) {
        lastList = calcDeltas(lastList)
        ls.add(ArrayList(lastList))
    }

    ls[ls.size - 1].add(0, 0L)
    for (i in (ls.size - 2) downTo 0) {
        ls[i].add(0, ls[i][0] - ls[i+1][0])
    }

    return ls[0][0]
}

fun calcDeltas(v: List<Long>): List<Long> {
    return (1..<v.size).map { i -> v[i] - v[i-1] }
}

fun calcPt2(inp: Board): Long {
    return inp.boards.map(::predictPrevVal).sum()
}

data class Board(val boards: List<List<Long>>)

fun parsePt1(inp: String): Board {
    val histories = inp.split("\n").map{ l -> l.split(" ").map{ v -> v.toLong()}}

    return Board(histories)
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
