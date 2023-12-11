package day7

fun main() {
    val board11 = parsePt1(input1)
    println("board11: $board11")
    val board1Mine = parsePt1(inputMine)

    val sol11 = calcPt1(board11)
    println("part11: $sol11, Answer: $answer1, Correct: ${answer1 == sol11}")
    println("part1Mine: ${calcPt1(board1Mine)}")

    val board21 = parsePt2(input1)
    val board2Mine = parsePt2(inputMine)

    val sol21 = calcPt2(board21)
    println("part21: $sol21, Answer: $answer2, Correct: ${answer2 == sol21}")
    println("part2Mine: ${calcPt2(board2Mine)}")
}

fun calcPt1(inp: Board): Long {
    val hands = inp.hands.toTypedArray()
    hands.sortBy { h -> h.handFullScore }
    return hands.mapIndexed { i, h -> ((i + 1) * h.bid).toLong() }.sum()
}

fun calcPt2(inp: Board): Long {
    return calcPt1(inp)
}

val cardStrengthPt1 = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
val cardStrengthPt2 = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

enum class HandType {
    FiveOfAKind,
    FourOfAKind,
    FullHouse,
    ThreeOfAKind,
    TwoPair,
    OnePair,
    HighCard,
}

fun calcHandPt1(cards: List<Int>, bid: Int): Hand {
    val counts = Array<Int>(13) { 0 }
    cards.forEach { c -> counts[c]++ }

    return calcHandCommon(cards, bid, counts)
}

fun calcHandPt2(cards: List<Int>, bid: Int): Hand {
    val counts = Array<Int>(13) { 0 }
    cards.forEach { c -> counts[c]++ }

    var max = 0
    var maxIndex = -1
    counts.forEachIndexed { i, c ->
        if (i != 0 && c > max) {
            max = c
            maxIndex = i
        }
    }
    if (maxIndex == -1) {
        maxIndex = 12
    }
    counts[maxIndex] += counts[0]
    counts[0] = 0

    return calcHandCommon(cards, bid, counts)
}

fun calcHandCommon(cards: List<Int>, bid: Int, counts: Array<Int>): Hand {
    val fiveIndex = counts.indexOf(5)
    val fourIndex = counts.indexOf(4)
    val threeIndex = counts.indexOf(3)
    val twoIndexes = counts.mapIndexed { i, v -> if (v == 2) { i } else { -1 } }.filter{ i -> i != -1 }
    val oneIndexes = counts.mapIndexed { i, v -> if (v == 1) { i } else { -1 } }.filter{ i -> i != -1 }

    val handType: HandType
    if (fiveIndex != -1) {
        handType = HandType.FiveOfAKind
    } else if (fourIndex != -1) {
        handType = HandType.FourOfAKind
    } else if (threeIndex != -1) {
        if (twoIndexes.size == 1) {
            handType = HandType.FullHouse
        } else {
            handType = HandType.ThreeOfAKind
        }
    } else if (twoIndexes.size == 2) {
        handType = HandType.TwoPair
    } else if (twoIndexes.size == 1) {
        handType = HandType.OnePair
    } else {
        handType = HandType.HighCard
    }

    val handScore = cards.reduce { acc, v -> (acc * 20) + v }
    val handFullScore = ((7 - handType.ordinal).toLong() shl 32) or handScore.toLong()

    return Hand(cards, bid, handType, handScore, handFullScore)
}

data class Hand(val cards: List<Int>, val bid: Int, val handType: HandType, val handScore: Int, val handFullScore: Long)
data class Board(val hands: List<Hand>)

fun parsePt1(inp: String): Board {
    val sp = inp.split("\n").map { l ->
        val s = l.split(" ")

        val cards = s[0].map { r -> cardStrengthPt1.indexOf(r) }
        calcHandPt1(cards, s[1].toInt())
    }

    return Board(sp)
}

fun parsePt2(inp: String): Board {
    val sp = inp.split("\n").map { l ->
        val s = l.split(" ")

        val cards = s[0].map { r -> cardStrengthPt2.indexOf(r) }
        calcHandPt2(cards, s[1].toInt())
    }

    return Board(sp)
}
