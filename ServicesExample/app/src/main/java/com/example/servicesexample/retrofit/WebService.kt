package com.example.servicesexample.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WebService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/{i}")
    suspend fun getPost(@Path("i") i: Int): Response<Post>

    @POST("posts")
    suspend fun createPost(@Body post: Post): Response<Post>
}

