package com.example.milistadecompras

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        // Obtención del nombre de usuario de la actividad anterior
        val username = intent.getStringExtra("username")
        Toast.makeText(this, "Bienvenido, $username", Toast.LENGTH_LONG).show()

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

        // Inicialización del Bottom Navigation Menu
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_home -> {
                    Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_nav_search -> {
                    Toast.makeText(this, "Buscar", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_nav_profile -> {
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

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            // Se obtiene el resultado de la actividad anterior
            val data = result.data

            // Si el resultado es OK, se obtiene el nombre de la lista de compras
            val purchaseListName = data?.getStringExtra("purchaseListName")
            val purchaseListDate = data?.getStringExtra("purchaseListDate")
            val purchaseListPeriod = data?.getStringExtra("purchaseListPeriod")

            // Se crea un nuevo elemento de la lista de compras
            val newPurchaseList = PurchaseListItem(purchaseListName, purchaseListDate, purchaseListPeriod)

            // Se notifica al adaptador de que se ha insertado un nuevo elemento
            adapter.addItem(newPurchaseList)
            adapter.notifyItemInserted(adapter.itemCount - 1)

            Toast.makeText(this, "Lista de compras creada: $purchaseListName", Toast.LENGTH_LONG).show()
        }
    }

    fun onAddButtonClick(view: View) {
        // Se crea un intent para iniciar la actividad de creación de lista de compras
        val intent = Intent(this, PurchaseListCreate::class.java)

        // Se inicia la actividad de creación de lista de compras
        startForResult.launch(intent)
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