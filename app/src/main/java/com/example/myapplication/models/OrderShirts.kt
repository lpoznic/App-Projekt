package com.example.myapplication.models

data class OrderShirts (
     val orderId:Int = 0,
     val shirts:List<Shirt> = emptyList(),
)