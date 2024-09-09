package com.example.myapplication.repository

import com.example.myapplication.data.Order
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.tasks.await

class OrderRepository(
    private val database: FirebaseFirestore = Firebase.firestore
) {

    suspend fun getOrders(): List<Order> {
        val data = database.collection("orders").get().await()
        val orders = parseDocumentsFromDatabase(data.documents)
        return orders
    }

    suspend fun addOrder(order: Order) {
        database.collection("orders").add(order).await()
    }

    suspend fun updateOrder(order: Order) {
        database.collection("orders").document(order.id).set(order).await()
    }

    suspend fun deleteOrder(orderId: String) {
        database.collection("orders").document(orderId).delete().await()
    }


    suspend fun getOrderById(id: String): Order {
        val data = database.collection("orders").get().await()
        return data.documents.find { it.id == id }?.let { document ->
            parseOrder(document)
        } ?: Order()
    }

    private fun parseDocumentsFromDatabase(documents: List<DocumentSnapshot>): List<Order> {
        val orders = mutableListOf<Order>()
        documents.forEach { document ->
            val order = parseOrder(document)
            orders.add(order)
        }
        return orders
    }

    private fun parseOrder(document: DocumentSnapshot): Order {
        var order = document.toObject(Order::class.java) ?: Order()
        order = order.copy(id = document.id) // Set the document ID as the order ID
        return order
    }


}