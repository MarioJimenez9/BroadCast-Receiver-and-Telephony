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

            //starting the service
            val intent = Intent(this, ServicePhoneState::class.java)
            startService(intent)

            Log.i("Data","Data has been safe")
            // Guardar los datos en las preferencias compartidas
            val sharedPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString("phoneNumber", phoneNumber)
            editor.putString("message", message)
            editor.apply()


            Toast.makeText(this, "Data has been safe", Toast.LENGTH_SHORT).show()
        }

        broadcastReceiver = MyBroadcastReceiver()
    }
    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}