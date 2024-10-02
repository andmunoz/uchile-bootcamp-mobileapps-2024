package com.example.milistadecompras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PurchaseListAdapter(val itemList: List<PurchaseListItem>) :
    RecyclerView.Adapter<PurchaseListAdapter.MyViewHolder>() {
        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val purchaseListName: TextView = itemView.findViewById(R.id.purchase_list_name)
            val purchaseListDate: TextView = itemView.findViewById(R.id.purchase_list_date)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.lista_de_compras, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.purchaseListName.text = itemList[position].purchaseListName.toString()
            holder.purchaseListDate.text = itemList[position].purchaseListDate.toString()
        }

        override fun getItemCount() = itemList.size
    }