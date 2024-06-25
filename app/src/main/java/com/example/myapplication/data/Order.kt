package com.example.myapplication.data

data class Order(
     val id: String,
     val customerName: String,
     val address: String,
     val email: String,
     val phoneNumber: String,
     val shirts: List<Shirt>,
     val status: String,
     val totalPrice: Double
)