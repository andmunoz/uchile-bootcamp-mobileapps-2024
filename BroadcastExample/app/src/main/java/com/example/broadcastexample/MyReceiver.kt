package com.example.broadcastexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    private val tag = "BroadcastReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(tag, "Recibiendo mensaje...")
        val data = intent.getStringExtra("extra_data") ?: "No data"
        Toast.makeText(context, "Mensaje: $data", Toast.LENGTH_SHORT).show()
        Log.d(tag, "Mensaje recibido: $data")
    }
}

