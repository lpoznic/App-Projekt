package com.example.myapplication.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class FirestoreOrderRepository : OrderRepository {
    private val db = Firebase.firestore.collection("orders")

    override suspend fun addOrder(order: Order) {
        db.add(order).await()
    }

    override suspend fun getOrderById(orderId: String): Order? {
        val document = db.document(orderId).get().await()
        return document.toObject(Order::class.java)
    }

    override suspend fun getAllOrders(): List<Order> {
        val snapshot = db.get().await()
        return snapshot.toObjects(Order::class.java)
    }

    override suspend fun updateOrder(order: Order) {
        db.document(order.id).set(order).await()
    }

    override suspend fun deleteOrder(orderId: String) {
        db.document(orderId).delete().await()
    }
}