package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.data.Order
import com.example.myapplication.data.Shirt
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore
        val shirt1 = Shirt(
            name = "Classic Tee",
            size = "M",
            imageUrl = "https://example.com/images/classic_tee.jpg",
            price = 15.99
        )

        val shirt2 = Shirt(
            name = "Striped Polo",
            size = "L",
            imageUrl = "https://example.com/images/striped_polo.jpg",
            price = 24.99
        )

        val shirt3 = Shirt(
            name = "Graphic Hoodie",
            size = "XL",
            imageUrl = "https://example.com/images/graphic_hoodie.jpg",
            price = 39.99
        )
        val ordersCollection = db.collection("orders") // Reference to the "orders" collection
        val newOrder = Order(
            id = "order123", // You might need to generate unique IDs yourself
            customerName = "John Doe",
            // ... other order details
            shirts = listOf(shirt1, shirt3),
            address =  "J.J. Strossmayera 149, Osijek",
            email = "lpoznic@net.hr",
            phoneNumber = "0957982637",
            status = "Pending",
            totalPrice = shirt1.price + shirt3.price

        )
        ordersCollection.add(newOrder) // Add the order to the collection
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    //NavigationController()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}