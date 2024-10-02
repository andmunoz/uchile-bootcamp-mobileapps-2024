package com.example.milistadecompras

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PurchaseList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PurchaseListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_purchase_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.purchase_list_recycler)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val itemList: List<PurchaseListItem> = listOf()
        adapter = PurchaseListAdapter(itemList)
        recyclerView.adapter = adapter
    }
}