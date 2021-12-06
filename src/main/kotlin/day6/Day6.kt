package day6

import AocDay
import java.io.File

data class Generation(val count: Long, var age: Int)

class Day6 : AocDay {
    override fun solve() {
        File("./src/main/resources/day_six_input.txt")
            .readLines()
            .apply {
                assert(this.size == 1)
                val lanternfishAges = this[0].split(",").map { Integer.parseInt(it) }

                countAfter(80, lanternfishAges)
                countAfter(256, lanternfishAges)
            }
    }

    private fun countAfter(days: Long, input: List<Int>) {
        var firstGeneration = input
        var dayCount = days
        val generations = mutableListOf<Generation>()

        while (dayCount > 0) {
            var newCount: Long = 0
            val firstGenerationNextAges = mutableListOf<Int>()

            // increment the first generation, adding the number created for the next generation
            for (age in firstGeneration) {
                if (age == 0) {
                    newCount += 1
                    firstGenerationNextAges.add(6)
                } else {
                    firstGenerationNextAges.add(age - 1)
                }
            }
            firstGeneration = firstGenerationNextAges

            // if there are other generations, check those as well
            for (generation in generations) {
                if (generation.age == 0) {
                    newCount += generation.count
                    generation.age = 6
                } else {
                    generation.age -= 1
                }
            }

            // if there are new ones, add a new generation for them
            // all of them will increase and make more at the same rate
            if (newCount > 0) {
                generations.add(Generation(newCount, 8))
            }
            dayCount -= 1
        }
        val generationsCount = generations.sumOf { generation ->
            generation.count
        }
        println("Lanternfish count after $days days: ${firstGeneration.size + generationsCount}")
    }
}