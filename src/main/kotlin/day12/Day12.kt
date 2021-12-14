package day12

import AocDay
import java.io.File

data class CaveNode(val name: String, var next: CaveNode? = null)

class Day12 : AocDay {
    override fun solve() {
        val inputStrings = File("./src/main/resources/day_twelve_input_test.txt")
            .readLines()
            .map { it.split("-") }

        val mutableInputList = inputStrings as MutableList
        val paths = mutableListOf<CaveNode>()

        while (mutableInputList.isNotEmpty()) {
            for (input in mutableInputList) {
                if (input[0] == "start") {
                    mutableInputList.remove(input)
                    paths.add(CaveNode(input[0], CaveNode(input[1])))
                } else if (input[1] == "start") {
                    mutableInputList.remove(input)
                    paths.add(CaveNode(input[1], CaveNode(input[0])))
                } else {
                    for (path in paths) {
                        findPath(path, input, mutableInputList)
                    }
                }
            }
        }
        println(paths)
    }

    private fun findPath(path: CaveNode, input: List<String>, mutableInputList: MutableList<List<String>>) {
        if (path.name == input[0]) {
            mutableInputList.remove(input)
            path.next = CaveNode(input[0], CaveNode(input[1]))
        } else if (path.name == input[1]) {
            mutableInputList.remove(input)
            path.next = CaveNode(input[1], CaveNode(input[0]))
        } else if (path.next != null) {
            findPath(path.next!!, input, mutableInputList)
        }
    }
}