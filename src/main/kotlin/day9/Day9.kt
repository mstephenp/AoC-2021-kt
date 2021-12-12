package day9

import AocDay
import java.io.File

data class Point(
    val height: Int,
    var isLowPoint: Boolean = false,
    var isBasinWall: Boolean = false,
    var hasBeenChecked: Boolean = false
)

fun Point.getRiskLevel(): Int = this.height + 1

enum class Direction {
    Up, Down, Left, Right, Init
}

class Day9 : AocDay {
    override fun solve() {
        File("./src/main/resources/day_nine_input.txt")
            .readLines()
            .map {
                it.split("").filter { s -> s.isNotEmpty() }
            }.apply {
                val pointMap = transformToPointMap(this)
                markLowPoints(pointMap)
                markBasinWalls(pointMap)
                getSumOfRiskLevels(pointMap)
                countAllBasins(pointMap)

                //printMapForFun(pointMap)
            }
    }

    private fun getSumOfRiskLevels(pointMap: List<List<Point>>) {
        pointMap.map { line ->
            line.filter { it.isLowPoint }
        }.apply {
            val riskLevelSum = this.sumOf { line ->
                line.sumOf { it.getRiskLevel() }
            }
            println("Sum of Risk Levels: $riskLevelSum")
        }
    }

    private fun printMapForFun(pointMap: List<List<Point>>) {
        pointMap.forEach { line ->
            line.forEach { point ->
                when {
                    point.isLowPoint -> print("+")
                    point.isBasinWall -> print("'")
                    else -> print(" ")
                }
            }
            println()
        }
    }

    private fun markBasinWalls(pointMap: List<List<Point>>) {
        pointMap.forEach { line ->
            line.forEach { point ->
                if (point.height == 9) {
                    point.isBasinWall = true
                }
            }
        }
    }

    private fun countAllBasins(pointMap: List<List<Point>>) {
        val basinSizeList = mutableListOf<Int>()
        pointMap.forEachIndexed { y, pointLine ->
            pointLine.forEachIndexed { x, point ->
                if (point.isLowPoint) {
                    basinSizeList.add(countBasin(x, y, pointMap, 0, Direction.Init))
                }
            }
        }
        val largest = basinSizeList.sorted().takeLast(3)
        val largestMultiplied = largest[0] * largest[1] * largest[2]

        println("Largest Three Basins Multiplied $largestMultiplied")
    }
}

private fun countBasin(x: Int, y: Int, pointMap: List<List<Point>>, count: Int, from: Direction): Int {
    if (pointMap[y][x].hasBeenChecked) {
        return 0
    } else if (pointMap[y][x].isBasinWall) {
        return 0
    } else {
        pointMap[y][x].hasBeenChecked = true
    }
    when (from) {
        Direction.Init -> {
            val aboveCount = when {
                (y - 1) >= 0 -> countBasin(x, y - 1, pointMap, count + 1, Direction.Up)
                else -> 0
            }
            val belowCount = when {
                (y + 1) < pointMap.size -> countBasin(x, y + 1, pointMap, count + 1, Direction.Down)
                else -> 0
            }
            val leftCount = when {
                (x - 1) >= 0 -> countBasin(x - 1, y, pointMap, count + 1, Direction.Left)
                else -> 0
            }
            val rightCount = when {
                (x + 1) < pointMap[y].size -> countBasin(x + 1, y, pointMap, count + 1, Direction.Right)
                else -> 0
            }
            return 1 + aboveCount + belowCount + leftCount + rightCount
        }
        Direction.Up -> {
            val aboveCount = when {
                (y - 1) >= 0 -> countBasin(x, y - 1, pointMap, count + 1, Direction.Up)
                else -> 0
            }
            val leftCount = when {
                (x - 1) >= 0 -> countBasin(x - 1, y, pointMap, count + 1, Direction.Left)
                else -> 0
            }
            val rightCount = when {
                (x + 1) < pointMap[y].size -> countBasin(x + 1, y, pointMap, count + 1, Direction.Right)
                else -> 0
            }
            return 1 + aboveCount + leftCount + rightCount
        }
        Direction.Down -> {
            val belowCount = when {
                (y + 1) < pointMap.size -> countBasin(x, y + 1, pointMap, count + 1, Direction.Down)
                else -> 0
            }
            val leftCount = when {
                (x - 1) >= 0 -> countBasin(x - 1, y, pointMap, count + 1, Direction.Left)
                else -> 0
            }
            val rightCount = when {
                (x + 1) < pointMap[y].size -> countBasin(x + 1, y, pointMap, count + 1, Direction.Right)
                else -> 0
            }
            return 1 + belowCount + leftCount + rightCount
        }
        Direction.Right -> {
            val aboveCount = when {
                (y - 1) >= 0 -> countBasin(x, y - 1, pointMap, count + 1, Direction.Up)
                else -> 0
            }
            val belowCount = when {
                (y + 1) < pointMap.size -> countBasin(x, y + 1, pointMap, count + 1, Direction.Down)
                else -> 0
            }
            val rightCount = when {
                (x + 1) < pointMap[y].size -> countBasin(x + 1, y, pointMap, count + 1, Direction.Right)
                else -> 0
            }
            return 1 + aboveCount + belowCount + rightCount
        }
        Direction.Left -> {
            val aboveCount = when {
                (y - 1) >= 0 -> countBasin(x, y - 1, pointMap, count + 1, Direction.Up)
                else -> 0
            }
            val belowCount = when {
                (y + 1) < pointMap.size -> countBasin(x, y + 1, pointMap, count + 1, Direction.Down)
                else -> 0
            }
            val leftCount = when {
                (x - 1) >= 0 -> countBasin(x - 1, y, pointMap, count + 1, Direction.Left)
                else -> 0
            }
            return 1 + aboveCount + belowCount + leftCount
        }
    }
}

private fun markLowPoints(pointMap: List<List<Point>>) {
    pointMap.forEachIndexed { y, pointLine ->
        pointLine.forEachIndexed { x, _ ->
            when (y) {
                0 -> markPointOnTopRow(x, pointMap)
                pointMap.size - 1 -> markPointOnBottomRow(x, pointMap)
                else -> markPoint(x, y, pointMap)
            }
        }
    }
}

private fun markPoint(x: Int, y: Int, pointMap: List<List<Point>>) {
    val p = pointMap[y][x]
    when (x) {
        0 -> {
            val adjacentValues = mutableListOf<Int>()
            adjacentValues.add(pointMap[y][1].height)
            adjacentValues.add(pointMap[y + 1][0].height)
            adjacentValues.add(pointMap[y - 1][0].height)
            p.isLowPoint = adjacentValues.map { it > p.height }.all { it }
        }
        pointMap[y].size - 1 -> {
            val adjacentValues = mutableListOf<Int>()
            adjacentValues.add(pointMap[y][x - 1].height)
            adjacentValues.add(pointMap[y + 1][x].height)
            adjacentValues.add(pointMap[y - 1][x].height)
            p.isLowPoint = adjacentValues.map { it > p.height }.all { it }
        }
        else -> {
            val adjacentValues = mutableListOf<Int>()
            adjacentValues.add(pointMap[y][x - 1].height)
            adjacentValues.add(pointMap[y][x + 1].height)
            adjacentValues.add(pointMap[y - 1][x].height)
            adjacentValues.add(pointMap[y + 1][x].height)
            p.isLowPoint = adjacentValues.map { it > p.height }.all { it }
        }
    }
}


private fun markPointOnBottomRow(x: Int, pointMap: List<List<Point>>) {
    val y = pointMap.size - 1
    val p = pointMap[y][x]
    when (x) {
        0 -> {
            val adjacentValues = mutableListOf<Int>()
            adjacentValues.add(pointMap[y][1].height)
            adjacentValues.add(pointMap[y - 1][0].height)
            p.isLowPoint = adjacentValues.map { it > p.height }.all { it }
        }
        pointMap[y].size - 1 -> {
            val adjacentValues = mutableListOf<Int>()
            adjacentValues.add(pointMap[y][x - 1].height)
            adjacentValues.add(pointMap[y - 1][x].height)
            p.isLowPoint = adjacentValues.map { it > p.height }.all { it }
        }
        else -> {
            val adjacentValues = mutableListOf<Int>()
            adjacentValues.add(pointMap[y][x - 1].height)
            adjacentValues.add(pointMap[y][x + 1].height)
            adjacentValues.add(pointMap[y - 1][x].height)
            p.isLowPoint = adjacentValues.map { it > p.height }.all { it }
        }
    }
}

private fun markPointOnTopRow(x: Int, pointMap: List<List<Point>>) {
    val p = pointMap[0][x]
    when (x) {
        0 -> {
            val adjacentValues = mutableListOf<Int>()
            adjacentValues.add(pointMap[0][1].height)
            adjacentValues.add(pointMap[1][0].height)
            p.isLowPoint = adjacentValues.map { it > p.height }.all { it }
        }
        pointMap[0].size - 1 -> {
            val adjacentValues = mutableListOf<Int>()
            adjacentValues.add(pointMap[0][x - 1].height)
            adjacentValues.add(pointMap[1][x].height)
            p.isLowPoint = adjacentValues.map { it > p.height }.all { it }
        }
        else -> {
            val adjacentValues = mutableListOf<Int>()
            adjacentValues.add(pointMap[0][x - 1].height)
            adjacentValues.add(pointMap[0][x + 1].height)
            adjacentValues.add(pointMap[1][x].height)
            p.isLowPoint = adjacentValues.map { it > p.height }.all { it }
        }
    }
}

private fun transformToPointMap(stringMap: List<List<String>>): List<List<Point>> {
    val pointList = mutableListOf<List<Point>>()
    stringMap.forEach { line -> pointList.add(line.map { Point(it.toInt()) }) }
    return pointList
}