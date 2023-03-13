package com.example.llamada_y_mensaje

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

                // El teléfono está sonando, obtén el número de teléfono que está llamando
                val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                Log.i("Telefono",phoneNumber.toString())

                Log.i("Numero Guardado","numero guardado")

                // Obtenemos los datos guardados en las preferencias compartidas
                val sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                Log.i("myPrefs",sharedPrefs.toString())
                val savedPhoneNumber = sharedPrefs.getString("phoneNumber", "")
                Log.i("numero",savedPhoneNumber.toString())
                val savedMessage = sharedPrefs.getString("message", "")
                Log.i("mensaje",savedMessage.toString())

                // ch
                if (phoneNumber == savedPhoneNumber) {
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(phoneNumber, null, savedMessage, null, null)
                    Log.i("Respuesta","Mensaje enviado correctamnete")
                }else{
                    Log.i("Respuesta","Hubo un problema al enviar el mensaje")
                }
            }
        }
    }
}