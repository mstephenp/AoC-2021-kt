package day11

import AocDay
import java.io.File

class Day11 : AocDay {
    override fun solve() {
        val map = File("./src/main/resources/day_eleven_input.txt")
            .readLines()
            .map { it.split("") }
            .map { line -> line.filter { s -> s.isNotEmpty() } }
            .map { it.map { n -> n.toInt() } }
            .map { it.toTypedArray() }
            .toTypedArray()
        stepFor100(map)
        getFirstSyncAfter100(map)
    }

    private fun stepFor100(map: Array<Array<Int>>) {
        var n = 0
        var flashCount = 0
        while (n < 100) {
            n++
            flashCount += step(map)
        }
        println("Flash Count: $flashCount")
    }

    private fun getFirstSyncAfter100(map: Array<Array<Int>>) {
        var n = 100
        var flashCount = 0
        while (n < 1000) {
            n++
            flashCount += step(map)
            if (isFirstSync(map)) {
                println("First Sync: $n")
                break
            }
        }
    }

    private fun step(map: Array<Array<Int>>): Int {
        map.forEachIndexed { y, line ->
            line.forEachIndexed { x, _ ->
                map[y][x] += 1
            }
        }
        map.forEachIndexed { y, line ->
            line.forEachIndexed { x, _ ->
                tryFlash(x, y, map)
            }
        }
        return countFlashes(map)
    }

    private fun countFlashes(map: Array<Array<Int>>): Int {
        return map.map { it.filter { n -> n == 0 } }.sumOf { it.count { n -> n == 0 } }
    }

    private fun isFirstSync(map: Array<Array<Int>>): Boolean {
        val mapSize = map.sumOf { it.size }
        var flashCount = 0
        for (line in map) {
            for (value in line) {
                if (value == 0) {
                    flashCount++
                }
            }
        }
        return flashCount == mapSize
    }

    private fun tryFlash(x: Int, y: Int, map: Array<Array<Int>>) {
        if (map[y][x] > 9) {
            map[y][x] = 0
            if (y - 1 >= 0) {
                if (x - 1 >= 0) {
                    increase(x - 1, y - 1, map)
                }
                increase(x, y - 1, map)
                if (x + 1 < map[y].size) {
                    increase(x + 1, y - 1, map)
                }
            }
            if (x + 1 < map[y].size) {
                increase(x + 1, y, map)
            }
            if (y + 1 < map.size) {
                if (x + 1 < map[y].size) {
                    increase(x + 1, y + 1, map)
                }
                increase(x, y + 1, map)
                if (x - 1 >= 0) {
                    increase(x - 1, y + 1, map)
                }
            }
            if (x - 1 >= 0) {
                increase(x - 1, y, map)
            }
        }
    }

    private fun increase(x: Int, y: Int, map: Array<Array<Int>>) {
        if (map[y][x] != 0) {
            map[y][x] += 1
            if (map[y][x] > 9) {
                tryFlash(x, y, map)
                map[y][x] = 0
            }
        }
    }
}
