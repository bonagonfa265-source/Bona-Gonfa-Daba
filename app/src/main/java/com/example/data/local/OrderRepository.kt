package com.example.data.local

import com.example.data.model.OrderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlin.random.Random

class OrderRepository(private val orderDao: OrderDao) {

    val allOrders: Flow<List<OrderEntity>> = orderDao.getAllOrders()

    suspend fun insertOrder(order: OrderEntity): Long {
        return orderDao.insertOrder(order)
    }

    suspend fun updateOrder(order: OrderEntity) {
        orderDao.updateOrder(order)
    }

    suspend fun deleteOrder(order: OrderEntity) {
        orderDao.deleteOrder(order)
    }

    suspend fun deleteOrderById(id: Long) {
        orderDao.deleteOrderById(id)
    }

    suspend fun getOrderById(id: Long): OrderEntity? {
        return orderDao.getOrderById(id)
    }

    suspend fun ensureSampleOrderIfEmpty() {
        val currentOrders = allOrders.firstOrNull()
        if (currentOrders.isNullOrEmpty()) {
            val sample = OrderEntity(
                orderNumber = "BG-${Random.nextInt(1000, 9999)}",
                serviceId = "tshirt",
                serviceTitle = "Custom T-Shirt",
                customerName = "Bona Customer",
                customerPhone = "0919199459",
                customerAddress = "Bole Medhanialem, Addis Ababa",
                telegramUsername = "@bonacustomer",
                selectedColor = "Obsidian Black",
                selectedSize = "L",
                selectedOption = "Front + Back Dual Print",
                customText = "Bona Graphics 💎🖤",
                quantity = 2,
                notes = "Gold lettering on chest, clean minimalist font. Please notify before dispatch.",
                totalPriceEtb = 1300,
                status = "In Printing",
                createdAtMillis = System.currentTimeMillis() - 86400000L // 1 day ago
            )
            orderDao.insertOrder(sample)
        }
    }
}
