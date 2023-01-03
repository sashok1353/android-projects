package com.example.wwitestapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface NavigateTo {
    object NavigateToWebView : NavigateTo
    object NavigateToGame : NavigateTo
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isFeatureEnabledUseCase: IsFeatureEnabledUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<NavigateTo>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val navigationEvents: SharedFlow<NavigateTo> = _navigationEvents

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val isGameDisabled = isFeatureEnabledUseCase()
            _navigationEvents.emit(
                if (isGameDisabled) NavigateTo.NavigateToWebView
                else NavigateTo.NavigateToGame
            )
        }
    }
}