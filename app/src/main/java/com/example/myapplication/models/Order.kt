package com.example.myapplication.models

import java.text.SimpleDateFormat
import java.util.Date

val dateFormat = SimpleDateFormat("dd/MM/yyyy")

data class Order (
     val shirt: List<Shirt> = emptyList(),
     val address:String = "",
     val eMail:String ="",
     val phoneNumber:String ="",
     val ordered:Boolean = false,
     val orderId:Int = 0,
     val customerName:String = "",
     val orderDate: String = dateFormat.format(Date())
)