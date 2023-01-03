package com.example.wwitestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
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
        val imgView: ImageView = findViewById(R.id.imgView)

        binding.boardBackground.setOnTouchListener(swipeListener)


        lifecycleScope.launch {
            swipeListener.gestureEvents
                .onEach { gesture ->
                    viewModel.notifyGestureOccurred(gesture)
                }
                .collect()

        }

    }
}