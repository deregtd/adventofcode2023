package day2

import kotlin.math.max

fun main() {
    val inp1 = parseGames(input1)
    println("addedValids1: ${addedValids(inp1)}")
    val inpMine = parseGames(inputMine)
    println("addedValidsMine: ${addedValids(inpMine)}")
    println("addedPowers1: ${addedPowers(inp1.map(::maxRoll).map(::powerRoll))}")
    println("addedPowersMine: ${addedPowers(inpMine.map(::maxRoll).map(::powerRoll))}")
}

fun addedValids(inp: List<Game>): Int {
    val valid1 = inp.filter(::isGameValid)
    return valid1.map { v -> v.id }.sum()
}

fun isGameValid(inp: Game): Boolean {
    return inp.rolls.all{ r -> r.red <= 12 && r.green <= 13 && r.blue <= 14 }
}

fun maxRoll(inp: Game): Roll {
    var roll = Roll(0, 0, 0)
    inp.rolls.forEach { r ->
        roll.red = max(roll.red, r.red)
        roll.green = max(roll.green, r.green)
        roll.blue = max(roll.blue, r.blue)
    }
    return roll
}

fun powerRoll(inp: Roll): Int {
    return inp.red * inp.green * inp.blue
}

fun addedPowers(inp: List<Int>): Int {
    return inp.sum()
}

data class Roll(var red: Int, var green: Int, var blue: Int)
data class Game(val id: Int, val rolls: List<Roll>)

fun parseGames(inp: String): List<Game> {
    return inp.split("\n").map(::parseGame)
}

fun parseGame(inp: String): Game {
    val pts = inp.split(": ")
    val pt1Sp = pts[0].split(" ")
    val gameId = pt1Sp[1].toInt()

    val rollsRaw = pts[1].split("; ")
    val rolls = rollsRaw.map{ r ->
        val rpt = r.split(", ").map { rp -> rp.split(" ") }
        val rpt2 = rpt.map { rp -> Pair(rp[0].toInt(), rp[1]) }
        var red = 0
        var green = 0
        var blue = 0
        rpt2.forEach { (count, color) ->
            when (color) {
                "red" -> { red = count }
                "green" -> { green = count }
                "blue" -> { blue = count }
            }
        }
        Roll(red, green, blue)
    }

    return Game(gameId, rolls)
}