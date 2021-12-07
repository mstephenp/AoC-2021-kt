package day7

import AocDay
import java.io.File
import kotlin.math.abs

class Day7 : AocDay {
    override fun solve() {
        File("./src/main/resources/day_seven_input.txt")
            .readLines()
            .map { line ->
                val numberList = line.split(",").map { Integer.parseInt(it) }

                numberList.apply {
                    val distanceSums = (this.minOf { it }..this.maxOf { it }).map { diff ->
                        this.sumOf { num -> abs(num - diff) }
                    }
                    println("Sum of min distances: ${distanceSums.minOf { it }}")

                    val distanceSumsFuelUsage = (this.minOf { it }..this.maxOf { it }).map { diff ->
                        this.sumOf { num -> (0..abs(num - diff)).sum() }
                    }
                    println("Sum of min distances with fuel usage: ${distanceSumsFuelUsage.minOf { it }}")
                }
            }
    }
}
