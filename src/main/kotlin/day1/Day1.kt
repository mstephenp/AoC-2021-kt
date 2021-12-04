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
                val increaseCount = this.fold(0 to this[0]) { acc, n ->
                    when {
                        n > acc.second -> (acc.first + 1) to n
                        else -> acc.first to n
                    }
                }.first

                val increaseCountWindowed = this.windowed(3)
                    .map { it.sum() }
                    .fold(0 to 0) { acc, n ->
                        when {
                            acc.second == 0 -> acc.first to n
                            acc.second < n -> (acc.first + 1) to n
                            else -> acc.first to n
                        }
                    }.first

                println("Increases: $increaseCount")
                println("Windowed Increases: $increaseCountWindowed")
            }
    }
}