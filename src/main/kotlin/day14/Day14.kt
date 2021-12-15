package day14

import AocDay
import java.io.File

class Day14 : AocDay {
    override fun solve() {
        val pairInsertionMap = mutableMapOf<String, String>()
        val parts = File("./src/main/resources/day_fourteen_input_test.txt")
            .readText()
            .split("\r\n\r\n")
        val template = parts[0]
        parts[1].split("\r\n").map {
            val pairInsertionParts = it.split(" -> ")
            pairInsertionMap[pairInsertionParts[0]] = pairInsertionParts[1]
        }

        val (most, least) = countElements(
            doSteps(10, template, pairInsertionMap),
            pairInsertionMap.values.toSet()
        )
        println("Answer: ${most - least}")
    }

    private fun countElements(outString: String, values: Set<String>): Pair<Int, Int> {
        val counts = values.map { c ->
            outString.count { it == c[0] }
        }
        return Pair(counts.maxOf { it }, counts.minOf { it })
    }

    private fun doSteps(count: Int, template: String, pairInsertionMap: MutableMap<String, String>): String {
        return when (count) {
            0 -> template
            else -> doSteps(count - 1, step(template, pairInsertionMap), pairInsertionMap)
        }
    }

    private fun step(template: String, pairInsertionMap: MutableMap<String, String>): String {
        return template.windowed(2).joinToString("") { pair ->
            pair[0] + pairInsertionMap[pair]!!
        }.plus(template[template.lastIndex])
    }
}