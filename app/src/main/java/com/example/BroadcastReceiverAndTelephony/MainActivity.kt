package com.example.BroadcastReceiverAndTelephony

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.example.BroadcastReceiverAndTelephony.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    //setting up binding view
    private lateinit var binding: ActivityMainBinding
    private lateinit var broadcastReceiver: MyBroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Setting uo listener for the "Save" Button
        binding.btnSave.setOnClickListener {
            //Retrieving phone number and message from the input fields
            val phoneNumber = binding.number.text.toString()
            val message = binding.message.text.toString()

            //starting the service that will handle phone state changes
            val intent = Intent(this, ServicePhoneState::class.java)
            startService(intent)

            Log.i("Data","Data has been safe")

            //saving phone number and message to shared preferences
            val sharedPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString("phoneNumber", phoneNumber)
            editor.putString("message", message)
            editor.apply()

            //Displaying a toast message to the user
            Toast.makeText(this, "Data has been safe", Toast.LENGTH_SHORT).show()
        }

        //Initializing the broadcast receiver
        broadcastReceiver = MyBroadcastReceiver()
    }
    override fun onStart() {
        super.onStart()

        //Creating an intent filter for phone state changes
        val intentFilter = IntentFilter()
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)

        //Registering the broadcast receiver with the intent filter when the activity start
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()

        //Unregistering the broadcast receiver when the activity stop
        unregisterReceiver(broadcastReceiver)
    }
}