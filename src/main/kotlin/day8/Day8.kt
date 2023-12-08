package day8

fun main() {
    val board11 = parsePt1(input1)
    println("board11: $board11")
    val board11b = parsePt1(input1b)
    val board1Mine = parsePt1(inputMine)

    val sol11 = calcPt1(board11)
    println("part11: $sol11, Answer: $answer1, Correct: ${answer1 == sol11}")
    val sol11b = calcPt1(board11b)
    println("part11a: $sol11b, Answer: $answer1b, Correct: ${answer1b == sol11b}")
    println("part1Mine: ${calcPt1(board1Mine)}")

    val board21 = parsePt2(input2)
    val board2Mine = parsePt2(inputMine)

    val sol21 = calcPt2(board21)
    println("part21: $sol21, Answer: $answer2, Correct: ${answer2 == sol21}")
    println("part2Mine: ${calcPt2(board2Mine)}")
}

fun calcPt1(inp: Board): Int {
    return stepsForStart(inp, "AAA")
}

fun stepsForStart(inp: Board, start: String): Int {
    var steps = 0
    var v = start
    while (v[2] != 'Z') {
        val walk = inp.walks[steps % inp.walks.size]
        val p = inp.maps[v] ?: throw Exception("no")
        steps++
        v = if (walk) { p.second } else { p.first }
    }
    return steps
}

fun calcPt2(inp: Board): Long {
    val starts = inp.maps.keys.filter { k -> k.endsWith("A")}
    val finishes = starts.map { s ->
        stepsForStart(inp, s)
    }

    var l = 1L
    for (i in finishes) {
        l = findLCM(i.toLong(), l)
    }

    return l
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

data class Board(val walks: List<Boolean>, val maps: Map<String, Pair<String, String>>)

fun parsePt1(inp: String): Board {
    val s1 = inp.split("\n\n")
    val walks = s1[0].map { c -> c == 'R' }

    val maps = HashMap<String, Pair<String, String>>()
    val paths = s1[1].split("\n").forEach{ l ->
        val s2 = l.split(" = ")
        val r = s2[1].substring(1, s2[1].length-1).split(", ")
        maps[s2[0]] = Pair(r[0], r[1])
    }

    return Board(walks, maps)
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
