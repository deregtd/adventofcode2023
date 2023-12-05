package day5

val maps = arrayOf("seed", "soil", "fertilizer", "water", "light", "temperature", "humidity", "location")

fun main() {
    val board1 = parse(input1)
    val boardMine = parse(inputMine)

    println("part11: ${calcPt1(board1)}")
    println("part1Mine: ${calcPt1(boardMine)}")

    println("part21: ${calcPt2(board1)}")
    println("part2Mine: ${calcPt2(boardMine)}")
}

fun calcPt2(board: Board): Long {
    var ranges = ArrayList<LongRange>()
    for (i in 0..<board.seeds.size step 2) {
        ranges.add(board.seeds[i]..<board.seeds[i]+board.seeds[i+1])
    }

    for (i in 1..<maps.size) {
        val mapRanges = board.maps[maps[i-1]+"-to-"+maps[i]] ?: throw Exception("bad")

        var nextRanges = ArrayList<LongRange>()
        for (r in ranges) {
            val mappedRanges = mapRangeList(r, mapRanges)
            nextRanges.addAll(mappedRanges)
        }
        ranges = nextRanges
    }

    return ranges.map { r -> r.start }.min()
}

fun calcPt1(board: Board): Long {
    var round = board.seeds
    for (i in 1..<maps.size) {
        round = round.map{v -> map(board, maps[i-1], maps[i], v)}
    }
    return round.min()
}

data class SrcDestRange(val srcStart: Long, val destStart: Long, val length: Long, val srcRange: LongRange)
data class Board(val seeds: List<Long>, val maps: Map<String, List<SrcDestRange>>)

fun parse(inp: String): Board {
    val sp = inp.split("\n\n")
    val seeds = sp[0].split(": ")[1].split(" ").map{ m -> m.toLong() }

    var x = 1..7

    val m = HashMap<String, List<SrcDestRange>>()
    for (i in 1..7) {
        val sp2 = sp[i].split(" map:\n")
        val name = sp2[0]
        val ranges = sp2[1].split("\n").map { r -> r.split(" ").map{  v -> v.toLong() }}
        m[name] = ranges.map { r -> SrcDestRange(r[1], r[0], r[2], r[1]..<r[1]+r[2]) }
    }

    return Board(seeds, m)
}

fun map(board: Board, srcType: String, destType: String, num: Long): Long {
    val ranges = board.maps[srcType+"-to-"+destType] ?: throw Exception("bad")
    return mapList(num, ranges)
}

fun mapList(num: Long, ranges: List<SrcDestRange>): Long {
    for (r in ranges) {
        if (num in r.srcRange) {
            return r.destStart + (num - r.srcStart)
        }
    }
    return num
}

fun mapRangeList(inRange: LongRange, ranges: List<SrcDestRange>): List<LongRange> {
    var out = ArrayList<LongRange>()

    var lastOut = inRange.start
    for (r in ranges) {
        if (r.srcRange.start <= inRange.endInclusive && r.srcRange.endInclusive >= inRange.start) {
            if (r.srcRange.start > lastOut) {
                // Catch up from outside, then catches up to start of range
                out.add(lastOut..<r.srcRange.start)
                lastOut = r.srcRange.start
            }

            val startOffset = lastOut - r.srcRange.start
            if (r.srcRange.endInclusive <= inRange.endInclusive) {
                // Checking range ends before end of input range, so grab the whole rest of the map range
                out.add(r.destStart+startOffset..<r.destStart+r.length)
                lastOut = r.srcStart+r.length
                if (lastOut > inRange.endInclusive) {
                    return out
                }
            } else {
                // Input ends before end of maprange
                val endOffset = inRange.endInclusive - lastOut
                out.add(r.destStart + startOffset..<r.destStart+startOffset+endOffset)
                return out
            }
        }
    }

    // Catch up at the end
    out.add(lastOut..inRange.endInclusive)

    return out
}