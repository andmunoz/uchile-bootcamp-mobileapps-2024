package com.example.milistadecompras.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PurchaseListOpenHelper(context: Context): SQLiteOpenHelper(context, "purchase_list.db", null, 3) {
    override fun onCreate(bd: SQLiteDatabase?) {
        // Crea la tabla de listas de compras
        bd?.execSQL("CREATE TABLE purchase_list (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, date TEXT, period TEXT, status INTEGER)")

        // Crea la tabla de productos con una carga inicial de productos
        bd?.execSQL("CREATE TABLE product (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")
        bd?.insert("product", null, ContentValues().apply {put("name", "Manzana")})
        bd?.insert("product", null, ContentValues().apply {put("name", "Naranja")})
        bd?.insert("product", null, ContentValues().apply {put("name", "Plátano")})
        bd?.insert("product", null, ContentValues().apply {put("name", "Tallarines")})
        bd?.insert("product", null, ContentValues().apply {put("name", "Arroz")})

        // Crea la tabla de asociación entre productos y listas de compras
        bd?.execSQL("CREATE TABLE product_purchase_list (id INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER, purchase_list_id INTEGER, quantity INTEGER)")
    }

    override fun onUpgrade(bd: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Actualiza la base de datos
        bd?.execSQL("DROP TABLE IF EXISTS product_purchase_list")
        bd?.execSQL("DROP TABLE IF EXISTS product")
        bd?.execSQL("DROP TABLE IF EXISTS purchase_list")
        onCreate(bd)
    }
}