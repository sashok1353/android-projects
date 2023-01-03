package com.example.wwitestapp

import androidx.lifecycle.MutableLiveData

class SplashActivityViewState(val onRetryClickHandler: () -> Unit) {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
}