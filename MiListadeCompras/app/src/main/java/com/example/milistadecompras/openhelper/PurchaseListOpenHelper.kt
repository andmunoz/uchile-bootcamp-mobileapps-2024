package com.example.milistadecompras.openhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PurchaseListOpenHelper(context: Context): SQLiteOpenHelper(context, "purchase_list.db", null, 1) {
    override fun onCreate(bd: SQLiteDatabase?) {
        bd?.execSQL("CREATE TABLE purchase_list (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, date TEXT, period TEXT)")
    }

    override fun onUpgrade(bd: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        bd?.execSQL("DROP TABLE IF EXISTS purchase_list")
        onCreate(bd)
    }
}