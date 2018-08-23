package com.pemt.market.flow.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.v("hotsx-token", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.v("hotsx-message", message.toString())
    }
}