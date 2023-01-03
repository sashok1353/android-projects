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

    val viewState: SplashActivityViewState = SplashActivityViewState(
        onRetryClickHandler = { checkIsFeatureEnabledAndNavigate() }
    )

    private val _navigationEvents = MutableSharedFlow<NavigateTo>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val navigationEvents: SharedFlow<NavigateTo> = _navigationEvents

    init {
        checkIsFeatureEnabledAndNavigate()
    }

    private fun checkIsFeatureEnabledAndNavigate() {
        viewState.isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isGameDisabled = isFeatureEnabledUseCase()
                _navigationEvents.emit(
                    if (isGameDisabled) NavigateTo.NavigateToWebView
                    else NavigateTo.NavigateToGame
                )
            } catch (noInternetException: NoInternetException) {
                onLoadingError()
                return@launch
            }

        }
    }

    private fun onLoadingError() {
        viewState.isLoading.postValue(false)
    }

}