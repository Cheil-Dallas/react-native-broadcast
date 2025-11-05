package com.cheil.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class TestReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        Log.d("TestReceiver", "Received: $action")

        when (action) {
            "com.cheil.action.CXP:PA_F2" -> {
                Log.d("TestReceiver", "➡ Trigger TEST behavior")
            }
            "com.cheil.action.OPEN_WALLET" -> {
                Log.d("TestReceiver", "➡ Opening Samsung Wallet")

                val pm = context.packageManager
                val intent = pm.getLaunchIntentForPackage("com.samsung.android.samsungpay.wallet")

                if (intent != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Samsung Wallet not installed", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}
