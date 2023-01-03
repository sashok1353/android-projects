package com.example.wwitestapp.game2048.game

import com.example.wwitestapp.game2048.board.Cell
import com.example.wwitestapp.game2048.board.Direction

interface Game {
    fun initialize()
    fun canMove(): Boolean
    fun hasWon(): Boolean
    fun processMove(direction: Direction)
    operator fun get(i: Int, j: Int): Int?

    fun getFlattenValues(): Collection<Int?>
}
