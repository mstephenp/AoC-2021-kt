package day8

import AocDay
import java.io.File

class Day8 : AocDay {
    override fun solve() {
        File("./src/main/resources/day_eight_input.txt")
            .readLines().map { line ->
                line.split("|")
            }.map { note ->
                Note(
                    note[0].split(" ")
                        .filter { it.isNotEmpty() }
                        .map {
                            it.toCharArray().sorted().joinToString("")
                        },
                    note[1].split(" ")
                        .filter { it.isNotEmpty() }
                        .map {
                            it.toCharArray().sorted().joinToString("")
                        }
                )
            }.apply {
                val digitCount = this.sumOf { note ->
                    note.outputValue.count { value ->
                        when (value.length) {
                            2, 3, 4, 7 -> true
                            else -> false
                        }
                    }
                }
                println("Unique Digit Count: $digitCount")
                println("Sum of output values ${this.sumOf { it.getDigits() }}")
            }
    }
}

class Note(signalPattern: List<String>, val outputValue: List<String>) {
    private val numberMap = mutableMapOf<String, Int>()
    private val numberStringMap = mutableMapOf<Int, String>()

    init {
        // we are only concerned with 2 <= signal <= 7
        // loop twice, the first time look for 2,3,4,7 - we know what they are
        // then loop again and use those known numbers to find the others
        for (signal in signalPattern) {
            when (signal.length) {
                2 -> {
                    numberMap[signal] = 1
                    numberStringMap[1] = signal
                }
                3 -> {
                    numberMap[signal] = 7
                    numberStringMap[7] = signal
                }
                4 -> {
                    numberMap[signal] = 4
                    numberStringMap[4] = signal
                }
                7 -> {
                    numberMap[signal] = 8
                    numberStringMap[8] = signal
                }
            }
        }
        // second loop over the remaining numbers
        for (signal in signalPattern.filter { it !in numberMap.keys }) {
            when (signal.length) {
                5 -> {
                    // a signal of length 5 that contains the letters in our known value for 1 is a 3
                    if (signal.containsAll(numberStringMap[1]!!)) {
                        numberMap[signal] = 3
                        numberStringMap[3] = signal
                        // if it contains the values in the known value 4 minus the known value 1, it's a 5
                    } else if (signal.containsAll(numberStringMap[4]!!.filter { it !in numberStringMap[1]!! })) {
                        numberMap[signal] = 5
                        numberStringMap[5] = signal
                        // otherwise, it's a two
                    } else {
                        numberMap[signal] = 2
                        numberStringMap[2] = signal
                    }
                }
                6 -> {
                    // a signal of length 6 can be negated from the known value of 8 - there will be only one value remaining
                    // if the remainder is in the known value for 1, it must be a 6
                    if (numberStringMap[8]!!.filter { it !in signal } in numberStringMap[1]!!) {
                        numberMap[signal] = 6
                        numberStringMap[6] = signal
                        // if it is in the known value for 4, it must be a 0
                    } else if (numberStringMap[8]!!.filter { it !in signal } in numberStringMap[4]!!) {
                        numberMap[signal] = 0
                        numberStringMap[0] = signal
                        // otherwise, it is a 9
                    } else {
                        numberMap[signal] = 9
                        numberStringMap[9] = signal
                    }
                }
            }
        }
    }

    // using the map created on init, look up all the values, turn them into strings and concatenate them
    // then cast that new number back to an Int
    fun getDigits(): Int {
        return this.outputValue.map { numberMap[it]!! }.joinToString("").toInt()
    }
}

// extension function to help look for non-sequential matches in a string
// will find "ae" in "abcde"
private fun String.containsAll(s: String): Boolean {
    for (c in s) {
        if (!this.contains(c)) {
            return false
        }
    }
    return true
}
