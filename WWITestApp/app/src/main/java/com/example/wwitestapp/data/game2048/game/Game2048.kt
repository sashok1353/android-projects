package com.example.wwitestapp.data.game2048.game

import com.example.wwitestapp.domain.game2048.board.Cell
import com.example.wwitestapp.domain.game2048.board.Direction
import com.example.wwitestapp.domain.game2048.board.GameBoard
import com.example.wwitestapp.data.game2048.board.createGameBoard
import com.example.wwitestapp.domain.game2048.Game

fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
    Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

    override fun getFlattenValues(): Collection<Int?> {
        return board.getFlattenValues()
    }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) {
    val nextElement = initializer.nextValue(this)
    nextElement?.let { this.getAllCells().filter { it == nextElement.first }.forEach { this[it] = nextElement.second } }
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
    val listWithValues: List<Int> = rowOrColumn.map { this[it] }.moveAndMergeEqual { value -> value * 2 }

    rowOrColumn.forEachIndexed { index, cell -> this[cell] = if (index < listWithValues.size) listWithValues[index] else null }

    return listWithValues.isNotEmpty() && listWithValues.size < rowOrColumn.size
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    when (direction) {
        Direction.UP -> {
            return (1..width).map { col ->
                val columnCells = getColumn(1..4, col)
                moveValuesInRowOrColumn(columnCells)
            }.any { it }
        }
        Direction.DOWN -> {
            return (1..width).map { col ->
                val columnCells = getColumn(1..4, col)
                val reversedColumnCells = columnCells.asReversed()
                moveValuesInRowOrColumn(reversedColumnCells)
            }.any { it }
        }
        Direction.LEFT -> {
            return (1..4).map { row ->
                val rowCells = getRow(row, 1..width)
                moveValuesInRowOrColumn(rowCells)
            }.any { it }
        }
        Direction.RIGHT -> {
            return (1..4).map { row ->
                val rowCells = getRow(row, 1..width)
                val reversedRowCells = rowCells.asReversed()
                moveValuesInRowOrColumn(reversedRowCells)
            }.any { it }
        }
    }
}