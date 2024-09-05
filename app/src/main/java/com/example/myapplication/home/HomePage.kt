package com.example.myapplication.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Order
import com.example.myapplication.data.Shirt
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.firebase.database.database


@Composable
@Preview(showBackground = true)
fun HomeScreenContent(){
    val orders = remember { mutableStateListOf<Order>() }

    // Fetch orders fromFirebase when the composable enters the composition
    LaunchedEffect(Unit) {
        val database = com.google.firebase.Firebase.database
        val ordersRef = database.getReference("orders")

        ordersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orders.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(Order::class.java)
                    order?.let { orders.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {// Handle errors
            }
        })
    }

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
        OrderContainer(
            orders = orders
        )

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
                color = Color(0xFFFFFFFFF),
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
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        unfocusedLabelColor = Color.DarkGray,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
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
    orders: List<Order>
){
    LazyColumn(){
        item{ Spacer(modifier = Modifier.height(10.dp),)}
        items(orders.size) { order ->
            OrderCard(order = orders.get(order))
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}


@Composable
fun OrderCard(
    order: Order
) {
    Card(
        modifier = Modifier
            .width(360.dp)
            .height(110.dp)
            .fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131111))
    ){
        Column(
            modifier =Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.customerName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFFFFFF6700),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = order.id, // Or any other relevant date/time field
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFFFFFFFFFF),
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
                    text = "Total: $${String.format("%.2f", order.totalPrice)}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    ),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

