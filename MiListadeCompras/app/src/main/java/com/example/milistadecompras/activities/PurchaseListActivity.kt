package com.example.milistadecompras.activities

import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.milistadecompras.R

class PurchaseListActivity : BaseActivity() {
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

        // Inflamos el Toolbar y el Bottom Navigation
        super.inflateToolbar()
        super.inflateBottomNavigationMenu()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        // Llamada al método onCreateContextMenu de AppCompatActivity
        super.onCreateContextMenu(menu, v, menuInfo)

        // Se infla el menú de contexto
        menuInflater.inflate(R.menu.list_purchase_context_menu, menu)
    }

    fun onAddButtonClick(view: View) : Unit {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PurchaseListDetailFragment()).commit()
    }
}