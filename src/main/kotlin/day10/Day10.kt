package day10

import AocDay
import java.io.File
import java.util.*

class Day10 : AocDay {

    private val closingCharLookup = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )
    private val illegalCharScoreLookup = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    private val legalCharScoreLookup = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    override fun solve() {
        File("./src/main/resources/day_ten_input.txt")
            .readLines()
            .apply {
                val illegalChars = this.map(::countIllegalChars).filter { it.isPresent }.map { it.get() }
                val errorScore = illegalChars.sumOf { illegalCharScoreLookup[it]!! }
                println("Error score $errorScore")

                val completionStrings = this.filter { countIllegalChars(it).isEmpty }.map(::getCompletionString)
                val completionStringScore = completionStrings.map { getScore(it) }.sorted()
                val middleScore = getMiddleScore(completionStringScore)
                println("Middle Score $middleScore")
            }
    }

    private fun getMiddleScore(completionStringScore: List<Long>): Long {
        return completionStringScore
            .drop(completionStringScore.size / 2)
            .dropLast(completionStringScore.size / 2)[0]
    }

    private fun getScore(chars: Array<Char>): Long {
        var total = 0L
        for (c in chars.reversed()) {
            total = (5 * total) + legalCharScoreLookup[c]!!
        }
        return total
    }


    private fun getCompletionString(line: String): Array<Char> {
        var expected = arrayOf<Char>()

        for (c in line) {
            expected = when (c) {
                in closingCharLookup.keys -> expected.plus(closingCharLookup[c]!!)
                else -> expected.dropLast(1).toTypedArray()
            }
        }
        return expected
    }


    private fun countIllegalChars(line: String): Optional<Char> {
        var expected = arrayOf<Char>()

        for (c in line) {
            expected = when (c) {
                in closingCharLookup.keys -> expected.plus(closingCharLookup[c]!!)
                else -> when {
                    c != expected.last() -> return Optional.of(c)
                    else -> expected.dropLast(1).toTypedArray()
                }
            }
        }
        return Optional.empty()
    }
}