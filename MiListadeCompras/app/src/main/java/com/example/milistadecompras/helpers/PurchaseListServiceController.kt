package com.example.milistadecompras.helpers

import com.example.milistadecompras.data.PurchaseListItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PurchaseListServiceController(val baseUrl: String) {
    private lateinit var purchaseListService: PurchaseListService

    init {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        purchaseListService = retrofit.create(PurchaseListService::class.java)
    }

    suspend fun getPurchaseLists() = purchaseListService.getPurchaseLists()
    suspend fun createPurchaseList(purchaseList: PurchaseListItem) = purchaseListService.createPurchaseList(purchaseList)
}