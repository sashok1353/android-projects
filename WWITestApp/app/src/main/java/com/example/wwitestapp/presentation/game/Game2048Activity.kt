package com.example.wwitestapp.presentation.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.DialogTitle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.wwitestapp.R
import com.example.wwitestapp.databinding.ActivityGame2048Binding
import com.example.wwitestapp.game2048.gesture.OnSwipeTouchListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Game2048Activity : AppCompatActivity() {

    private val viewModel: Game2048ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGame2048Binding>(
            this, R.layout.activity_game2048
        )
        binding.lifecycleOwner = this
        binding.viewState = viewModel.viewState

        val swipeListener = OnSwipeTouchListener(this)

        binding.boardBackground.setOnTouchListener(swipeListener)


        lifecycleScope.launch {
            swipeListener.gestureEvents
                .onEach { gesture ->
                    when(viewModel.notifyGestureOccurred(gesture)) {
                        Result.HAS_WON -> {
                            createDialog("Victory!", "You won! Congratulation!", "Play again")
                        }
                        Result.HAS_LOST -> {
                            createDialog("Game Over!", "You lost!", "Try again")
                        }
                        Result.HAS_MOVE -> {}
                    }

                }
                .collect()
        }

    }

    private fun createDialog(title: String, message: String, buttonTitle: String) {
        val dialog = AlertDialog.Builder(this@Game2048Activity)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setNeutralButton(buttonTitle) { dialog,_ ->
            viewModel.viewState.restartHandler.invoke()
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onBackPressed() {

    }
}