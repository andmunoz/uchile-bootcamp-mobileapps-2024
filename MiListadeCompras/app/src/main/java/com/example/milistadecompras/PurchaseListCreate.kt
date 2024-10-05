package com.example.milistadecompras

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        val commonTitleTextView = findViewById<TextView>(R.id.common_title_textview)
        commonTitleTextView.text = "Crear Lista de Compras"
    }

    fun onSaveButtonClick(view: View) {
        // Obtiene los valores ingresados por el usuario
        val purchaseListName = findViewById<EditText>(R.id.purchaseNameEditText).text.toString()
        val purchaseListDate = findViewById<EditText>(R.id.purchaseDateEditText).text.toString()
        val purchaseListPeriod = findViewById<EditText>(R.id.purchasePeriodEditText).text.toString()

        // Prepara el intent para devolver los valores ingresados
        val resultIntent = Intent().apply {
            putExtra("purchaseListName", purchaseListName)
            putExtra("purchaseListDate", purchaseListDate)
            putExtra("purchaseListPeriod", purchaseListPeriod)
        }

        // Establece el resultado de la actividad y finaliza la actividad
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    fun onProductsButtonClick(view: View) {
        val productsStub = findViewById<ViewStub>(R.id.products_stub)
        productsStub.inflate()

        val productsButton = findViewById<Button>(R.id.products_button)
        productsButton.isEnabled = false
    }
}