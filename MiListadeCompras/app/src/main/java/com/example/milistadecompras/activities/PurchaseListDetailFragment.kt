package com.example.milistadecompras.activities

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.milistadecompras.R
import com.example.milistadecompras.openhelper.PurchaseListOpenHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PurchaseListDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PurchaseListDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var context: Context
    private lateinit var purchaseListName: EditText
    private lateinit var purchaseListDate: EditText
    private lateinit var purchaseListPeriod: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchase_list_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        context = view.context

        // Referencia a los widgets del fragment
        purchaseListName = view.findViewById<EditText>(R.id.purchaseNameEditText)
        purchaseListDate = view.findViewById<EditText>(R.id.purchaseDateEditText)
        purchaseListPeriod = view.findViewById<EditText>(R.id.purchasePeriodEditText)
        saveButton = view.findViewById<Button>(R.id.saveButton)
        
        // Agregar listener al botón
        saveButton.setOnClickListener {
            onSaveButtonClick(it)
        }
    }

    fun onSaveButtonClick(view: View) {
        // Guardar en la base de datos
        val dbHelper = PurchaseListOpenHelper(this.context)
        val db = dbHelper.writableDatabase

        // Inserta los valores en la base de datos
        val values = ContentValues().apply {
            put("name", purchaseListName.text.toString())
            put("date", purchaseListDate.text.toString())
            put("period", purchaseListPeriod.text.toString())
        }
        db.insert("purchase_list", null, values)
        db.close()

        // Información al usuario
        Toast.makeText(this.context, "Lista de compras ${purchaseListName.text} creada exitosamente", Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PurchaseListDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PurchaseListDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}