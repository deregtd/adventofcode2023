package day24

import common.Point3
import common.Point3f
import common.extents
import kotlin.math.abs
import kotlin.math.round

fun main() {
    println("Part 1:")
    for (i in inputs1.indices) {
        val sample = inputs1[i]

        val board = parsePt1(sample.input)
//        println("board: $board")
        val sol = calcPt1(board, 7, 27)
        println("calced: $sol, Answer: ${sample.answer}, Correct: ${sample.answer == sol}")
    }

    val board1Mine = parsePt1(inputMine)
    println("Mine: ${calcPt1(board1Mine, 200000000000000, 400000000000000)}")

    println()
    println("Part 2:")

    for (i in inputs2.indices) {
        val sample = inputs2[i]

        val board = parsePt2(sample.input)
//        println("board: $board")
        val sol = calcPt2(board)
        println("calced: $sol, Answer: ${sample.answer}, Correct: ${sample.answer == sol}")
    }
//
//    val board2Mine = parsePt2(inputMine)
//    println("Mine: ${calcPt2(board2Mine)}")
}

fun calcPt1(inp: Board, minDim: Long, maxDim: Long): Long {
    var collisions = 0
    inp.s.forEachIndexed { i1, s1 ->
        for (i2 in (i1+1)..<inp.s.size) {
            val s2 = inp.s[i2]

//            xc = x1 + vx1*t1
//            yc = y1 + vy1*t1
//
//            xc = x2 + vx2*t2
//            yc = y2 + vy2*t2

//            x1 - x2 = vx2*t2 - vx1*t1
//            y1 - y2 = vy2*t2 - vy1*t1

            val t1 = (s2.vel.x*(s2.pos.y - s1.pos.y) + s2.vel.y*(s1.pos.x - s2.pos.x))/
                    (s2.vel.x*s1.vel.y - s1.vel.x*s2.vel.y + 0.00000000001)
            val t2 = (s1.vel.x*(s2.pos.y - s1.pos.y) + s1.vel.y*(s1.pos.x - s2.pos.x))/
                    (s2.vel.x*s1.vel.y - s1.vel.x*s2.vel.y + 0.00000000001)

            if (t1 < 0.1 || t2 < 0.1) {
                continue
            }

            val intersect = Point3f(s1.pos.x + s1.vel.x*t1, s1.pos.y + s1.vel.y*t1, 0.0)

            if (intersect.x >= minDim && intersect.x <= maxDim && intersect.y >= minDim && intersect.y <= maxDim) {
                collisions++
            }
        }
    }
    return collisions.toLong()
}

fun calcPt2(inp: Board): Long {
/*
Solve this:

px_s + vx_s*t_1 = px_1 + vx_1*t_1
py_s + vy_s*t_1 = py_1 + vy_1*t_1
pz_s + vz_s*t_1 = pz_1 + vz_1*t_1

px_s + vx_s*t_2 = px_2 + vx_2*t_2
py_s + vy_s*t_2 = py_2 + vy_2*t_2
pz_s + vz_s*t_2 = pz_2 + vz_2*t_2

px_s + vx_s*t_3 = px_3 + vx_3*t_3
py_s + vy_s*t_3 = py_3 + vy_3*t_3
pz_s + vz_s*t_3 = pz_3 + vz_3*t_3

Ended up stealing someone's Z3 code to do it since I don't want to sort out learning how to use Z3 and python

*/

//    val startPos: Point3f
//    val startV: Point3f

    //Pair(Point3f(24.0,13.0,10.0),Point3f(-3.0,1.0,2.0)
//    return round(startPos.x+startPos.y+startPos.z).toLong()
    return 0
}


data class Stone(var pos: Point3f, var vel: Point3f)
data class Board(var s: List<Stone>)

fun parsePt1(inp: String): Board {
    return Board(inp.split("\n").map{ r ->
        val sp = r.split(" @ ").map { v -> v.split(", ").map { n -> n.trim().toDouble() }}.map { pt -> Point3f(pt[0], pt[1], pt[2])}
        Stone(sp[0], sp[1])
    })
}

fun parsePt2(inp: String): Board {
    return parsePt1(inp)
}
