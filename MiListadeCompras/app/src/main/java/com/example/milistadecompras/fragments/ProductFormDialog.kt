package com.example.milistadecompras.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.milistadecompras.R
import com.example.milistadecompras.data.ProductItem
import com.example.milistadecompras.helpers.PurchaseListOpenHelper

class ProductFormDialog: DialogFragment() {
    // Declaramos una referencia al open helper
    private lateinit var purchaseListOpenHelper: PurchaseListOpenHelper

    // Declaramos variables que referencian a los widgets del dialog
    private lateinit var view: View
    private lateinit var productSpinner: Spinner
    private lateinit var productQuantity: EditText
    private lateinit var saveButton: Button

    // Declaramos una lista de productos cargados en el spinner
    private lateinit var productList: MutableList<ProductItem>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Obtenemos el builder de la clase AlertDialog
        val builder = AlertDialog.Builder(requireContext())

        // Inflamos el layout del diálogo
        val inflater = requireActivity().layoutInflater
        view = inflater.inflate(R.layout.dialog_product_form, null)

        // Configuramos el diálogo con el layout inflado
        builder.setView(view).setTitle("Agregar Producto")

        // Llenamos el spinner de productos
        fillProductSpinner(view)

        // Referenciamos el botón de guardar y le agreamos el listener
        saveButton = view.findViewById<Button>(R.id.save_product_button)
        saveButton.setOnClickListener {
            onSaveButtonClick(it)
        }

        // Finalizamos la creación del dialog
        return builder.create()
    }

    // Llena el spinner de productos
    private fun fillProductSpinner (view: View) {
        // Inicializamos el spinner
        productSpinner = view.findViewById<Spinner>(R.id.product_spinner)

        // Abrimos la base de datos
        purchaseListOpenHelper = PurchaseListOpenHelper(requireContext())
        val db = purchaseListOpenHelper.readableDatabase

        // Consultamos la tabla de productos
        val cursor = db.query("product", arrayOf("id", "name"), null, null, null, null, "name")

        // Recorremos el cursor para obtener los productos y sus nombres
        productList = ArrayList()
        val productNames: MutableList<String> = ArrayList()
        while (cursor.moveToNext()) {
            // Hacemos referencia al objeto ProductItem y lo agregamos a la lista
            val product = ProductItem(cursor.getInt(0), cursor.getString(1))
            productList.add(product)

            // Agregamos el nombre del producto a la lista de nombres
            productNames.add(product.productName.toString())
        }

        // Cerramos el cursor y la base de datos
        cursor.close()
        db.close()

        // Configuramos el adapter para el spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignamos el adapter al spinner
        productSpinner.adapter = adapter
    }

    // Función para manejar el botón guardar
    private fun onSaveButtonClick(view: View) {
        // Obtenemos el producto seleccionado
        val position = productSpinner.selectedItemPosition
        val selectedProduct = productList[position]
        productQuantity = this.view.findViewById(R.id.product_qty_text)

        // Obtenemos la cantidad del producto
        val quantity = productQuantity.text.toString().toInt()

        // Enviamos la información al fragment
        val listener = parentFragment as? OnProductAddedListener
        listener?.onProductAdded(selectedProduct, quantity)

        // Cerramos el diálogo
        dismiss()
    }

    // Interface de comunicación para enviar el producto seleccionado al fragment
    interface OnProductAddedListener {
        fun onProductAdded(product: ProductItem, quantity: Int)
    }
}