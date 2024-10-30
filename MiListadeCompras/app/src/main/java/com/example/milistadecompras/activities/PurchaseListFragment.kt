package com.example.milistadecompras.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milistadecompras.R
import com.example.milistadecompras.adapter.PurchaseListAdapter
import com.example.milistadecompras.data.PurchaseListItem
import com.example.milistadecompras.openhelper.PurchaseListOpenHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PurchaseListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PurchaseListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PurchaseListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var itemList: List<PurchaseListItem>
    private lateinit var context: Context

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
        return inflater.inflate(R.layout.fragment_purchase_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Guardar el contexto
        context = view.context

        // Obtención del RecyclerView
        recyclerView = view.findViewById(R.id.purchase_list_recycler)

        // Configuración del RecyclerView
        layoutManager = LinearLayoutManager(view.context)
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

        val dbHelper = PurchaseListOpenHelper(this.context)
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
                Toast.makeText(this.context, "Editar lista de compras", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.context_menu_purchase_list_delete -> {
                // Toast.makeText(this, "Eliminar lista de compras: ${selectedItem.purchaseListName}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this.context, "Eliminar lista de compras", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PurchaseListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PurchaseListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}