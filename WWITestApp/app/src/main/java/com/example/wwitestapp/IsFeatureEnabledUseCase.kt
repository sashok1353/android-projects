package com.example.wwitestapp

import android.net.ConnectivityManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class IsFeatureEnabledUseCase @Inject constructor() {

    suspend operator fun invoke(): Boolean {
        return false
        val config = Firebase.remoteConfig

        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(5)
            .build()

        config.setConfigSettingsAsync(settings)

        // Wrap with try-catch?
        try {
            val isFetchedFromBackendTask = config.fetchAndActivate().await()
        } catch (e: Exception) {
//            var connectivityManager: ConnectivityManager = getSystem
        }

        return FirebaseRemoteConfig.getInstance().getBoolean("game_disabled")

    }
}

