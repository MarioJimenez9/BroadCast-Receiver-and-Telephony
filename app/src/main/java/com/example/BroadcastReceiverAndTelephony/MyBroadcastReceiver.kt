package com.example.BroadcastReceiverAndTelephony

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {

                // Store the phone number that is calling
                val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                Log.i("Phone: ",phoneNumber.toString())
                Log.i("Saved Phone","#####")

                // Obte
                val sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                Log.i("myPrefs",sharedPrefs.toString())
                val savedPhoneNumber = sharedPrefs.getString("phoneNumber", "")
                Log.i("number",savedPhoneNumber.toString())
                val savedMessage = sharedPrefs.getString("message", "")
                Log.i("meesage",savedMessage.toString())

                // ch
                if (phoneNumber == savedPhoneNumber) {
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(phoneNumber, null, savedMessage, null, null)
                    Log.i("Success","The message was sent")
                }
                else{
                    Log.i("Wrong","The message wasn't sent")
                }
            }
        }
        else{
            print("No stoi")
        }
    }
}