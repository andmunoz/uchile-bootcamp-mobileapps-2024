package com.example.milistadecompras.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milistadecompras.adapter.PurchaseListAdapter
import com.example.milistadecompras.R
import com.example.milistadecompras.data.PurchaseListItem
import com.example.milistadecompras.openhelper.PurchaseListOpenHelper

class PurchaseList : BaseActivity() {
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

        // Inflamos el Toolbar y el Bottom Navigation
        super.inflateToolbar()
        super.inflateBottomNavigationMenu()

        // Obtención del RecyclerView
        recyclerView = findViewById(R.id.purchase_list_recycler)

        // Configuración del RecyclerView
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Inicialización de la lista de elementos
        adapter = PurchaseListAdapter(listOf())
        recyclerView.adapter = adapter

        // Registro del menú de contexto
        registerForContextMenu(recyclerView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        val dbHelper = PurchaseListOpenHelper(this)
        val db = dbHelper.readableDatabase

        val cursor = db.query("purchase_list", null, null, null, null, null, "name")
        itemList = mutableListOf()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(1)
                val date = getString(2)
                val period = getString(3)
                itemList += PurchaseListItem(name, date, period)
            }
        }
        cursor.close()
        db.close()

        adapter.clear()
        adapter.itemList = itemList
        adapter.notifyDataSetChanged()
    }

    fun onAddButtonClick(view: View) {
        // Se crea un intent para iniciar la actividad de creación de lista de compras
        val intent = Intent(this, PurchaseListCreate::class.java)

        // Se inicia la actividad de creación de lista de compras
        startActivity(intent)
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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // Llamada al método onContextItemSelected de AppCompatActivity
        super.onContextItemSelected(item)

        // Se obtiene la posición del elemento seleccionado
        /* val info = item.menuInfo as RecyclerView.AdapterContextMenuInfo
        val position = info.position

        // Se obtiene el elemento seleccionado
        val selectedItem = adapter.getItem(position) */

        // Se manejan las opciones del menú de contexto
        when (item.itemId) {
            R.id.context_menu_edit_purchase_list -> {
                // Toast.makeText(this, "Editar lista de compras: ${selectedItem.purchaseListName}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Editar lista de compras", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.context_menu_purchase_list_delete -> {
                // Toast.makeText(this, "Eliminar lista de compras: ${selectedItem.purchaseListName}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Eliminar lista de compras", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return false
        }
    }
}