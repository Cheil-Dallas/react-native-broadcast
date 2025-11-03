package com.cheil.broadcast

import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.*

class BroadcastModule(private val reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {
    
    override fun getName(): String = "BroadcastModule"

    @ReactMethod
    fun sendBroadcast(action: String) {
        Log.d("BroadcastModule", "Send Broadcast: $action")
        val intent = Intent(action)
        reactContext.sendBroadcast(intent)
    }
}
