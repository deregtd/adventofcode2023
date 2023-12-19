package day19

data class Point(var x: Int, var y: Int)

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
    val res = inp.parts.map { p ->
        var wkn = "in"
w@      while (true) {
            if (wkn == "A") {
                return@map true
            } else if (wkn == "R") {
                return@map false
            }

            val w = inp.workflowLookup[wkn]!!
            for (r in w.rules) {
                val o = r.match(p)
                if (o != null) {
                    wkn = o
                    continue@w
                }
            }
            throw Exception()
        }
        throw Exception()
    }
    val rat = res.mapIndexed { i, r ->
        if (!r) {
            return@mapIndexed 0
        }

        inp.parts[i].a+inp.parts[i].s+inp.parts[i].m+inp.parts[i].x
    }
    return rat.sum().toLong()
}

data class ToCheck(val wkn: String, val pr: PartRange)

fun calcPt2(inp: Board): Long {
    val accepts = mutableListOf<PartRange>()
    val toCheck = mutableListOf(ToCheck("in", PartRange(hashMapOf('x' to 1..4000, 'm' to 1..4000, 'a' to 1..4000, 's' to 1..4000))))
    while (toCheck.isNotEmpty()) {
        val tc = toCheck.removeAt(0)
        if (tc.wkn == "A") {
            accepts.add(tc.pr)
            continue
        } else if (tc.wkn == "R") {
            continue
        }

        val w = inp.workflowLookup[tc.wkn]!!
        var pra = tc.pr
        for (r in w.rules) {
            val (acc, rej) = r.match(pra)
            if (acc != null) {
                toCheck.add(ToCheck(r.result, acc))
            }
            if (rej == null) {
                break
            }
            pra = rej
        }
    }

    return accepts.map { a -> a.combos() }.sum()
}

class PartRange(val pts: HashMap<Char,IntRange>) {
    fun combos(): Long {
        var c = 1L
        for (r in pts) {
            c *= r.value.endInclusive - r.value.start + 1
        }
        return c
    }
}

data class Part (val x: Int, val m: Int, val a: Int, val s: Int)

enum class Op {
    None,
    GT,
    LT,
}

class Rule(
    val cat: Char,
    val op: Op,
    val operand: Int,
    val result: String) {

    // Accept ranges, reject ranges
    fun match(p: PartRange): Pair<PartRange?, PartRange?> {
        if (op == Op.None) {
            return Pair(p, null)
        }

        val rng = p.pts[cat]!!
        val s = rng.start
        val e = rng.endInclusive
        val ra: IntRange
        val rr: IntRange
        if (op == Op.LT) {
            ra = s..<operand
            rr = operand..e
        } else {
            ra = (operand+1)..e
            rr = s..operand
        }

        val ha = HashMap(p.pts)
        ha[cat] = ra
        val hr = HashMap(p.pts)
        hr[cat] = rr

        return Pair(
            if (ra.isEmpty()) { null } else { PartRange(ha) },
            if (rr.isEmpty()) { null } else { PartRange(hr) },
        )
    }

    fun match(p: Part): String? {
        if (op == Op.None) {
            return result
        }

        val ck = when (cat) {
            'x' -> p.x
            'a' -> p.a
            's' -> p.s
            'm' -> p.m
            else -> throw Exception()
        }

        val worked = when (op) {
            Op.LT -> ck < operand
            Op.GT -> ck > operand
            else -> throw Exception()
        }

        return if (worked) {
            result
        } else {
            null
        }
    }
}

data class Workflow(val name:String, val rules: List<Rule>)

data class Board(val workflows: List<Workflow>, val workflowLookup: HashMap<String, Workflow>, val parts: List<Part>)

fun parsePt1(inp: String): Board {
    val sp = inp.split("\n\n")
    val workflows = sp[0].split("\n").map { r ->
        val spa = r.split("{")
        val name = spa[0]
        val rsp = spa[1].substring(0..(spa[1].length-2)).split(",").map { rl ->
            val risp = rl.split(":")
            if (risp.size == 1) {
                Rule(' ', Op.None, 0, risp[0])
            } else {
                Rule(risp[0][0], if (risp[0][1] == '>') { Op.GT } else { Op.LT }, risp[0].substring(2).toInt(), risp[1])
            }
        }
        Workflow(name, rsp)
    }

    val wl = HashMap<String, Workflow>()
    workflows.forEach { w -> wl[w.name] = w }

    val parts = sp[1].split("\n").map { p ->
        val map = HashMap<Char,Int>()
        p.substring(1,p.length-1).split(",").forEach { v ->
            val psp = v.split("=")
            map[psp[0][0]] = psp[1].toInt()
        }
        Part(map['x']!!, map['m']!!, map['a']!!, map['s']!!)
    }

    return Board(workflows, wl, parts)
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
