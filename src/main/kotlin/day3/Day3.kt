package day3

import AocDay
import java.io.File

class Day3 : AocDay {
    override fun solve() {
        File("./src/main/resources/day_three_input.txt")
            .readLines()
            .apply {
                calculatePowerConsumption(this)
                calculateLifeSupportRating(this)
            }
    }

    private fun calculatePowerConsumption(report: List<String>) {
        val width = report[0].length
        assert(report.none { it.length != width })

        var gammaRate = ""
        var epsilonRate = ""
        (0 until width).forEach { index ->
            val oneCount = report.count { it[index] == '1' }
            val zeroCount = report.count { it[index] == '0' }

            when {
                oneCount >= zeroCount -> {
                    gammaRate += '1'
                    epsilonRate += '0'
                }
                else -> {
                    gammaRate += '0'
                    epsilonRate += '1'
                }
            }
        }
        val gamma = Integer.parseInt(gammaRate, 2)
        val epsilon = Integer.parseInt(epsilonRate, 2)

        println("Power Consumption: ${gamma * epsilon}")
    }

    private fun calculateLifeSupportRating(report: List<String>) {
        val o2Rating = getRating(report, 0, true)
        val co2Rating = getRating(report, 0, false)

        println("Life Support Rating: ${o2Rating * co2Rating}")
    }

    private fun getRating(report: List<String>, index: Int, isO2: Boolean): Int {
        return when (report.size) {
            1 -> Integer.parseInt(report[0], 2)
            else -> {
                val oneCount = report.count { it[index] == '1' }
                val zeroCount = report.count { it[index] == '0' }

                if (oneCount >= zeroCount) {
                    getRating(report.filter { it[index] == if (isO2) '1' else '0' }, index + 1, isO2)
                } else {
                    getRating(report.filter { it[index] == if (isO2) '0' else '1' }, index + 1, isO2)
                }
            }
        }
    }
}