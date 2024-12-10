package com.example.servicesexample.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebServiceController(val baseUrl: String, val token: String) {
    // Creamos una referencia para el servicio web
    private val webService: WebService

    init {
        val retrofit: Retrofit
        if (token.isNotEmpty() || token != "") {
            // Configuramos el cliente OkHttp
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(token))
                .build()

            // Configuramos el cliente Retrofit
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        else {
            // Configuramos el cliente Retrofit sin autenticación
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        // Creamos una instancia del servicio web
        webService = retrofit.create(WebService::class.java)
    }

    suspend fun getPosts() = webService.getPosts()
    suspend fun getPost(i: Int) = webService.getPost(i)
    suspend fun createPost(post: Post) = webService.createPost(post)
}

