package day1

fun main() {
    println("input1: ${calcOutput(input1)}")
    println("input2: ${calcOutput(input2)}")
    println("inputMine: ${calcOutput(inputMine)}")
}

fun calcOutput(inp: String): Int {
    val lines = inp.split("\n")
    val firsts = lines.map(::firstDigit)
    val lasts = lines.map(::lastDigit)
    val items = firsts.mapIndexed { idx, first -> first * 10 + lasts[idx] }
    return items.reduce { acc, next -> acc + next }
}

fun firstDigit(inp: String): Int {
    for (i in 0..<inp.length) {
        val (valid, char) = isDigitInt(inp, i)
        if (valid) {
            return char
        }
    }
    throw Exception("nope")
//    return inp.first { c -> c.isDigit() }.digitToInt()
}

fun lastDigit(inp: String): Int {
    for (i in (inp.length - 1) downTo 0) {
        val (valid, char) = isDigitInt(inp, i)
        if (valid) {
            return char
        }
    }
    throw Exception("nope")
//    return inp.last { c -> c.isDigit() }.digitToInt()
}

val digits = listOf<String>("one","two","three","four","five","six","seven","eight","nine")

fun isDigitInt(inp: String, i: Int): Pair<Boolean, Int> {
    if (inp[i].isDigit()) {
        return Pair(true, inp[i].digitToInt())
    }
    for (digitIdx in digits.indices) {
        val digit = digits[digitIdx]
        if (i <= inp.length - digit.length) {
            if (inp.substring(i, i + digit.length) == digit) {
                return Pair(true, digitIdx + 1)
            }
        }
    }
    return Pair(false, 0)
}
