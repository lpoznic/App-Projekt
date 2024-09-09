package com.example.myapplication.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.UUID
import android.os.Parcelable

data class Order(
     val id: String = UUID.randomUUID().toString(),
     val customerName: String = "",
     val address: String = "",
     val email: String = "",
     val phoneNumber: String = "",
     val shirts: List<Shirt> = listOf(Shirt()),
     val status: String = "",
     val totalPrice: Double = 0.0,
     val dateCreated: java.util.Date = java.util.Date()
)

@Composable
fun rememberOrderState(order: Order?): MutableState<Order?> {
     return remember { mutableStateOf(order) }
}