package com.example.wwitestapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WebViewActivity : AppCompatActivity() {

    private val viewModel: WebViewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        var webView: WebView = findViewById(R.id.webView)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.loadsImagesAutomatically = true
        webView.webViewClient = WebViewClient()

        lifecycleScope.launch {
            viewModel.urlArriveEvents
                .collect { url ->
                    webView.loadUrl(url)
                }
        }
    }

    override fun onBackPressed() {
        // Ничего не делает, чтобы запретить переходить назад
    }

}