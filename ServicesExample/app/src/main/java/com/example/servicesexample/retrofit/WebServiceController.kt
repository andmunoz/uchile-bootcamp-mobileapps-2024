package com.example.servicesexample.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebServiceController(val baseUrl: String) {
    // Creamos una referencia para el servicio web
    private val webService: WebService

    init {
        // Configuramos el cliente Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Creamos una instancia del servicio web
        webService = retrofit.create(WebService::class.java)
    }

    suspend fun getPosts() = webService.getPosts()
    suspend fun getPost(i: Int) = webService.getPost(i)
    suspend fun createPost(post: Post) = webService.createPost(post)
}

