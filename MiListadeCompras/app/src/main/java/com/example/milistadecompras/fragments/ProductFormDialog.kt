package com.example.milistadecompras.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private lateinit var productImageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var photoCameraButton: Button

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

        // Referenciamos el botón de la cámara y le agregamos el listener
        photoCameraButton = view.findViewById<Button>(R.id.photo_camera_button)
        photoCameraButton.setOnClickListener {
            onPhotoCameraButtonClick(it)
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

        // TODO: Obtener la imagen del producto y guardarlo en la base de datos
        // val image = productImageView

        // Enviamos la información al fragment
        val listener = parentFragment as? OnProductAddedListener
        listener?.onProductAdded(selectedProduct, quantity)

        // Cerramos el diálogo
        dismiss()
    }

    // Función para manejar el botón de la cámara
    private fun onPhotoCameraButtonClick(view: View) {
        // Validamos el permiso de la cámara (si no está otorgado, lo solicitamos)
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), 0)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // Si el permiso fue otorgado, abrimos la cámara
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Abrimos la cámara y obtenemos la imagen resultante (foto)
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val imageBitmap = intent?.extras?.get("data") as Bitmap
                productImageView.setImageBitmap(imageBitmap)
            }
        }

    // Interface de comunicación para enviar el producto seleccionado al fragment
    interface OnProductAddedListener {
        fun onProductAdded(product: ProductItem, quantity: Int)
    }
}