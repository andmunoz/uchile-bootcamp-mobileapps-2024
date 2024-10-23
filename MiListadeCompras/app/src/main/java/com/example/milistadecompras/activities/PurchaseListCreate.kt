package com.example.milistadecompras.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.milistadecompras.R
import com.example.milistadecompras.openhelper.PurchaseListOpenHelper

class PurchaseListCreate : BaseActivity() {
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

        // Inflamos el Toolbar y el Bottom Navigation
        super.inflateToolbar()
        super.inflateBottomNavigationMenu()
    }

    fun onSaveButtonClick(view: View) {
        // Obtiene los valores ingresados por el usuario
        val purchaseListName = findViewById<EditText>(R.id.purchaseNameEditText).text.toString()
        val purchaseListDate = findViewById<EditText>(R.id.purchaseDateEditText).text.toString()
        val purchaseListPeriod = findViewById<EditText>(R.id.purchasePeriodEditText).text.toString()

        // Guardar en la base de datos
        val dbHelper = PurchaseListOpenHelper(this)
        val db = dbHelper.writableDatabase

        // Inserta los valores en la base de datos
        val values = ContentValues().apply {
            put("name", purchaseListName)
            put("date", purchaseListDate)
            put("period", purchaseListPeriod)
        }
        db.insert("purchase_list", null, values)
        db.close()

        finish()
    }

    fun onProductsButtonClick(view: View) {
        val productsStub = findViewById<ViewStub>(R.id.products_stub)
        productsStub.inflate()

        val productsButton = findViewById<Button>(R.id.products_button)
        productsButton.isEnabled = false
    }
}