package com.example.wwitestapp.game2048.board

fun createSquareBoard(width: Int): SquareBoard {
    val cells = (1..width).flatMap { i ->
        (1..width).map { j -> Cell(i, j) }
    }

    return object : SquareBoard {
        override val width = width

        override fun getCellOrNull(i: Int, j: Int): Cell? {
            return cells.find { it.i == i && it.j == j }
        }

        override fun getCell(i: Int, j: Int): Cell {
            return getCellOrNull(i, j) ?: throw IllegalArgumentException("Invalid cell coordinates")
        }

        override fun getAllCells(): List<Cell> {
            return cells
        }

        override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
            val allCells = cells.filter { it.i == i && jRange.contains(it.j) }
            return if (jRange.first > jRange.last) allCells.sortedBy { it.j }.reversed() else allCells.sortedBy { it.j }
        }

        override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
            val allCells = cells.filter { it.j == j && iRange.contains(it.i) }
            return if (iRange.first > iRange.last) allCells.reversed() else allCells
        }

        override fun Cell.getNeighbour(direction: Direction): Cell? {
            return when (direction) {
                Direction.UP -> getCellOrNull(i - 1, j)
                Direction.DOWN -> getCellOrNull(i + 1, j)
                Direction.LEFT -> getCellOrNull(i, j - 1)
                Direction.RIGHT -> getCellOrNull(i, j + 1)
            }
        }

        override fun getCellValue(i: Int, j: Int): Int? {
            TODO("Not yet implemented")
        }
    }
}
fun <T> createGameBoard(width: Int): GameBoard<T> {
    val board = createSquareBoard(width)
    val cellValues = mutableMapOf<Cell, T?>()
    cellValues.putAll(board.getAllCells().map { it to null })

    return object : GameBoard<T>, SquareBoard by board {
        override operator fun get(cell: Cell): T? {
            return cellValues[cell]
        }

        override operator fun set(cell: Cell, value: T?) {
            cellValues[cell] = value
        }

        override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
            return cellValues.filterValues(predicate).keys
        }

        override fun find(predicate: (T?) -> Boolean): Cell? {
            return cellValues.filterValues(predicate).keys.firstOrNull()
        }

        override fun any(predicate: (T?) -> Boolean): Boolean {
            for (cell in getAllCells()) {
                if (predicate(get(cell))) {
                    return true
                }
            }
            return false
        }

        override fun all(predicate: (T?) -> Boolean): Boolean {
            for (cell in getAllCells()) {
                if (!predicate(get(cell))) {
                    return false
                }
            }
            return true
        }

        override fun getFlattenValues(): List<T?> {
            return getAllCells()
                .map { get(it) }
        }
    }
}

