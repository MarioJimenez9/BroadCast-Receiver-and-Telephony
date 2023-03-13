package com.example.llamada_y_mensaje

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.telephony.TelephonyManager

class ServicePhoneState : Service() {
    private lateinit var callReceiver: MyBroadcastReceiver
    private var isServiceRunning = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (isServiceRunning) {
            stopService(Intent(applicationContext, ServicePhoneState::class.java))
        }
        isServiceRunning = true

        callReceiver = MyBroadcastReceiver()

        val intentFilter = IntentFilter()
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)

        registerReceiver(callReceiver, intentFilter)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(callReceiver)
        isServiceRunning = false
    }
}

