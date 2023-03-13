package com.example.BroadcastReceiverAndTelephony

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.telephony.TelephonyManager

// Define the ServicePhoneState class that extends service
class ServicePhoneState : Service() {
    //Declare the callReceiver and isServiceRunning variables
    private lateinit var callReceiver: MyBroadcastReceiver
    private var isServiceRunning = false

    // Override the onBind method
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // Override the onStartCommandMethod
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Check if the service is already running and stop it
        if (isServiceRunning) {
            stopService(Intent(applicationContext, ServicePhoneState::class.java))
        }
        // Set isServiceRunning to true
        isServiceRunning = true

        // Create a new instance of myBroadcastReceiver
        callReceiver = MyBroadcastReceiver()

        // Create a new intent filter and dd the ACTION_PHONE_STATE_CHANGED action
        val intentFilter = IntentFilter()
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)

        // Register the callReceiver with the intent filter
        registerReceiver(callReceiver, intentFilter)

        // Return START_STICKY to indicate that the service should be restarted if it is killed
        return START_STICKY
    }
    // Override the onDestroy method to unregister the callReceiver and set isServiceRunning to false
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(callReceiver)
        isServiceRunning = false
    }
}

