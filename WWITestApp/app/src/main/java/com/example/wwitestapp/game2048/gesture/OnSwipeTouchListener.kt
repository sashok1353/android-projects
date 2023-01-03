package com.example.wwitestapp.game2048.gesture

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlin.math.abs

enum class Gesture {
    SWIPE_UP,
    SWIPE_DOWN,
    SWIPE_RIGHT,
    SWIPE_LEFT,
}

class OnSwipeTouchListener(context: Context) : View.OnTouchListener {
    private val _gestureEvents = MutableSharedFlow<Gesture>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val gestureEvents: SharedFlow<Gesture> = _gestureEvents

    private val gestureDetector: GestureDetector = GestureDetector(context, GestureListener())
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(motionEvent)
    }
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD: Int = 100
        private val SWIPE_VELOCITY_THRESHOLD: Int = 100
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            onClick()
            return super.onSingleTapUp(e)
        }
        override fun onDoubleTap(e: MotionEvent): Boolean {
            onDoubleClick()
            return super.onDoubleTap(e)
        }
        override fun onLongPress(e: MotionEvent) {
            onLongClick()
            super.onLongPress(e)
        }
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > SWIPE_THRESHOLD && abs(
                            velocityX
                        ) > SWIPE_VELOCITY_THRESHOLD
                    ) {
                        if (diffX > 0) {
                            onSwipeRight()
                        }
                        else {
                            onSwipeLeft()
                        }
                    }
                }
                else {
                    if (abs(diffY) > SWIPE_THRESHOLD && abs(
                            velocityY
                        ) > SWIPE_VELOCITY_THRESHOLD
                    ) {
                        if (diffY < 0) {
                            onSwipeUp()
                        }
                        else {
                            onSwipeDown()
                        }
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return false
        }
    }
    fun onSwipeRight() {
        _gestureEvents.tryEmit(Gesture.SWIPE_RIGHT)
    }
    fun onSwipeLeft() {
        _gestureEvents.tryEmit(Gesture.SWIPE_LEFT)
    }
    fun onSwipeUp() {
        _gestureEvents.tryEmit(Gesture.SWIPE_UP)
    }
    fun onSwipeDown() {
        _gestureEvents.tryEmit(Gesture.SWIPE_DOWN)
    }
    private fun onClick() {}
    private fun onDoubleClick() {}
    private fun onLongClick() {}
}
