package day4

import AocDay
import java.io.File
import java.util.*

/*
    --- Day 4: Giant Squid ---
 */
class Day4 : AocDay {
    override fun solve() {
        val inputLines = File("./src/main/resources/day_four_input.txt").readLines()
        val numberInputs = inputLines[0].split(',').map { Integer.parseInt(it) }
        val boardInputs = inputLines
            .subList(2, inputLines.size)
            .filter { it != "" }
            .chunked(5)
            .map { lineList ->
                lineList.map { line ->
                    line.trim()
                        .split("\\s+".toRegex())
                        .map { Integer.parseInt(it) }
                }
            }.map {
                Board(it)
            }
        
        val winner = playGame(numberInputs, boardInputs)
        if (winner.isPresent) {
            val (unmarkedSum, number) = winner.get()
            println("Winning Score: ${unmarkedSum * number}")
        }

        val (lastUnmarkedSum, lastNumber) = playGameGetLastWinner(numberInputs, boardInputs)
        println("Last Winning Score: ${lastUnmarkedSum * lastNumber}")

    }

    private fun playGame(numberInputs: List<Int>, boardInputs: List<Board>): Optional<Pair<Int, Int>> {
        numberInputs.forEach { number ->
            markBoards(number, boardInputs)
            val winner = checkForBingo(boardInputs)
            if (winner.isPresent) {
                return Optional.of(winner.get().getUnmarkedScore() to number)
            }
        }
        return Optional.empty<Pair<Int, Int>>()
    }

    private fun playGameGetLastWinner(numberInputs: List<Int>, boardInputs: List<Board>): Pair<Int, Int> {
        val winners = mutableListOf<Pair<Board, Int>>()

        for (number in numberInputs) {
            markBoards(number, boardInputs)
            val winningBoards = checkForBingoBoards(boardInputs)
            winningBoards.forEach { winner ->
                winners.add(winner to number)
                (boardInputs as ArrayList).remove(winner)
                if (boardInputs.isEmpty()) {
                    return winners.last().first.getUnmarkedScore() to winners.last().second
                }
            }
        }
        return winners.last().first.getUnmarkedScore() to winners.last().second
    }

    private fun checkForBingo(boardInputs: List<Board>): Optional<Board> {
        boardInputs.forEach { board ->
            if (board.hasWinner()) {
                return Optional.of(board)
            }
        }
        return Optional.empty<Board>()
    }

    private fun checkForBingoBoards(boardInputs: List<Board>): List<Board> {
        val bingoBoards = mutableListOf<Board>()
        boardInputs.forEach { board ->
            if (board.hasWinner()) {
                bingoBoards.add(board)
            }
        }
        return bingoBoards
    }

    private fun markBoards(number: Int, boardInputs: List<Board>) {
        boardInputs.forEach { board ->
            board.mark(number)
        }
    }
}