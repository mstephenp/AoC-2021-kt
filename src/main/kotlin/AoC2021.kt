import day11.Day11

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
//        "Day One" to Day1(),
//        "Day Two" to Day2(),
//        "Day Three" to Day3(),
//        "Day Four" to Day4(),
//        "Day Five" to Day5(),
//        "Day Six" to Day6(),
//        "Day Seven" to Day7(),
//        "Day Eight" to Day8(),
//        "Day Nine" to Day9(),
//        "Day Ten" to Day10(),
        "Day Eleven" to Day11()
    )
}


