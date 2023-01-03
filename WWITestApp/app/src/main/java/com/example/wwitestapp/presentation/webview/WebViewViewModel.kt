package com.example.wwitestapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val getUrlForWebViewUseCase: GetUrlForWebViewUseCase,
) : ViewModel() {
    private val _urlArriveEvents = MutableSharedFlow<String>()
    val urlArriveEvents: SharedFlow<String> = _urlArriveEvents

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val url = getUrlForWebViewUseCase()
            _urlArriveEvents.emit(url)
        }
    }
}