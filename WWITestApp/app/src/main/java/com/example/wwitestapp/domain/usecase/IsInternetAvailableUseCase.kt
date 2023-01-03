package com.example.wwitestapp.domain.isinternetavailable

import java.net.URL
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class IsInternetAvailableUseCase @Inject constructor() {
    suspend operator fun invoke(): Boolean {
        return suspendCoroutine {
            try {
                URL("https://firebase.google.com/generate_204")
                    .openConnection()
                    .getInputStream()
                    .bufferedReader()
                    .readLines()

                it.resume(true)
            } catch (e: Exception) {
                it.resume(false)
            }
        }
    }

}