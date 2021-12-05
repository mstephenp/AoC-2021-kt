package day4

data class Space(val value: Int, var isMarked: Boolean)

class Board(lines: List<List<Int>>) {
    private var boardLines = listOf<List<Space>>()

    init {
        boardLines = lines.map { line ->
            line.map { value ->
                Space(value, false)
            }
        }
    }

    private fun hasHorizontalMatch(): Boolean {
        boardLines.forEach { line ->
            if (line.none { !it.isMarked }) {
                return true
            }
        }
        return false
    }

    private fun hasVerticalMatch(): Boolean {
        (0 until 5).forEach { index ->
            if (boardLines.none { !it[index].isMarked }) {
                return true
            }
        }
        return false
    }

    fun hasWinner(): Boolean {
        return this.hasVerticalMatch() || this.hasHorizontalMatch()
    }

    fun mark(number: Int) {
        this.boardLines.forEach { boardLine ->
            boardLine.forEach { space ->
                if (space.value == number) {
                    space.isMarked = true
                }
            }
        }
    }

    fun getUnmarkedScore(): Int {
        var unmarkedScore = 0
        boardLines.forEach { line ->
            line.forEach { space ->
                if (!space.isMarked) {
                    unmarkedScore += space.value
                }
            }
        }
        return unmarkedScore
    }
}
