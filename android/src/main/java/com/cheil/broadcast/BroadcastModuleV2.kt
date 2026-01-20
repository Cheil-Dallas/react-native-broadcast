package com.cheil.broadcast

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.samsung.retailexperience.standoutlib.StandoutServiceClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BroadcastModuleV2 (private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String = "BroadcastModuleV2"

    private val scope = CoroutineScope(Dispatchers.IO)

    private val serviceClient: StandoutServiceClient by lazy {
        StandoutServiceClient(reactContext)
    }

    @ReactMethod
    fun sendMessage(data: String) {
        Log.d(TAG, "Sending message: $data")
        scope.launch {
            serviceClient.sendCommand(data)
        }
    }

    companion object {
        val TAG = BroadcastModuleV2::class.java.simpleName
    }
}
