package day2

import AocDay
import java.io.File


enum class Command {
    Forward, Up, Down
}

data class Position(val horizontal: Int, val depth: Int)

class Day2 : AocDay {
    override fun solve() {
        File("./src/main/resources/day_two_input.txt")
            .readLines()
            .map {
                createCommand(it)
            }.apply {
                val partOnePosition = calculatePositionForPartOne(this)
                val partTwoPosition = calculatePositionForPartTwo(this)
                println("Part One Answer: ${partOnePosition.horizontal * partOnePosition.depth}")
                println("Part Two Answer: ${partTwoPosition.horizontal * partTwoPosition.depth}")
            }
    }

    private fun createCommand(command_string: String): Pair<Command, Int> {
        val commandParts = command_string.split(" ")
        val commandNumber = Integer.parseInt(commandParts[1])
        return when (commandParts[0]) {
            "forward" -> Pair(Command.Forward, commandNumber)
            "up" -> Pair(Command.Up, commandNumber)
            "down" -> Pair(Command.Down, commandNumber)
            else -> throw UnsupportedOperationException("unknown command")
        }
    }

    private fun calculatePositionForPartOne(commands: List<Pair<Command, Int>>): Position {
        var horizontal = 0
        var depth = 0
        for (command in commands) {
            when (command.first) {
                Command.Forward -> horizontal += command.second
                Command.Up -> depth -= command.second
                Command.Down -> depth += command.second
            }
        }
        return Position(horizontal, depth)
    }

    private fun calculatePositionForPartTwo(commands: List<Pair<Command, Int>>): Position {
        var horizontal = 0
        var depth = 0
        var aim = 0
        for (command in commands) {
            when (command.first) {
                Command.Forward -> {
                    horizontal += command.second
                    depth += command.second * aim
                }
                Command.Up -> aim -= command.second
                Command.Down -> aim += command.second
            }
        }
        return Position(horizontal, depth)
    }
}