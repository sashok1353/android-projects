package com.example.wwitestapp.game2048.game.presentation

import android.content.res.ColorStateList
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.lifecycle.MutableLiveData
import com.example.wwitestapp.R

// TODO move to another package
@BindingConversion
fun ownerToString(isVisible: Boolean): Int {
    return if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("backgroundTint")
fun View.setBackgroundTint(@ColorRes color: Int) {
    this.backgroundTintList = ColorStateList.valueOf(
        ContextCompat.getColor(this.context, color)
    )
}



class TileViewState {
    val backgroundColor = MutableLiveData(R.color.game_2_color)
    val number = MutableLiveData(2)
    val isVisible = MutableLiveData(false)
}

fun main() {

    val tile = TileViewState()


    tile.number.value = 2048


}