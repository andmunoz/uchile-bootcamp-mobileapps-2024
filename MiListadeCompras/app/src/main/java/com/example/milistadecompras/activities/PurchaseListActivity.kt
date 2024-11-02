package com.example.milistadecompras.activities

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.example.milistadecompras.R
import com.example.milistadecompras.fragments.PurchaseListDetailFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class PurchaseListActivity : AppCompatActivity() {
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
        inflateToolbar()
        inflateBottomNavigationMenu()
    }

    // Función para inflar el Toolbar
    private fun inflateToolbar() {
        // Reemplazar el ActionBar por el Toolbar
        val toolbar: Toolbar = findViewById(R.id.main_toolbar_view)
        setSupportActionBar(toolbar)
    }

    // Función para inflar el menú de opciones del Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Llama al onCreateOptionsMenu de AppCompatActivity
        super.onCreateOptionsMenu(menu)

        // Infla el menú de opciones desde el archivo XML
        menuInflater.inflate(R.menu.options_menu, menu)

        // Devuelve true para que se muestre el menú de opciones
        return true
    }

    // Función para manejar la selección de elementos del menú de opciones
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Llama al onOptionsItemSelected de AppCompatActivity
        super.onOptionsItemSelected(item)

        // Maneja la selección de elementos del menú de opciones
        when (item.itemId) {
            R.id.app_bar_search -> {
                Toast.makeText(this, "Pronto...", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.app_bar_my_profile -> {
                Toast.makeText(this, "Pronto...", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return false
        }
    }

    // Función para inflar el Bottom Navigation
    private fun inflateBottomNavigationMenu() {
        // Inicialización del Bottom Navigation Menu
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_my_lists -> {
                    // Volvemos al primer fragment del back stack
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    true
                }
                R.id.bottom_nav_sync -> {
                    Toast.makeText(this, "Pronto...", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_nav_notifications -> {
                    Toast.makeText(this, "Pronto...", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    Log.e("PurchaseList", "Item de menú no reconocido: ${item.itemId}")
                    false
                }
            }
        }
    }

    // Función para inflar el menú de contexto de la lista de compras
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

    // Función para activar la creación de lista de compras
    fun onAddButtonClick(view: View) : Unit {
        // Se agrega el fragment de detalle de la lista de compras
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PurchaseListDetailFragment())
            .addToBackStack(null)
            .commit()
    }
}