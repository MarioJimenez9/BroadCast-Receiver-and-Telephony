package com.example.BroadcastReceiverAndTelephony

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

// Define the MyBroadcastReceiver class that extends BroadcastReceiver
class MyBroadcastReceiver : BroadcastReceiver() {

    // Override the onReceive method that is called when a broadcast is received
    override fun onReceive(context: Context, intent: Intent) {

        // Check if the broadcast action is ACTION_PHONE_STATE_CHANGED
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            // Get the phone state from the intent
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            // If the phone is ringing
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                // Get the incoming phone number from the intent
                val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                // Log the incoming phone number
                Log.i("Phone: ",phoneNumber.toString())
                Log.i("Saved Phone","#####")

                //Get the saved phone nummber and message from shared preferences
                val sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                Log.i("myPrefs",sharedPrefs.toString())
                val savedPhoneNumber = sharedPrefs.getString("phoneNumber", "")
                Log.i("number",savedPhoneNumber.toString())
                val savedMessage = sharedPrefs.getString("message", "")
                Log.i("meesage",savedMessage.toString())

                if (phoneNumber == savedPhoneNumber) {
                    //Send a text message with the saved message to the incoming phone number
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(phoneNumber, null, savedMessage, null, null)
                    Log.i("Success","The message was sent")
                }
                else{
                    Log.i("Nothing","Any message was sent")
                }
            }
        }
        else{
            //Check if the state changes works well
            print("No phone state change broadcast received")
        }
    }
}