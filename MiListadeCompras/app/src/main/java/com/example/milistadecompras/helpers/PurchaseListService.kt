package com.example.milistadecompras.helpers

import com.example.milistadecompras.data.PurchaseListItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PurchaseListService {
    @GET("/purchase_lists.json")
    fun getPurchaseLists(): Response<List<PurchaseListItem>>

    @GET("/purchase_lists/{id}.json")
    fun getPurchaseList(@Path("id") id: Int): Response<PurchaseListItem>

    @POST("/purchase_list.json")
    fun createPurchaseList(@Body purchaseList: PurchaseListItem): Response<PurchaseListItem>

    @PUT("/purchase_lists/{id}.json")
    fun updatePurchaseList(@Path("id") id: Int, @Body purchaseList: PurchaseListItem): Response<PurchaseListItem>

    @DELETE("/purchase_lists/{id}.json")
    fun deletePurchaseList(@Path("id") id: Int): Response<Void>
}