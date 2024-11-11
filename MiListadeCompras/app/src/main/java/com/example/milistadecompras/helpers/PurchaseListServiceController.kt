package com.example.milistadecompras.helpers

import com.example.milistadecompras.data.PurchaseListItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PurchaseListServiceController(val baseUrl: String) {
    private lateinit var purchaseListService: PurchaseListService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        purchaseListService = retrofit.create(PurchaseListService::class.java)
    }

    suspend fun getPurchaseLists() = purchaseListService.getPurchaseLists()
    suspend fun getPurchaseList(id: Int) = purchaseListService.getPurchaseList(id)
    suspend fun createPurchaseList(purchaseList: PurchaseListItem) = purchaseListService.createPurchaseList(purchaseList)
    suspend fun updatePurchaseList(id: Int, purchaseList: PurchaseListItem) = purchaseListService.updatePurchaseList(id, purchaseList)
    suspend fun deletePurchaseList(id: Int) = purchaseListService.deletePurchaseList(id)
}