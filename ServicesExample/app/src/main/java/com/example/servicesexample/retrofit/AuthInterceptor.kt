package com.example.servicesexample.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token") // Agrega el encabezado de autorización
            .build()
        return chain.proceed(request)
    }
}