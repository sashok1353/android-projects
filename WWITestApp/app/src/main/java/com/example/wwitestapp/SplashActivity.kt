package com.example.wwitestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            viewModel.navigationEvents.collect { navigateTo ->
                when (navigateTo) {
                    is NavigateTo.NavigateToGame -> {
                        startActivity(
                            Intent(this@SplashActivity, Game2048Activity::class.java)
                        )
                    }
                    is NavigateTo.NavigateToWebView -> {
                        val intent = Intent(this@SplashActivity, WebViewActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}