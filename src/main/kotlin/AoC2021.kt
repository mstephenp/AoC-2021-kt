import day1.Day1
import day2.Day2

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
        "Day Two" to Day2()
    )
}


