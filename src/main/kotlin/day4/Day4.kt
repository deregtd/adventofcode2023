package day4

fun main() {
    var cards1 = parseCards(input1)
    var cards1Winners = cards1.map(::winnerCount)
    var cards1Vals = cards1Winners.map(::valOfCard)
    var cardsMine = parseCards(inputMine)
    var cardsMineWinners = cardsMine.map(::winnerCount)
    var cardsMineVals = cardsMineWinners.map(::valOfCard)

    println("vals1Sum: ${cards1Vals.sum()}")
    println("valsMineSum: ${cardsMineVals.sum()}")

    println("vals1Count: ${countCards(cards1Winners)}")
    println("valsMineCount: ${countCards(cardsMineWinners)}")
}

fun countCards(cardVals: List<Int>): Int {
    val counts = IntArray(cardVals.size) { 1 }

    var scratchCount = 0
    for (i in cardVals.indices) {
        val v = cardVals[i]
        for (it in 1..counts[i]) {
            if (v > 0) {
                for (h in i + 1..i + v) {
                    counts[h]++
                }
            }
        }
        scratchCount += counts[i]
    }

    return scratchCount
}

fun parseCards(s: String): List<List<List<Int>>> {
    return s.split("\n").map { l ->
        val pt1 = l.split(": ")
        pt1[1].split(" | ").map(::strToList)
    }
}

fun strToList(s: String): List<Int> {
    val l = ArrayList<Int>()
    for (i in (0..s.length-2).step(3)) {
        l.add(s.substring(i, i+2).trim().toInt())
    }
    return l
}

fun winnerCount(card: List<List<Int>>): Int {
    return card[1].filter { i -> card[0].contains(i) }.size
}

fun valOfCard(winners: Int): Int {
    if (winners == 0) {
        return 0
    }
    return (1 shl (winners - 1))
}
