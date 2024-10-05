package com.example.milistadecompras

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PurchaseListCreate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Llama al onCreate de AppCompatActivity
        super.onCreate(savedInstanceState)

        // Activa la barra lateral de bordes
        enableEdgeToEdge()

        // Establece el diseño de la actividad
        setContentView(R.layout.activity_purchase_list_create)

        // Configura el uso de las barras de insets para el diseño
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}