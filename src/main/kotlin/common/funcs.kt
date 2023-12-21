package common

import kotlin.math.min

data class Cycle(val firstIndex: Int, val vals: List<Int>)

fun findCycle(nums: List<Int>, maxCycle: Int? = null): Cycle? {
    val maxCycleLen = maxCycle ?: ((nums.size) / 2)
    for (i in nums.size-1 downTo 0) {
        val maxLen = min(maxCycleLen, ((nums.size - i) / 2))
        for (cycleLen in 2..maxLen) {
            var foundFail = false
            for (h in 0..<cycleLen) {
                if (nums[i + h] != nums[i + maxLen + h]) {
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
