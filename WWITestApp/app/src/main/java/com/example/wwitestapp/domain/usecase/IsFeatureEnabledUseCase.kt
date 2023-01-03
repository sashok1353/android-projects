package com.example.wwitestapp.domain.isfeatureenabled

import android.content.Context
import android.content.ContextWrapper
import com.example.wwitestapp.domain.isinternetavailable.IsInternetAvailableUseCase
import com.example.wwitestapp.NoInternetException
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class IsFeatureEnabledUseCase @Inject constructor(
    @ApplicationContext context: Context?,
    private val getIsInternetAvailableUseCase: IsInternetAvailableUseCase
) : ContextWrapper(context) {

    @Throws(NoInternetException::class)
    suspend operator fun invoke(): Boolean {
        val config = Firebase.remoteConfig

        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(5)
            .build()

        config.setConfigSettingsAsync(settings)

        val isInternetAvailable = getIsInternetAvailableUseCase()

        if (!isInternetAvailable) throw NoInternetException()

        try {
            config.fetchAndActivate().await()
        } catch (e: Exception) {
            throw NoInternetException(e)
        }

        return FirebaseRemoteConfig.getInstance().getBoolean("game_disabled")

    }
}

