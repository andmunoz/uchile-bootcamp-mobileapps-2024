package com.example.milistadecompras

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Llama al onCreate de AppCompatActivity
        super.onCreate(savedInstanceState)

        // Activa la barra lateral de bordes
        enableEdgeToEdge()

        // Establece el diseño de la actividad
        setContentView(R.layout.activity_main)

        // Configura el uso de las barras de insets para el diseño
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onNextButtonClick(view: View) {
        // Obtiene el nombre ingresado por el usuario
        val name = findViewById<EditText>(R.id.nameEditText).text.toString()

        // Crea un Intent para iniciar la actividad de lista de compras
        val intent = Intent(this, PurchaseList::class.java)

        // Pasa el nombre del usuario a la actividad de lista de compras
        intent.putExtra("username", name)

        // Inicia la actividad de lista de compras
        startActivity(intent)
    }
}