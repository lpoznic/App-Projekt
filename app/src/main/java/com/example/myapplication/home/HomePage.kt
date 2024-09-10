package com.example.myapplication.home

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.Order
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import com.example.myapplication.MainActivity
import com.example.myapplication.getOrderDetailsPath
import com.example.myapplication.ui.OrderState
import com.example.myapplication.ui.OrderViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firestore.v1.StructuredQuery
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Locale


@Composable
fun HomeScreenContent(navController: NavController){

    Log.d("MyApp", "HomeScreenContent is on")
    val firestore = Firebase.firestore
    val ordersRef = firestore.collection("orders")

    val orders = remember { mutableStateOf<List<Order>>(emptyList()) }
    val scope = rememberCoroutineScope()


    suspend fun fetchOrders() {
        val snapshot = ordersRef.get().await()
        orders.value = snapshot.toObjects(Order::class.java)
        Log.d("MyApp", "Orders fetched: ${orders.value}")
    }

    LaunchedEffect(Unit) {
        fetchOrders()
    }

    Log.d("MyApp", "Orders.value: ${orders.value}")

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .background(Color.Black),
    ){
        ScreenTitle(
            title = stringResource(id = R.string.home_subtitle),
            subtitle = stringResource(id = R.string.home_subtitle)
        )
        SearchBar(
            iconResource = R.drawable.ic_search,
            labelText = "Search"
        )
        Row(
            modifier = Modifier.align(Alignment.Start).padding(20.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        val newOrder = Order()
                        firestore.collection("orders").add(newOrder)
                        Log.d("MyApp", "New order added with ID: ${newOrder.id}")

                        fetchOrders()
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6700)),
            ) {
                Text(text = "Nova narudžba")
            }
        }
        LaunchedEffect (orders){}
        OrderContainer(
            navController = navController,
            orders = orders.value)
    }


}



@Composable
fun ScreenTitle(
    title: String, subtitle: String
) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = subtitle, style = TextStyle(
                color = Color(0xFFFFFFFF),
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic
            ), modifier = Modifier.padding(horizontal = 17.dp)
        )
        Text(
            text = title,
            style = TextStyle(color = Color(0xFFFF6700),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    @DrawableRes iconResource: Int,
    labelText: String,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.DarkGray,
        disabledContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedLabelColor = Color.DarkGray,
    )
) {
    val searchInput = remember { mutableStateOf("") }
    TextField(
        value = searchInput.value,
        onValueChange = { searchInput.value = it },
        label = {
            Text(labelText)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = labelText,
                tint = Color.Gray,
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
            )
        },
        colors = colors,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun OrderContainer(
    navController: NavController,
    orders: List<Order>
) {
        if (orders.isNotEmpty()) {
            LazyColumn {
                item { Spacer(modifier = Modifier.height(10.dp)) }
                items(orders.sortedByDescending { it.dateCreated }) { order ->
                    OrderCard(order = order,
                        navController = navController)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        } else {
            Text("Nema narudžbi", color = Color.White)
        }

    Log.d("MyApp", "Orders passed to OrderContainer: ${orders}")
    }



@Composable
fun OrderCard(
    order: Order,
    navController: NavController
) {
    Log.d("MyApp", "OrderCard is appears")
    Card(
        modifier = Modifier
            .width(360.dp)
            .height(110.dp)
            .fillMaxSize()
            .clickable {
                navController.navigate(getOrderDetailsPath(order))
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131111)),
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.customerName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFFFF6700),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = order.dateCreated.toString(), // Or any other relevant date/time field
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFFFFFFFF),
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    ),
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Shirts: ${order.shirts.size}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Total: $${String.format(Locale.GERMAN,"%.2f", order.shirts.size*20.0)}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    ),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
