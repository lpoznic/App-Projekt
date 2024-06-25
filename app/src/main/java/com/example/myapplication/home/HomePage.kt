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

@Composable
@Preview(showBackground = true)
fun HomeScreenContent(){
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
            orders
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
        orders.forEach{order ->
            item{
                OrderCard(
                    customer = order.customerName,
                    shirtAmount = order.shirt.size,
                    state = order.ordered,
                    date = order.orderDate
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}


@Composable
fun OrderCard(
    customer: String,
    shirtAmount: Int,
    state: Boolean,
    date: String
    //navController: NavController,
    //orderId : String
) {
    Card(
        modifier = Modifier
            .clickable {
                //navController.navigate(getOrderDetailsPath(orderId))
            }
            .width(360.dp)
            .height(110.dp)
            .fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131111))
    ){
        //Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
        Text(  //ime
            text = customer, style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFFFFFF6700),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ), modifier = Modifier.padding(horizontal = 16.dp)
        )
            Spacer(modifier = Modifier.width(10.dp))
            Text( //datum
                text = date.toString(), style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFFFFFFFFFF),
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp
                ), modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = shirtAmount.toString(),
                fontSize = 40.sp,
                overflow = TextOverflow.Visible,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White, fontWeight = FontWeight.Medium
                ), modifier = Modifier.padding(horizontal = 22.dp)
            )
            Spacer(modifier = Modifier.width(240.dp))
            Text(
                if(state){"+"}else{"-"},
                fontSize = 50.sp,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFFFC107), fontWeight = FontWeight.ExtraBold
                ),  modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
            )

        }


    }

}

