package com.example.milistadecompras.data

// Data class para representar un elemento de la lista de compras
data class PurchaseListItem(val purchaseListId: Int?,
                            val purchaseListName: String?,
                            val purchaseListDate: String?,
                            val purchaseListPeriod: String?,
                            val purchaseListStatus: Int?)
