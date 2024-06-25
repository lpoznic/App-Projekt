package com.example.myapplication.details

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Shirt

@Composable
@Preview(showBackground = true)

fun OrderCreatorContent(){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ){
        OrderTitle(
        title = stringResource(id = R.string.order_title),
        subtitle = stringResource(id = R.string.order_subtitle)
    )
        NameBar(
            labelText = "Ime i prezime"
        )
        AddressBar(
            labelText = "Adresa"
        )
        EmailBar(
            labelText = "E-mail"
        )
        PhoneBar(
            labelText = "Kontakt"
        )
        Spacer(
            modifier = Modifier.padding(vertical = 10.dp)
        )
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
            Spacer(modifier = Modifier.width(70.dp))
            Button(onClick = { /*TODO*/ },
                colors =  ButtonDefaults.buttonColors(Color.DarkGray),
                ){
                Text(text = "Uredi listu")

            }
        }
        ShirtContainer(
            order.shirt
        )
    }
}

@Composable
fun ShirtContainer(shirts: List<Shirt>
) {
    LazyColumn(){
        item{ Spacer(modifier = Modifier.height(10.dp),)}
        shirts.forEach{shirt ->
            item{
                ShirtCard(
                    shirtAmount = shirts.size,
                    shirtName = shirt.name,
                    shirtSize = shirt.shirtSize,
                    shirtImage = shirt.image
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@Composable
fun ShirtCard(
    shirtAmount: Int,
    shirtName: String,
    shirtSize: String,
    @DrawableRes shirtImage: Int
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
            Text(  //ime
                text = shirtName.uppercase(), style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    //fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ), modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(  //veličina
                text = shirtSize.uppercase(), style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    //fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ), modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = shirtAmount.toString(), style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.LightGray,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(painter = painterResource(id = shirtImage),
                contentDescription = shirtName,
                modifier = Modifier.width(100.dp)
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
                color = Color(0xFFFFFFFFF),
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic
            ), modifier = Modifier.padding(horizontal = 17.dp)
        )
        Text(
            text = subtitle,
            style = TextStyle(color = Color(0xFFFF6700),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameBar(labelText: String,
            colors: TextFieldColors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )) {
    val nameInput = remember { mutableStateOf("") }
    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 3.dp),
        text = labelText,
        color = Color.White,
        textAlign = TextAlign.Start,
        fontSize = 22.sp)
    TextField(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = nameInput.value,
        onValueChange = { nameInput.value = it },
        label = {
            Text(labelText)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressBar(labelText: String,
               colors: TextFieldColors = TextFieldDefaults.textFieldColors(
                   containerColor = Color.Transparent,
                   unfocusedLabelColor = Color.Black,
                   focusedIndicatorColor = Color.Transparent,
                   unfocusedIndicatorColor = Color.Transparent,
                   disabledIndicatorColor = Color.Transparent
               )) {
    val addressInput = remember { mutableStateOf("") }
    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 3.dp),
        text = labelText,
        color = Color.White,
        textAlign = TextAlign.Start,
        fontSize = 22.sp)
    TextField(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = addressInput.value,
        onValueChange = { addressInput.value = it },
        label = {
            Text(labelText)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailBar(labelText: String,
               colors: TextFieldColors = TextFieldDefaults.textFieldColors(
                   containerColor = Color.Transparent,
                   unfocusedLabelColor = Color.Black,
                   focusedIndicatorColor = Color.Transparent,
                   unfocusedIndicatorColor = Color.Transparent,
                   disabledIndicatorColor = Color.Transparent
               )) {
    val emailInput = remember { mutableStateOf("") }
    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 3.dp),
        text = labelText,
        color = Color.White,
        textAlign = TextAlign.Start,
        fontSize = 22.sp)
    TextField(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = emailInput.value,
        onValueChange = { emailInput.value = it },
        label = {
            Text(labelText)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneBar(labelText: String,
             colors: TextFieldColors = TextFieldDefaults.textFieldColors(
                 containerColor = Color.Transparent,
                 unfocusedLabelColor = Color.Black,
                 focusedIndicatorColor = Color.Transparent,
                 unfocusedIndicatorColor = Color.Transparent,
                 disabledIndicatorColor = Color.Transparent
             )) {
    val contactInput = remember { mutableStateOf("") }
    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 3.dp),
        text = labelText,
        color = Color.White,
        textAlign = TextAlign.Start,
        fontSize = 22.sp)
    TextField(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = contactInput.value,
        onValueChange = { contactInput.value = it },
        label = {
            Text(labelText)
        },
    )
}


fun OrderList(){

}

fun ShirtList(){


}
