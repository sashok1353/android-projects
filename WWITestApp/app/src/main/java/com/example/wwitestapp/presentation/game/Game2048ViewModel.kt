package com.example.wwitestapp

import androidx.lifecycle.ViewModel
import com.example.wwitestapp.game2048.board.Direction
import com.example.wwitestapp.game2048.game.newGame2048
import com.example.wwitestapp.game2048.game.presentation.TileViewState
import com.example.wwitestapp.game2048.gesture.Gesture

enum class Result{
    HAS_WON,
    HAS_LOST,
    HAS_MOVE
}

class Game2048ViewModel : ViewModel() {
    private var gameManager = newGame2048()
        .apply {
            initialize()
        }

    val viewState = GameViewState(
        restartHandler = {
            gameManager = newGame2048()
            gameManager.initialize()
            updateUiStateByBoardState()
        }
    )

    init {
        updateUiStateByBoardState()
    }

    private fun updateUiStateByBoardState() {
        val flatTiles = viewState.boardViewState.allTiles

        gameManager.getFlattenValues().forEachIndexed { index, cellValue ->
            flatTiles.getOrNull(index)?.let {
                bindCell(cellValue, it)
            }
        }
    }

    private fun bindCell(cellValue: Int?, tileViewState: TileViewState) {
        tileViewState.isVisible.value = cellValue != null
        val cellValue = cellValue ?: return

        tileViewState.number.value = cellValue
        tileViewState.backgroundColor.value = mapValueToColor(cellValue)
    }

    private fun mapValueToColor(cellValue: Int): Int? {
        return when(cellValue) {
            2 -> R.color.game_2_color
            4 -> R.color.game_4_color
            8 -> R.color.game_8_color
            16 -> R.color.game_16_color
            32 -> R.color.game_32_color
            64 -> R.color.game_64_color
            128 -> R.color.game_128_color
            256 -> R.color.game_256_color
            512 -> R.color.game_512_color
            1024 -> R.color.game_1024_color
            2048 -> R.color.game_2048_color
            else -> R.color.board_background_tile
        }
    }

    fun notifyGestureOccurred(gesture: Gesture):Result {
        if(gameManager.hasWon()) {
            return Result.HAS_WON
        } else if(gameManager.canMove()) {
            val direction =  when (gesture) {
                Gesture.SWIPE_RIGHT -> Direction.RIGHT
                Gesture.SWIPE_LEFT ->  Direction.LEFT
                Gesture.SWIPE_UP -> Direction.UP
                Gesture.SWIPE_DOWN -> Direction.DOWN
            }
            gameManager.processMove(direction)
            updateUiStateByBoardState()
            return Result.HAS_MOVE
        } else {
            return Result.HAS_LOST
        }
    }

}

class GameViewState(val restartHandler: () -> Unit ) {
    val boardViewState = GameBoard()
}

class GameBoard {
    val tileV1H1 = TileViewState()
    val tileV1H2 = TileViewState()
    val tileV1H3 = TileViewState()
    val tileV1H4 = TileViewState()

    val tileV2H1 = TileViewState()
    val tileV2H2 = TileViewState()
    val tileV2H3 = TileViewState()
    val tileV2H4 = TileViewState()

    val tileV3H1 = TileViewState()
    val tileV3H2 = TileViewState()
    val tileV3H3 = TileViewState()
    val tileV3H4 = TileViewState()

    val tileV4H1 = TileViewState()
    val tileV4H2 = TileViewState()
    val tileV4H3 = TileViewState()
    val tileV4H4 = TileViewState()

    val allTiles = listOf(
        tileV1H1, tileV1H2, tileV1H3, tileV1H4,
        tileV2H1, tileV2H2, tileV2H3, tileV2H4,
        tileV3H1, tileV3H2, tileV3H3, tileV3H4,
        tileV4H1, tileV4H2, tileV4H3, tileV4H4,
    )
}
