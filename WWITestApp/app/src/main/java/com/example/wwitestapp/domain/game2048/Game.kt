package com.example.wwitestapp.domain.game2048

import com.example.wwitestapp.domain.game2048.board.Direction

interface Game {
    fun initialize()
    fun canMove(): Boolean
    fun hasWon(): Boolean
    fun processMove(direction: Direction)
    operator fun get(i: Int, j: Int): Int?

    fun getFlattenValues(): Collection<Int?>
}
