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
                val commandParts = it.split(" ")
                val commandNumber = Integer.parseInt(commandParts[1])
                when (commandParts[0]) {
                    "forward" -> Pair(Command.Forward, commandNumber)
                    "up" -> Pair(Command.Up, commandNumber)
                    "down" -> Pair(Command.Down, commandNumber)
                    else -> throw UnsupportedOperationException("unknown command")
                }
            }.apply {
                val partOnePosition = this.fold(Position(0, 0)) { pos, command ->
                    when (command.first) {
                        Command.Forward -> Position(pos.horizontal + command.second, pos.depth)
                        Command.Up -> Position(pos.horizontal, pos.depth - command.second)
                        Command.Down -> Position(pos.horizontal, pos.depth + command.second)
                    }
                }

                val partTwoPosition = this.fold(Position(0, 0) to 0) { posAimPair, command ->
                    when (command.first) {
                        Command.Forward -> {
                            Position(
                                posAimPair.first.horizontal + command.second,
                                posAimPair.first.depth + (command.second * posAimPair.second)
                            ) to posAimPair.second
                        }
                        Command.Up -> {
                            Position(
                                posAimPair.first.horizontal,
                                posAimPair.first.depth
                            ) to posAimPair.second - command.second
                        }
                        Command.Down -> {
                            Position(
                                posAimPair.first.horizontal,
                                posAimPair.first.depth
                            ) to posAimPair.second + command.second
                        }
                    }
                }

                println("Final Position: ${partOnePosition.horizontal * partOnePosition.depth}")
                println("Final Aimed Position: ${partTwoPosition.first.horizontal * partTwoPosition.first.depth}")
            }
    }
}