package day5

import AocDay
import java.io.File

data class Position(val x: Int, val y: Int)
data class VentLine(val start: Position, val end: Position)

class Day5 : AocDay {
    override fun solve() {
        File("./src/main/resources/day_five_input.txt")
            .readLines()
            .map {
                val parts = it.split(" -> ")
                VentLine(getPosition(parts[0]), getPosition(parts[1]))
            }.apply {
                // part one
                val grid = createGrid()
                this.forEach { markGrid(grid, it, true) }
                val overlaps = grid.map { it.count { n -> n >= 2 } }.filter { it != 0 }.sum()
                println("Overlaps: $overlaps")

            }.apply {
                // part two
                val grid = createGrid()
                this.forEach { markGrid(grid, it, false) }
                val overlaps = grid.map { it.count { n -> n >= 2 } }.filter { it != 0 }.sum()
                println("Overlaps with diagonals: $overlaps")
            }
    }

    private fun createGrid(): MutableList<MutableList<Int>> {
        val grid = mutableListOf<MutableList<Int>>()
        for (m in 0 until 1000) {
            grid.add(mutableListOf<Int>())
        }
        for (n in 0 until 1000) {
            for (_m in 0 until 1000) {
                grid[n].add(0)
            }
        }
        return grid
    }

    private fun markGrid(grid: MutableList<MutableList<Int>>, ventLine: VentLine, ignoreDiagonals: Boolean) {
        if (ventLine.start.x == ventLine.end.x || ventLine.start.y == ventLine.end.y) {
            markHorizontalOrVertical(grid, ventLine)
        } else {
            if (!ignoreDiagonals) {
                markDiagonal(grid, ventLine)
            }
        }
    }

    private fun markDiagonal(grid: MutableList<MutableList<Int>>, ventLine: VentLine) {
        val xRange = if (ventLine.start.x <= ventLine.end.x) {
            (ventLine.start.x..ventLine.end.x)
        } else {
            (ventLine.start.x downTo ventLine.end.x)
        }
        val yRange = if (ventLine.start.y <= ventLine.end.y) {
            (ventLine.start.y..ventLine.end.y)
        } else {
            (ventLine.start.y downTo ventLine.end.y)
        }
        for ((x, y) in xRange.zip(yRange)) {
            grid[x][y] += 1
        }
    }

    private fun markHorizontalOrVertical(grid: MutableList<MutableList<Int>>, ventLine: VentLine) {
        val xRange = if (ventLine.start.x <= ventLine.end.x) {
            (ventLine.start.x..ventLine.end.x)
        } else {
            (ventLine.end.x..ventLine.start.x)
        }
        val yRange = if (ventLine.start.y <= ventLine.end.y) {
            (ventLine.start.y..ventLine.end.y)
        } else {
            (ventLine.end.y..ventLine.start.y)
        }
        for (x in xRange) {
            for (y in yRange) {
                grid[x][y] += 1
            }
        }
    }

    private fun getPosition(s: String): Position {
        val p = s.split(",")
        val (x, y) = Integer.parseInt(p[0]) to Integer.parseInt(p[1])
        return Position(x, y)
    }
}