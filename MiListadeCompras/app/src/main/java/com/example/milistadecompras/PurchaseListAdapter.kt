package com.example.milistadecompras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PurchaseListAdapter(val itemList: List<PurchaseListItem>) :
    // Clase adapter para el RecyclerView
    RecyclerView.Adapter<PurchaseListAdapter.MyViewHolder>() {
        // Clase interna para el ViewHolder
        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val purchaseListName: TextView = itemView.findViewById(R.id.purchase_list_name)
            val purchaseListDate: TextView = itemView.findViewById(R.id.purchase_list_date)
        }

        // Crea el ViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.lista_de_compras, parent, false)
            return MyViewHolder(itemView)
        }

        // Enlaza los datos con el ViewHolder
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.purchaseListName.text = itemList[position].purchaseListName.toString()
            holder.purchaseListDate.text = itemList[position].purchaseListDate.toString()
        }

        // Devuelve el número de elementos en la lista
        override fun getItemCount() = itemList.size
    }