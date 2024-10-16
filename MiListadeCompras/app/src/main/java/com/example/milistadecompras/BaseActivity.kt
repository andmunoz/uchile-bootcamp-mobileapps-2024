package com.example.milistadecompras

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            R.id.app_bar_switch -> {
                return true
            }
            R.id.app_bar_setup -> {
                return true
            }
            R.id.app_bar_help_support -> {
                return true
            }
            R.id.app_bar_help_contact -> {
                return true
            }
            else -> return false
        }
    }
}