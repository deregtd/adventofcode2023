package day6

fun main() {
    val board11 = parsePt1(input1)
    val board1Mine = parsePt1(inputMine)

    println("part11: ${calc(board11)}")
    println("part11Answer: ${answer1}")
    println("part1Mine: ${calc(board1Mine)}")

    val board21 = parsePt2(input1)
    val board2Mine = parsePt2(inputMine)

    println("part21: ${calc(board21)}")
    println("part21Answer: ${answer2}")
    println("part2Mine: ${calc(board2Mine)}")
}

fun calc(board: Board): Int {
    return board.races.map{ r ->
        val dists = (1..<r.time).map { v -> (r.time - v) * v }
        dists.filter{ d -> d > r.dist }.size
    }.reduce{ a,b -> a * b }
}

data class Race(val time: Long, val dist: Long)
data class Board(val races: List<Race>)

fun parsePt1(inp: String): Board {
    val sp = inp.split("\n").map { l ->
        l.substring(11).split(" ").filter{ v -> v != "" }.map{ v -> v.toLong() }
    }

    val races = sp[0].mapIndexed{ i, _ -> Race(sp[0][i], sp[1][i]) }
    return Board(races)
}

fun parsePt2(inp: String): Board {
    val sp = inp.split("\n").map { l ->
        l.substring(11).replace(" ", "").toLong()
    }

    return Board(listOf(Race(sp[0], sp[1])))
}
