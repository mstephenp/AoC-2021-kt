package day1

import AocDay
import java.io.File

class Day1 : AocDay {
    override fun solve() {
        File("./src/main/resources/day_one_input.txt")
            .readLines()
            .map {
                Integer.parseInt(it)
            }.apply {
                println("Part One Answer: ${getIncreaseDepthCount(this)}")
                println("Part Two Answer: ${getIncreaseDepthCount(this.windowed(3).map { it.sum() })}")
            }
    }

    private fun getIncreaseDepthCount(depths: List<Int>): Int {
        var count = 0
        var previousDepth = depths[0]
        depths.forEach {
            if (it > previousDepth) {
                count += 1
            }
            previousDepth = it
        }
        return count
    }
}