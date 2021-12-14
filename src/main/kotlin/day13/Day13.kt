package day13

import AocDay
import java.io.File

enum class Fold {
    Up, Right
}

class Day13 : AocDay {
    override fun solve() {
        val parts = File("./src/main/resources/day_thirteen_input.txt")
            .readText()
            .split("\r\n\r\n")

        check(parts.size == 2)

        val (dotsInput, foldsInput) = parts[0] to parts[1]

        val dotMap = getDotMap(dotsInput)
        val folds = getFolds(foldsInput)

        val out = doFold(dotMap, folds[0])
        val count = out.sumOf { line -> line.count { it } }
        println("Visible Dots: $count")

        val result = doFolds(dotMap, folds)

        result.forEach { line ->
            line.forEach {
                print(
                    if (it) {
                        "0"
                    } else {
                        " "
                    }
                )
            }
            println()
        }
    }

    private fun doFolds(dotMap: List<BooleanArray>, folds: List<Pair<Fold, Int>>): List<BooleanArray> {
        return if (folds.isEmpty()) {
            dotMap
        } else {
            val fold = folds.first()
            val next = folds.subList(1, folds.size)
            doFolds(doFold(dotMap, fold), next)
        }
    }

    private fun doFold(dotMap: List<BooleanArray>, fold: Pair<Fold, Int>): List<BooleanArray> {
        return when (fold.first) {
            Fold.Up -> foldUp(dotMap, fold.second)
            Fold.Right -> foldRt(dotMap, fold.second)
        }
    }

    private fun foldRt(dotMap: List<BooleanArray>, crease: Int): List<BooleanArray> {
        val outMap = mutableListOf<BooleanArray>()
        (dotMap.indices).forEach { _ ->
            outMap.add(BooleanArray(dotMap[0].size / 2))
        }
        dotMap.forEachIndexed() { idx, row ->
            var colCount = crease
            while (colCount >= 0) {
                val outMapCol = crease - colCount
                val rowCol = colCount + crease
                outMap[idx][outMapCol] = row[rowCol + 2] || dotMap[idx][outMapCol]
                colCount--
            }
        }
        return outMap
    }

    private fun foldUp(dotMap: List<BooleanArray>, crease: Int): List<BooleanArray> {
        var rowCount = crease
        var end = dotMap.size
        val outMap = mutableListOf<BooleanArray>()
        (0..crease).forEach { _ ->
            outMap.add(BooleanArray(dotMap[0].size))
        }
        while (rowCount >= 0) {
            val lastRow = dotMap[end - 1]
            (lastRow.indices).forEach { idx ->
                outMap[crease - rowCount][idx] = dotMap[crease - rowCount][idx] || lastRow[idx]
            }
            end--
            rowCount--
        }
        return outMap
    }

    private fun getFolds(foldsInput: String): List<Pair<Fold, Int>> {
        return foldsInput.split("\r\n").map { input ->
            val parts = input.split("=")
            when (parts[0]) {
                "fold along y" -> Pair(Fold.Up, parts[1].toInt() - 1)
                else -> Pair(Fold.Right, parts[1].toInt() - 1)
            }
        }
    }

    private fun getDotMap(dotsInput: String): List<BooleanArray> {

        val (maxX, maxY) = getMax(dotsInput)

        val dotMap = mutableListOf<BooleanArray>()
        (0 until maxY).forEach { _ ->
            dotMap.add(BooleanArray(maxX))
        }

        val dotPairStrings = dotsInput.split("\r\n")
        for (dotPair in dotPairStrings) {
            val partStrings = dotPair.split(",")
            val x = partStrings[0].toInt()
            val y = partStrings[1].toInt()
            dotMap[y][x] = true
        }
        return dotMap
    }

    private fun getMax(dotsInput: String): Pair<Int, Int> {
        val dotPairStrings = dotsInput.split("\r\n")
        val dotPairs = dotPairStrings.map { it.split(",") }.map { Pair(it[0].toInt(), it[1].toInt()) }
        val xValues = dotPairs.map { it.first }
        val yValues = dotPairs.map { it.second }

        return Pair(xValues.maxOf { it } + 1, yValues.maxOf { it } + 1)
    }


}