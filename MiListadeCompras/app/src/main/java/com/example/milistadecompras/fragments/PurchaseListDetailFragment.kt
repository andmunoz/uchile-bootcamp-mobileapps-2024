package com.example.milistadecompras.fragments

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milistadecompras.R
import com.example.milistadecompras.adapter.ProductAdapter
import com.example.milistadecompras.data.ProductItem
import com.example.milistadecompras.data.PurchaseListItem
import com.example.milistadecompras.data.PurchaseListProductItem
import com.example.milistadecompras.helpers.PurchaseListOpenHelper
import com.example.milistadecompras.helpers.PurchaseListServiceController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val BASE_URL = "https://ejemplo-firebase-657d0-default-rtdb.firebaseio.com"

class PurchaseListDetailFragment : Fragment(), ProductFormDialog.OnProductAddedListener {
    // Referencia al contexto de la actividad
    private lateinit var context: Context
    private lateinit var webServiceController: PurchaseListServiceController

    // Referencias a todos los widgets del fragment que requieren uso
    private lateinit var purchaseListName: EditText
    private lateinit var purchaseListDate: EditText
    private lateinit var purchaseListPeriod: EditText
    private lateinit var saveButton: Button
    private lateinit var addProductButton: Button
    private lateinit var productList: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: ProductAdapter

    // Usar el patrón de Factory Method cuando se pasen parámetros al fragment
    /* companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PurchaseListDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("param1", param1)
                    putString("param2", param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout para este fragment
        return inflater.inflate(R.layout.fragment_purchase_list_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Guardar el contexto
        context = view.context

        // Referencia a los widgets del fragment
        purchaseListName = view.findViewById<EditText>(R.id.purchase_list_name_input)
        purchaseListDate = view.findViewById<EditText>(R.id.purchase_list_date_input)
        purchaseListPeriod = view.findViewById<EditText>(R.id.purchase_list_period_input)
        saveButton = view.findViewById<Button>(R.id.save_button)
        addProductButton = view.findViewById<Button>(R.id.add_product_button)
        productList = view.findViewById<RecyclerView>(R.id.product_list)

        // Agregar listener a los botones
        saveButton.setOnClickListener {
            onSaveButtonClick(it)
        }
        addProductButton.setOnClickListener {
            onAddProductButtonClick(it)
        }

        // Configurar el recycler
        layoutManager = LinearLayoutManager(view.context)
        productList.layoutManager = layoutManager
        adapter = ProductAdapter(listOf())
        productList.adapter = adapter

        // Inicializar el WebServiceController
        webServiceController = PurchaseListServiceController(BASE_URL)
    }

    // Función para guardar la lista de compras
    private fun onSaveButtonClick(view: View) {
        // Guardar en la base de datos
        val dbHelper = PurchaseListOpenHelper(this.context)
        val db = dbHelper.writableDatabase

        // Inserta los valores en la base de datos
        val values = ContentValues().apply {
            put("name", purchaseListName.text.toString())
            put("date", purchaseListDate.text.toString())
            put("period", purchaseListPeriod.text.toString())
            put("status", 1)
        }
        db.insert("purchase_list", null, values)
        db.close()

        // TODO: Falta agregar los productos a la lista de compras al momento de crearla

        // Guardar en la nube
        CoroutineScope(Dispatchers.IO).launch {
            val purchaseList = PurchaseListItem(100,
                purchaseListName.text.toString(),
                purchaseListDate.text.toString(),
                purchaseListPeriod.text.toString(),
                0)
            val response = webServiceController.createPurchaseList(purchaseList)
            if (!response.isSuccessful) {
                Log.e("PurchaseList", "Error al subir la lista: ${response.errorBody()}")
                return@launch
            }
        }

        // Información al usuario
        Toast.makeText(
            this.context,
            "Lista de compras ${purchaseListName.text} creada exitosamente",
            Toast.LENGTH_SHORT
        ).show()

        // Volver a la actividad principal
        parentFragmentManager.popBackStack()
    }

    // Función para activar el Dialog para agregar un producto existente
    private fun onAddProductButtonClick(view: View) {
        // Abrir el diálogo de productos
        val dialog = ProductFormDialog()
        dialog.show(childFragmentManager, "ProductFormDialog")
    }

    // Información al usuario al agregar un producto
    override fun onProductAdded(product: ProductItem, quantity: Int) {
        // Agregar el producto a la lista
        val itemList = adapter.itemList.toMutableList()
        itemList.add(PurchaseListProductItem(product, quantity))
        adapter.clear()
        adapter.itemList = itemList
        adapter.notifyDataSetChanged()

        // Mostrar qué producto llegó
        Toast.makeText(context, "Producto agregado: ${product.productName}", Toast.LENGTH_SHORT).show()
    }
}