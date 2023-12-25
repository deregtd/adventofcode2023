package day25

import java.lang.Math.random

fun main() {
    for (i in inputs1.indices) {
        val sample = inputs1[i]

        val board = parsePt1(sample.input)
//        println("board: $board")
        val sol = calcPt1(board)
        println("calced: $sol, Answer: ${sample.answer}, Correct: ${sample.answer == sol}")
    }

    val board1Mine = parsePt1(inputMine)
    println("Mine: ${calcPt1(board1Mine)}")
}

fun calcPt1(inp: Board): Long {
    while (true) {
        val groups = karger(inp)
        return (groups[0].size * groups[1].size).toLong()
    }
}

fun karger(inp: Board): List<List<Component>> {
    val vertCountAll = inp.comps.values.size
    val edgesAll = HashSet<Pair<String,String>>()

    for (c in inp.comps) {
        for (conn in c.value.connected) {
            val pair = Pair(c.key, conn)
            if (!edgesAll.contains(pair) && !edgesAll.contains(Pair(pair.second, pair.first))) {
                edgesAll.add(pair)
            }
        }
    }

    val edges = ArrayList(edgesAll)
    val edgeCount = edgesAll.size

    while (true) {
        var vertCount = vertCountAll
        val sets = HashMap<Component, MutableList<Component>>()
        while (vertCount > 2) {
            val edge = edges[(random()*edgeCount).toInt()]

            val v1 = inp.comps[edge.first]!!
            val v2 = inp.comps[edge.second]!!
            var coll1 = sets[v1]
            val coll2 = sets[v2]
            if (coll1 != null && coll1 == coll2) {
                continue
            }

            if (coll1 == null) {
                coll1 = mutableListOf(v1)
                sets[v1] = coll1
            }
            if (coll2 == null) {
                coll1.add(v2)
                sets[v2] = coll1
            } else {
                for (c in coll2) {
                    coll1.add(c)
                    sets[c] = coll1
                }
            }

            vertCount--
        }

        val crossingEdges = edges.filter { edge ->
            val v1 = inp.comps[edge.first]!!
            val v2 = inp.comps[edge.second]!!
            val coll1 = sets[v1]
            val coll2 = sets[v2]
            coll1 != coll2
        }
        if (crossingEdges.size == 3) {
            val uniqueSets = sets.values.distinct()
            return uniqueSets
        }
    }
}

//fun calcPt1BruteForce(inp: Board): Long {
//    val pairs = HashSet<Pair<String,String>>()
//
//    for (c in inp.comps) {
//        for (conn in c.value.connected) {
//            val pair1 = Pair(c.key, conn)
//            val pair2 = Pair(c.key, conn)
//            if (!pairs.contains(pair1) && !pairs.contains(pair2)) {
//                pairs.add(pair1)
//            }
//        }
//    }
//    val tried = HashSet<HashSet<Pair<String,String>>>()
//
//    for (c1 in pairs) {
//        println(c1.first)
//        for (c2 in pairs) {
//            if (c2 == c1) {
//                continue
//            }
//            for (c3 in pairs) {
//                if (c3 == c1 || c3 == c2) {
//                    continue
//                }
//                val tset = hashSetOf(c1, c2, c3, Pair(c1.second, c1.first), Pair(c2.second, c2.first), Pair(c3.second, c3.first))
//                if (tried.contains(tset)) {
//                    continue
//                }
//                tried.add(tset)
//                val groups = calcGroups(inp, tset)
//                if (groups.size > 1) {
//                    return (groups[0].size * groups[1].size).toLong()
//                }
//            }
//        }
//    }
//    return 0
//}

//fun calcGroups(inp: Board, without: HashSet<Pair<String, String>>): List<List<Component>> {
//    val setLookup = HashMap<String,List<Component>>()
//    val sets = mutableListOf<List<Component>>()
//
//    for (c in inp.comps) {
//        if (setLookup.containsKey(c.key)) {
//            continue
//        }
//
//        val set = mutableListOf<Component>()
//        sets.add(set)
//
//        val toWalk = mutableListOf(c.value)
//        while (toWalk.isNotEmpty()) {
//            val el = toWalk.removeLast()
//            if (setLookup.containsKey(el.name)) {
//                continue
//            }
//
//            set.add(el)
//            setLookup[el.name] = set
//
//            for (connected in el.connected) {
//                if (setLookup.containsKey(connected)) {
//                    continue
//                }
//                if (without.contains(Pair(el.name, connected))) {
//                    continue
//                }
//
//                toWalk.add(inp.comps[connected]!!)
//            }
//        }
//    }
//
//    return sets
//}

data class Component(val name: String, val connected: HashSet<String>)

data class Board(var comps: Map<String, Component>)

fun parsePt1(inp: String): Board {
    val comps = inp.split("\n").map{ r ->
        val sp = r.split(": ")
        Component(sp[0], HashSet(sp[1].split(" ")))
    }

    val map = HashMap(comps.associateBy { c -> c.name })
    for (c in comps) {
        for (conn in c.connected) {
            if (!map.containsKey(conn)) {
                map[conn] = Component(conn, listOf(c.name).toHashSet())
            } else {
                if (!map[conn]!!.connected.contains(c.name)) {
                    map[conn]!!.connected.add(c.name)
                }
            }
        }
    }

    return Board(map)
}
