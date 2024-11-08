package com.example.servicesexample.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WebService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/${i}")
    suspend fun getPost(i: Int): Response<Post>

    @POST("posts")
    suspend fun createPost(@Body post: Post): Response<Post>
}

