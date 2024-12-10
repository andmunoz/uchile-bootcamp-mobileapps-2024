package com.example.broadcastexample

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var broadcastText: EditText
    private lateinit var broadcastButton: Button
    private lateinit var staticReceiver: MyReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        broadcastText = findViewById(R.id.broadcast_text_Input)
        broadcastButton = findViewById(R.id.broadcast_button)
        broadcastButton.setOnClickListener { sendBroadcast() }
        staticReceiver = MyReceiver()
    }

    override fun onResume() {
        super.onResume()K
        Log.d("StaticReceiver", "Registrando...")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(
                staticReceiver,
                IntentFilter("com.example.MY_ACTION"),
                RECEIVER_NOT_EXPORTED
            )
        }
        Log.d("StaticReceiver", "Registrado!")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Receiver", "Desregistrando receiver...")
        unregisterReceiver(staticReceiver)
        Log.d("Receiver", "Receiver desregistrado!")
    }

    private fun sendBroadcast() {
        val data = broadcastText.text.toString()
        val intent = Intent("com.example.MY_ACTION")
        Log.d("Broadcast", "Enviando mensaje...")
        intent.putExtra("extra_data", data)
        sendBroadcast(intent)
        Log.d("Broadcast", "Mensaje enviado: $data")
    }
}