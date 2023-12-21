package day20

import common.findLCM

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
    var lowPulses = 0L
    var highPulses = 0L
    for (i in 1..1000) {
        val (lp, hp, _) = pressButton(inp)
        lowPulses += lp
        highPulses += hp
    }

    return lowPulses * highPulses
}

data class Pulse(val target: String, val from: String, val isHigh: Boolean)

// short pulses, long pulses
fun pressButton(inp: Board): Triple<Long, Long, List<String>> {
    val toWork = ArrayList<Pulse>()
    toWork.add(Pulse("broadcaster", "", false))
    var lp = 0L
    var hp = 0L
    var lxTargets = ArrayList<String>()
    while (toWork.isNotEmpty()) {
        val p = toWork.removeFirst()

        if (p.isHigh) {
            hp++
        } else {
            lp++
        }

        if (p.target == "lx" && p.isHigh) {
            lxTargets.add(p.from)
        }

        val m = inp.modules[p.target]
        if (m == null) {
            continue
        }
        when (m.type) {
            ModType.Broadcaster -> {
                m.targets.forEach { t ->
                    toWork.add(Pulse(t, m.name, p.isHigh))
                }
            }
            ModType.FlipFlop -> {
                if (p.isHigh) {
                    // ignore
                } else {
                    val sendHigh = !m.flipFlopState
                    m.flipFlopState = !m.flipFlopState
                    m.targets.forEach { t ->
                        toWork.add(Pulse(t, m.name, sendHigh))
                    }
                }
            }
            ModType.Conjunction -> {
                m.conjInputStatesHigh[p.from] = p.isHigh
                val sendHigh = !m.conjInputStatesHigh.all { v -> v.value }
                m.targets.forEach { t ->
                    toWork.add(Pulse(t, m.name, sendHigh))
                }
            }
        }
    }
    return Triple(lp, hp, lxTargets)
}

fun calcPt2(inp: Board): Long {
    var i = 1
    val targets = HashMap<String, Int>()
    while (true) {
        val (_, _, lxTargets) = pressButton(inp)

        for (lxt in lxTargets) {
            targets[lxt] = i
        }

        if (targets.size == 4) {
            break
        }

        i++
    }

    return findLCM(targets.values.map { v -> v.toLong() })
}

enum class ModType {
    Broadcaster,
    FlipFlop,
    Conjunction,
}

data class Module(val type: ModType, val name: String, val targets: List<String>, var flipFlopState: Boolean = false, var conjInputStatesHigh: HashMap<String, Boolean> = HashMap())

data class Board(val modules: Map<String, Module>)

fun parsePt1(inp: String): Board {
    var modules = inp.split("\n").map { r ->
        val sp = r.split(" -> ")
        val t = when (sp[0][0]) {
            '%' -> ModType.FlipFlop
            '&' -> ModType.Conjunction
            else -> ModType.Broadcaster
        }
        val name = if (t == ModType.Broadcaster) { sp[0] } else { sp[0].substring(1) }
        val targets = sp[1].split(", ")
        Module(t, name, targets)
    }

    modules.filter { m -> m.type == ModType.Conjunction }.forEach { m ->
        modules.filter { ma -> ma.targets.contains(m.name) }.forEach { ma ->
            m.conjInputStatesHigh[ma.name] = false
        }
    }

    return Board(modules.associateBy { m -> m.name })
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
