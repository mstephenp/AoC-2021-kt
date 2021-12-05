import day1.Day1
import day2.Day2
import day3.Day3
import day4.Day4

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
        "Day Four" to Day4()
    )
}


