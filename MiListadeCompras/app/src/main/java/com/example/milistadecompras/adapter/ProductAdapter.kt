package com.example.milistadecompras.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.milistadecompras.R
import com.example.milistadecompras.data.ProductItem
import com.example.milistadecompras.data.PurchaseListProductItem

class ProductAdapter(var itemList: List<PurchaseListProductItem>) :
    // Clase adapter para el RecyclerView
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
        // Clase interna para el ViewHolder
        class MyViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
            val productName: TextView = itemView.findViewById(R.id.product_name)
            val productQuantity: TextView = itemView.findViewById(R.id.product_qty)

            // Inicializa el listener para el menú de contexto
            init {
                itemView.setOnCreateContextMenuListener(this)
            }

            // Implementa el método para el menú de contexto
            override fun onCreateContextMenu(
                menu: ContextMenu?,
                v: View?,
                menuInfo: ContextMenu.ContextMenuInfo?
            ) { }
        }

        // Crea el ViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)
            return MyViewHolder(itemView)
        }

        // Enlaza los datos con el ViewHolder
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.productName.text = itemList[position].product.productName.toString()
            holder.productQuantity.text = itemList[position].quantity.toString()
        }

        // Devuelve el número de elementos en la lista
        override fun getItemCount() = itemList.size

        // Agrega un nuevo elemento a la lista
        fun addItem(item: ProductItem, quantity: Int) {
            itemList += PurchaseListProductItem(item, quantity)
            notifyItemInserted(getItemCount() - 1)
        }

        // Obtiene un elemento de la lista
        fun getItem(position: Int): PurchaseListProductItem {
            return itemList[position]
        }

        // Limpia el contenido de la lista
        fun clear() {
            itemList = emptyList()
            notifyDataSetChanged()
        }
    }