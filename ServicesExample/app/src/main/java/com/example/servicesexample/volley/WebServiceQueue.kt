package com.example.servicesexample.volley

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class WebServiceQueue(val baseURL: String, val context: Context) {
    // Declaramos la cola de peticiones de Volley
    private var queue = Volley.newRequestQueue(context)

    fun getData(resource: String, callback: (String) -> Unit) {
        // Realizamos la petición GET
        val jsonRequest = JsonArrayRequest(
            Request.Method.GET, baseURL + resource, null,
            { response ->
                callback(response.toString())
            },
            { error ->
                Log.e("Volley", error.toString())
                callback(error.toString())
            }
        )

        // Añadimos la petición a la cola
        queue.add(jsonRequest)
    }

    fun postData(resource: String, data: String, callback: (String) -> Unit) {
        // Realizamos la petición POST
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, baseURL + resource, null,
            { response ->
                callback(response.toString())
            },
            { error ->
                callback(error.toString())
            }
        )

        // Añadimos la petición a la cola
        queue.add(jsonObjectRequest)
    }
}