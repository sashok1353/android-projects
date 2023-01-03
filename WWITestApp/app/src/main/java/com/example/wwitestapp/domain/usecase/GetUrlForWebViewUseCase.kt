package com.example.wwitestapp.domain.geturlforwebview

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class GetUrlForWebViewUseCase @Inject constructor() {


    suspend operator fun invoke(): String {
        return suspendCoroutine { continuation ->
            val rootRef = FirebaseDatabase.getInstance().reference
            val url = rootRef.child("url")
            var link = ""
            url.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    link = snapshot.value as String
                    continuation.resume(link)
                }

                override fun onCancelled(error: DatabaseError) {
                    print(error)
                }

            })
        }
    }
}