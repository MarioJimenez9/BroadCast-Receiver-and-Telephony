package com.example.llamada_y_mensaje

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.example.llamada_y_mensaje.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var broadcastReceiver: MyBroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Configurar el botón para guardar los datos
        binding.btnGuardar.setOnClickListener {
            val phoneNumber = binding.etNumero.text.toString()
            val message = binding.etMensaje.text.toString()

            val intent = Intent(this, ServicePhoneState::class.java)
            startService(intent)

            Log.i("informacion","Valor de iniio")
            // Guardar los datos en las preferencias compartidas
            val sharedPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString("phoneNumber", phoneNumber)
            editor.putString("message", message)
            editor.apply()


            //Mostramnos pequeño mensaje para aviasrle al usuario que guardamos los datos
            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
        }

        //Nos suscribrimos al CallReceiver
        broadcastReceiver = MyBroadcastReceiver()
    }
    override fun onStart() {
        super.onStart()

        // Registrar el BroadcastReceiver
        val intentFilter = IntentFilter()
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        // Desregistrar el BroadcastReceiver
        unregisterReceiver(broadcastReceiver)
    }
}