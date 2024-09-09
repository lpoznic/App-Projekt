package com.example.myapplication.details

// ... other imports
import FirestoreRepository
import androidx.navigation.NavController
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.data.Order
import com.example.myapplication.data.Shirt
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.myapplication.Screens
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
//@Preview(showBackground = true)
fun OrderCreatorContent(navController: NavController, order: Order?) {

    val repository = remember { FirestoreRepository() }
    val coroutineScope = rememberCoroutineScope()

    var customerName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }



        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
        ) {
            OrderTitle(
                title = stringResource(id = R.string.order_title),
                subtitle = stringResource(id = R.string.order_subtitle)
            )
            Button(
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6700)),
                modifier = Modifier
                    .width(170.dp)
                    .height(55.dp),
                onClick = {
                    val updatedOrder = order?.copy(
                        customerName = customerName,
                        address = address,
                        email = email,
                        phoneNumber = phoneNumber,
                        totalPrice = 0.0,

                    )
                    // Update the order in the database
                    updatedOrder?.let {
                        coroutineScope.launch {
                            repository.updateOrder(it)
                            navController.popBackStack()
                        }
                    }

                    navController.popBackStack(Screens.HomeScreen.route, false) // Navigate to HomePage after saving
                }

            ) {
                Text(
                    text = "Spremi",
                    fontSize = 25.sp
                )
            }
            TextField(
                value = customerName,
                onValueChange = { customerName = it },
                label = { Text("Ime i prezime") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            TextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Adresa") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Kontakt") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 23.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lista majica",
                    style = TextStyle(
                        color = Color(0xFFFFFFFF),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Spacer(modifier = Modifier.width(40.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(Color.DarkGray),
                    onClick = {
                        val shirtsJson = Gson().toJson(order?.shirts)
                        // Navigate with the JSON string as a query parameter
                        navController.navigate("${Screens.ShirtOrder.route}?shirts=$shirtsJson")
                              },
                ) {
                    Text(text = "Uredi")
                }
                Spacer(modifier = Modifier.width(20.dp))
            }
            if (order != null) {
                ShirtContainer(order.shirts)
            }
        }

    }

@Composable
fun rememberOrderState(): MutableState<Order?> {
    return remember { mutableStateOf(null) }

}


    @Composable
    fun ShirtContainer(
        shirts: List<Shirt>
    ) {
        if (shirts.isEmpty()) {
            Text("Nema majica", color = Color.White)
        } else {
            LazyColumn() {
                item { Spacer(modifier = Modifier.height(10.dp)) }
                items(shirts) { shirt ->
                    ShirtCard(shirt = shirt)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }

    @Composable
    fun ShirtCard(
        shirt: Shirt
    ) {
        OutlinedCard(
            modifier = Modifier
                .width(360.dp)
                .height(100.dp)
                .fillMaxHeight(),
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            border = BorderStroke(2.dp, Color.Gray)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = shirt.design.name.uppercase(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = shirt.size.uppercase(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                // ... (Other Text composables for price and quantity)
                Image(
                    painter = rememberAsyncImagePainter(shirt.design.imageUrl),
                    contentDescription = shirt.design.name,
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }


    @Composable
    fun OrderTitle(
        title: String, subtitle: String
    ) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = title, style = TextStyle(
                    color = Color(0xFFFFFFFF),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic
                ), modifier = Modifier.padding(horizontal = 17.dp)
            )
            Text(
                text = subtitle,
                style = TextStyle(
                    color = Color(0xFFFF6700),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp)
            )
        }
    }



