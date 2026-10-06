package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bona_orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderNumber: String,
    val serviceId: String,
    val serviceTitle: String,
    val customerName: String,
    val customerPhone: String,
    val customerAddress: String,
    val telegramUsername: String = "",
    val selectedColor: String = "",
    val selectedSize: String = "",
    val selectedOption: String = "",
    val customText: String = "",
    val quantity: Int = 1,
    val notes: String = "",
    val totalPriceEtb: Int,
    val status: String = "Order Placed",
    val createdAtMillis: Long = System.currentTimeMillis()
)
