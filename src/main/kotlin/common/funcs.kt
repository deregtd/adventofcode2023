package common

import kotlin.math.min

data class Cycle(val firstIndex: Int, val vals: List<Int>)

fun findCycle(nums: List<Int>, minRepeats: Int = 2, minCycle: Int = 2, maxCycle: Int? = null): Cycle? {
    val maxCycleLen = maxCycle ?: ((nums.size) / minRepeats)
    for (i in nums.size-1 downTo 0) {
        val maxLen = min(maxCycleLen, ((nums.size - i) / minRepeats))
        for (cycleLen in minCycle..maxLen) {
            var foundFail = false
            for (h in 0..<cycleLen) {
                for (r in 1..<minRepeats)
                if (nums[i + h] != nums[i + r*cycleLen + h]) {
                    foundFail = true
                    break
                }
            }
            if (!foundFail) {
                return Cycle(i, nums.slice(i..<(i+cycleLen)))
            }
        }
    }
    return null
}

fun findLCM(nums: Collection<Long>): Long {
    return nums.fold(1) { acc, v -> findLCM(acc, v) }
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

// Mins, Maxes
fun extents(pts: List<Point3>): Pair<Point3, Point3> {
    if (pts.isEmpty()) {
        return Pair(Point3(0,0,0), Point3(0,0,0))
    }
    return Pair(Point3(pts.minBy { p -> p.x }.x, pts.minBy { p -> p.y }.y, pts.minBy { p -> p.z }.z),
        Point3(pts.maxBy { p -> p.x }.x, pts.maxBy { p -> p.y }.y, pts.maxBy { p -> p.z }.z))
}
