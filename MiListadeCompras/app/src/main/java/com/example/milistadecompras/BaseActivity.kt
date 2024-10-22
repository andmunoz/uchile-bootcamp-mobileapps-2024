package com.example.milistadecompras

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun inflateToolbar() {
        // Reemplazar el ActionBar por el Toolbar
        val toolbar: Toolbar = findViewById(R.id.main_toolbar_view)
        setSupportActionBar(toolbar)
    }

    fun inflateBottomNavigationMenu() {
        // Inicialización del Bottom Navigation Menu
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_my_lists -> {
                    Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_nav_sync -> {
                    Toast.makeText(this, "Buscar", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_nav_notifications -> {
                    Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    Log.e("PurchaseList", "Item de menú no reconocido: ${item.itemId}")
                    false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Llama al onCreateOptionsMenu de AppCompatActivity
        super.onCreateOptionsMenu(menu)

        // Infla el menú de opciones desde el archivo XML
        menuInflater.inflate(R.menu.options_menu, menu)

        // Devuelve true para que se muestre el menú de opciones
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Llama al onOptionsItemSelected de AppCompatActivity
        super.onOptionsItemSelected(item)

        // Maneja la selección de elementos del menú de opciones
        when (item.itemId) {
            R.id.app_bar_search -> {
                return true
            }
            R.id.app_bar_my_profile -> {
                return true
            }
            else -> return false
        }
    }
}