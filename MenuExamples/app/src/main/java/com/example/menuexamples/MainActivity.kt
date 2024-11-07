package com.example.menuexamples

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar la Toolbar en vez del ActionBar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar el RecyclerView
        val myListView: RecyclerView = findViewById(R.id.my_list_view)
        myListView.layoutManager = LinearLayoutManager(this)
        val nombres = listOf("Ana", "Pedro", "María", "Juan", "Lucía", "Carlos")
        myListView.adapter = MyListAdapter(nombres)
        registerForContextMenu(myListView)

        // Configurar el BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_search -> {
                    Toast.makeText(this, "Search selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> {
                    Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_more -> {
                    openBottomSheet()
                    true
                }
                else -> false
            }
        }
    }

    private fun openBottomSheet() {
        // Crear una instancia del Bottom Sheet
        bottomSheetDialog = BottomSheetDialog(this)

        // Inflar el diseño del Bottom Sheet
        val view = layoutInflater.inflate(R.layout.bottom_sheet_menu, null)

        // Configurar el Bottom Sheet con el diseño inflado
        bottomSheetDialog.setContentView(view)

        // Mostrar el Bottom Sheet
        bottomSheetDialog.show()
    }

    fun activateBottomSheetFirstOption(view: View) {
        Toast.makeText(this, "Opción 1 seleccionada", Toast.LENGTH_SHORT).show()
        bottomSheetDialog.dismiss()  // Cierra el Bottom Sheet
    }

    fun activateBottomSheetSecondOption(view: View) {
        Toast.makeText(this, "Opción 2 seleccionada", Toast.LENGTH_SHORT).show()
        bottomSheetDialog.dismiss()  // Cierra el Bottom Sheet
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_help -> {
                Toast.makeText(this, "Help selected", Toast.LENGTH_SHORT).show()
                var navigationActivityIntent = Intent(this, DrawerNavigationActivity::class.java)
                startActivity(navigationActivityIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                Toast.makeText(this, "Edit selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.delete -> {
                Toast.makeText(this, "Delete selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.popup_menu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.action_edit -> {
                    Toast.makeText(this, "Edit selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_delete -> {
                    Toast.makeText(this, "Delete selected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}
