package com.example.menuexamples

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView

class MyListAdapter (
    private val nombres: List<String>):
    RecyclerView.Adapter<MyListAdapter.ViewHolder>() {

    // ViewHolder contiene la vista del ítem
    class ViewHolder(view: View):
            RecyclerView.ViewHolder(view),
            View.OnCreateContextMenuListener {
        val itemText: TextView = view.findViewById(R.id.itemText)

        init {
            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: android.view.ContextMenu?,
            v: View?,
            menuInfo: android.view.ContextMenu.ContextMenuInfo?
        ) { }
    }

    // Inflamos la vista del ítem desde item_view.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_item, parent, false)
        return ViewHolder(view)
    }

    // Asignamos los datos al TextView de cada ítem
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemText.text = nombres[position]
    }

    // Tamaño de la lista
    override fun getItemCount(): Int = nombres.size
}