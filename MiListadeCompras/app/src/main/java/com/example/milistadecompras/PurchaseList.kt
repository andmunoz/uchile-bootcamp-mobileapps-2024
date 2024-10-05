package com.example.milistadecompras

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PurchaseList : AppCompatActivity() {
    // Declaración de variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PurchaseListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var itemList: List<PurchaseListItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        // Llamada al método onCreate de AppCompatActivity
        super.onCreate(savedInstanceState)

        // Configuración de la barra lateral de bordes
        enableEdgeToEdge()

        // Configuración del diseño de la actividad
        setContentView(R.layout.activity_purchase_list)

        // Configuración de las barras de insets para el diseño
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtención del RecyclerView
        recyclerView = findViewById(R.id.purchase_list_recycler)

        // Configuración del RecyclerView
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Inicialización de la lista de elementos
        itemList = listOf()
        adapter = PurchaseListAdapter(itemList)
        recyclerView.adapter = adapter

        // Obtención del nombre de usuario de la actividad anterior
        val username = intent.getStringExtra("username")
        Toast.makeText(this, "Bienvenido, $username", Toast.LENGTH_LONG).show()
    }
}