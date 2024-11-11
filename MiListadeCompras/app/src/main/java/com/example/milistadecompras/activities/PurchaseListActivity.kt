package com.example.milistadecompras.activities

import android.content.ContentValues
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
import com.example.milistadecompras.data.PurchaseListItem
import com.example.milistadecompras.fragments.PurchaseListDetailFragment
import com.example.milistadecompras.helpers.PurchaseListOpenHelper
import com.example.milistadecompras.helpers.PurchaseListServiceController
import com.example.milistadecompras.helpers.WebServiceHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

// Declaramos una constante
const val BASE_URL = "https://ejemplo-firebase-657d0-default-rtdb.firebaseio.com"

class PurchaseListActivity : AppCompatActivity() {
    private lateinit var webServiceHelper: WebServiceHelper
    private lateinit var webServiceController: PurchaseListServiceController

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

        // Inicializamos el WebServiceHelper
        webServiceHelper = WebServiceHelper(BASE_URL)
        webServiceController = PurchaseListServiceController(BASE_URL)
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
                    // syncPurchaseListsWithURLConnection()
                    syncPurchaseListWithRetrofit()
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

    private fun syncPurchaseListsWithURLConnection() {
        Toast.makeText(this, "Sincronizando...", Toast.LENGTH_SHORT).show()

        // Corrutina para sincronizar
        CoroutineScope(Dispatchers.IO).launch {
            /* Paso 1: Descargar las listas que no están en el dispositivo */
            // Descargar las listas existentes en la nube
            val purchaseLists = webServiceHelper.makeGETRequest("/purchase_lists.json")

            withContext(Dispatchers.Main) {
                // Validar si tengo resultados
                if (purchaseLists == null) {
                    Log.e("PurchaseList", "No hay listas en el servicio")
                    return@withContext
                }

                // Procesar las listas descargadas
                val purchaseListsArray = JSONArray(purchaseLists)
                for (i in 0 until purchaseListsArray.length()) {
                    val purchaseList = purchaseListsArray.getJSONObject(i)
                    val id = purchaseList.getInt("id")
                    val name = purchaseList.getString("name")
                    val date = purchaseList.getString("date")
                    val period = purchaseList.getString("period")
                    val status = purchaseList.getInt("status")

                    val dbHelper = PurchaseListOpenHelper(this@PurchaseListActivity)
                    val db = dbHelper.writableDatabase

                    // val cursor = db.query("purchase_list", null, "id = ?", arrayOf(id.toString()), null, null, null)

                    val values = ContentValues().apply {
                        put("id", id)
                        put("name", name)
                        put("date", date)
                        put("period", period)
                        put("status", status)
                    }
                    db.insert("purchase_list", null, values)
                    db.close()
                }
            }

            /* Paso 2: Subir las listas que NO están actualizadas en la nube */
            // Obtener las listas de la base de datos
            val dbHelper = PurchaseListOpenHelper(this@PurchaseListActivity)
            val db = dbHelper.readableDatabase

            // Hacemos la consulta y la recorremos
            val cursor = db.query("purchase_list", null, "status = 1", null, null, null, null)

            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(0)
                    val name = getString(1)
                    val date = getString(2)
                    val period = getString(3)

                    val purchaseList = JSONObject()
                    purchaseList.put("id", id)
                    purchaseList.put("name", name)
                    purchaseList.put("date", date)
                    purchaseList.put("period", period)
                    purchaseList.put("status", 0)

                    webServiceHelper.makePOSTRequest("/purchase_lists.json", purchaseList.toString())

                    db.update("purchase_list", ContentValues().apply { put("status", 0) }, "id = ?", arrayOf(id.toString()))
                }
            }
            cursor.close()
            db.close()
        }
    }

    private fun syncPurchaseListWithRetrofit() {
        Toast.makeText(this, "Sincronizando...", Toast.LENGTH_SHORT).show()

        CoroutineScope(Dispatchers.IO).launch {
            val purchaseLists = webServiceController.getPurchaseLists()
            withContext(Dispatchers.Main) {
                if (purchaseLists.isSuccessful) {
                    val purchaseListsArray = JSONArray(purchaseLists.body())
                    for (i in 0 until purchaseListsArray.length()) {
                        val purchaseList = purchaseListsArray.getJSONObject(i)
                        val id = purchaseList.getInt("id")
                        val name = purchaseList.getString("name")
                        val date = purchaseList.getString("date")
                        val period = purchaseList.getString("period")
                        val status = purchaseList.getInt("status")

                        val dbHelper = PurchaseListOpenHelper(this@PurchaseListActivity)
                        val db = dbHelper.writableDatabase
                        val values = ContentValues().apply {
                            put("id", id)
                            put("name", name)
                            put("date", date)
                            put("period", period)
                            put("status", status)
                        }
                        db.insert("purchase_list", null, values)
                        db.close()
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val dbHelper = PurchaseListOpenHelper(this@PurchaseListActivity)
            val db = dbHelper.readableDatabase

            // Hacemos la consulta y la recorremos
            val cursor = db.query("purchase_list", null, "status = 1", null, null, null, null)

            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(0)
                    val name = getString(1)
                    val date = getString(2)
                    val period = getString(3)

                    val purchaseList = PurchaseListItem(id, name, date, period, 0)

                    val response = webServiceController.createPurchaseList(purchaseList)
                    if (!response.isSuccessful) {
                        Log.e("PurchaseList", "Error al subir la lista: ${response.errorBody()}")
                        continue
                    }

                    db.update("purchase_list", ContentValues().apply { put("status", 0) }, "id = ?", arrayOf(id.toString()))
                }
            }
            cursor.close()
            db.close()
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