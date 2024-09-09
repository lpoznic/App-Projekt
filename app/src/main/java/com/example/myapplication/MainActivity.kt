package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Order
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.reflect.Modifier

private lateinit var db:FirebaseFirestore
private val orders: MutableList<Order> = mutableListOf()


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState:
                          Bundle?) {
        super.onCreate(savedInstanceState)

        db = FirebaseFirestore.getInstance()
        loadOrders()

        Log.d("MyApp", "MainActivity is on")
        setContent {
            Navigation()
        }
    }

    private fun loadOrders() {
        db.collection("orders").get()
            .addOnSuccessListener { result ->
                orders.clear()
                orders.addAll(result.toObjects(Order::class.java))

            }
    }
}
