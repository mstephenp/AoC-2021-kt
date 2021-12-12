import day1.Day1
import day10.Day10
import day11.Day11
import day2.Day2
import day3.Day3
import day4.Day4
import day5.Day5
import day6.Day6
import day7.Day7
import day8.Day8
import day9.Day9

fun main() {
    dayList().map {
        println()
        println(it.first)
        println(it.second.solve())
        println()
    }
}

fun dayList(): List<Pair<String, AocDay>> {
    return listOf(
        "Day One" to Day1(),
        "Day Two" to Day2(),
        "Day Three" to Day3(),
        "Day Four" to Day4(),
        "Day Five" to Day5(),
        "Day Six" to Day6(),
        "Day Seven" to Day7(),
        "Day Eight" to Day8(),
        "Day Nine" to Day9(),
        "Day Ten" to Day10(),
        "Day Eleven" to Day11()
    )
}


