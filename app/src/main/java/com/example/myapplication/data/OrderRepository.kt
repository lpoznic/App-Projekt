package com.example.myapplication.data

interface OrderRepository {
    suspend fun addOrder(order: Order)
    suspend fun getOrderById(orderId: String): Order?
    suspend fun getAllOrders(): List<Order>
    suspend fun updateOrder(order: Order)
    suspend fun deleteOrder(orderId: String)
}
