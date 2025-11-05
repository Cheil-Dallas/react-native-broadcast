package com.cheil.broadcast

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class BroadcastModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String = "BroadcastModule"

    @ReactMethod
    fun sendBroadcast(command: String) {
        Log.d("BroadcastModule", "Received Command: $command")

        /*
        if (handleInternalCommand(command)) {
            Log.d("BroadcastModule", "Internal handler executed: $command")
            return
        }
        */

        val intent = Intent(command)
        reactContext.sendBroadcast(intent)
        Log.d("BroadcastModule", "Broadcast Sent: $command")
    }

    private fun handleInternalCommand(command: String): Boolean {
        return when (command) {

            "OPEN:SNOTES" -> { openSamsungNotes(reactContext); true }
            "OPEN:WALLET" -> { launchApp(reactContext, "com.samsung.android.spay", "Samsung Wallet"); true }
            "OPEN:GALLERY" -> { openGallery(reactContext); true }
            "OPEN:CAMERA" -> { openCamera(reactContext); true }
            "OPEN:SETTINGS" -> { openSettings(reactContext); true }
            "OPEN:DRAWINGASSIST" -> { openDrawingAssist(reactContext); true }
            "OPEN:HEALTH" -> { launchApp(reactContext, "com.samsung.android.shealthmonitor", "Samsung Health"); true }
            "OPEN:INTERPRETER" -> { launchApp(reactContext, "com.samsung.android.translator", "Samsung Interpreter"); true }
            "OPEN:NOWBRIEF" -> { launchApp(reactContext, "com.samsung.android.app.spage", "Samsung Daily"); true }

            "CXP:PA_F2" -> { openCameraWithMode("portrait"); true }
            "CXP:PA_F3" -> { openCameraWithMode("photo"); true }
            "CXP:PA_F4" -> { openCameraWithMode("beauty"); true }
            "CXP:PA_F6" -> { openCameraWithMode("video"); true }
            "CXP:PA_F9" -> { openQuickCapture(reactContext); true }
            "CXP:PA_F10" -> { switchRearCamera(reactContext); true }

            else -> false
        }
    }

    private fun launchApp(context: Context, pkg: String, appName: String) {
        try {
            val intent = context.packageManager.getLaunchIntentForPackage(pkg)
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                Log.d("BroadcastModule", "$appName Opened")
            } else {
                Log.d("BroadcastModule", "$appName Not Installed")
            }
        } catch (e: Exception) {
            Log.e("BroadcastModule", "Failed to open $appName", e)
        }
    }

    private fun openSamsungNotes(context: Context) {
        launchApp(context, "com.samsung.android.app.notes", "Samsung Notes")
    }

    private fun openGallery(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                type = "image/*"
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.d("BroadcastModule", "Gallery Opened")
        } catch (e: Exception) {
            Log.e("BroadcastModule", "Failed to open Gallery", e)
        }
    }

    private fun openCamera(context: Context) {
        try {
            val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.d("BroadcastModule", "Camera Opened")
        } catch (e: Exception) {
            Log.e("BroadcastModule", "Failed to open Camera", e)
        }
    }

    private fun openSettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.d("BroadcastModule", "Settings Opened")
        } catch (e: Exception) {
            Log.e("BroadcastModule", "Failed to open Settings", e)
        }
    }

    private fun openDrawingAssist(context: Context) {
        launchApp(context, "com.samsung.android.penup", "Drawing Assist")
    }

    private fun openCameraWithMode(mode: String) {
        try {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                setClassName(
                    "com.sec.android.app.camera",
                    "com.sec.android.app.camera.Camera"
                )
                putExtra("camera_mode", mode)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            reactContext.startActivity(intent)
            Log.d("BroadcastModule", "Camera Mode: $mode")
        } catch (e: Exception) {
            Log.e("BroadcastModule", "Camera Mode Failed: $mode", e)
            openCamera(reactContext)
        }
    }

    private fun openQuickCapture(context: Context) {
        try {
            val intent = Intent("com.sec.android.quickcamera.CAPTURE").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.d("BroadcastModule", "Quick Capture Triggered")
        } catch (e: Exception) {
            Log.e("BroadcastModule", "Quick Capture Failed", e)
        }
    }

    private fun switchRearCamera(context: Context) {
        try {
            val intent = Intent("com.sec.android.app.camera.REAR_CAMERA").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Log.d("BroadcastModule", "Rear Camera Switch Command Sent")
        } catch (e: Exception) {
            Log.e("BroadcastModule", "Rear Camera Switch Failed", e)
        }
    }
}
