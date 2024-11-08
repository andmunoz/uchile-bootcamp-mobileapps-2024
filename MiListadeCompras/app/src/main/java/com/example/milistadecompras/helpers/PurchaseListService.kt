package com.example.milistadecompras.helpers

import com.example.milistadecompras.data.PurchaseListItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PurchaseListService {
    @GET("/purchase_lists.json")
    fun getPurchaseLists(): Response<List<PurchaseListItem>>

    @POST("/purchase_list.json")
    fun createPurchaseList(@Body purchaseList: PurchaseListItem): Response<PurchaseListItem>
}