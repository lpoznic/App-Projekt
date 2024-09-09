package com.example.myapplication.details

import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.data.Design
import com.example.myapplication.data.Shirt
import com.google.gson.Gson




var shirtAmount = 0

@Composable
fun ShirtOrderContent(navController: NavController, shirts:List<Shirt>, designs: List<Design>) {
    var modifiedShirts = remember { mutableStateListOf(shirts) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
    ) {
        ShirtOrderTitle(
            title = stringResource(id = R.string.shirts_title),
            subtitle = stringResource(id = R.string.shirts_subtitle)
        )
        ShirtOrderContainer(designs = designs)
        SizeContainer()
        AmountContainer()
        ShirtList(shirts)
    }

    Button(onClick = {
        val shirtsJson = Gson().toJson(modifiedShirts)
        navController.navigate("orderDetailsScreen/$shirtsJson")
    }) {
        Text(text = "Spremi i izađi")
    }


}




@Composable
fun AmountContainer() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { shirtAmount++ },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(LightGray)
        ) {
            // Inner content including an icon and a text label
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus),
                contentDescription = "Favorite",
                modifier = Modifier.size(20.dp),
                tint = Black
            )
        }
        Box(
            modifier = Modifier.width(100.dp),
            contentAlignment = Alignment.Center){
            Text(text = shirtAmount.toString(),
                style = TextStyle(
                    color = Color(0xFFFF6700),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center))
        }
        Button(
            onClick = {
                if(shirtAmount >0){
                    shirtAmount--
            }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(LightGray)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_minus),
                contentDescription = "Favorite",
                modifier = Modifier.size(20.dp),
                tint = Black
            )
        }
    }
}



@Composable
fun SizeContainer() {
    val currentActiveButton = remember { mutableStateOf(0) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(44.dp)
    ) {
        TabButton(
            text = "S",
            isActive = currentActiveButton.value == 0,
            modifier = Modifier.weight(1f),
            onClick = {currentActiveButton.value = 0},
        ) 
        TabButton(
            text = "M",
            isActive = currentActiveButton.value == 1,
            modifier = Modifier.weight(1f),
            onClick = {currentActiveButton.value = 1}
        ) 
        TabButton(
            text = "L",
            isActive = currentActiveButton.value == 2,
            modifier = Modifier.weight(1f),
            onClick = {currentActiveButton.value = 2}
        )
        TabButton(
            text = "XL",
            isActive = currentActiveButton.value == 3,
            modifier = Modifier.weight(1f),
            onClick = {currentActiveButton.value = 3}
        )
    }
}

@Composable fun TabButton(
    text: String, isActive: Boolean, modifier: Modifier, onClick: () -> Unit
) {
    Button(shape = RoundedCornerShape(7.dp),
        elevation = null,
        colors = if (isActive) ButtonDefaults.buttonColors(
            contentColor = White, containerColor = Color(0xFFFF6A2F)
        ) else ButtonDefaults.buttonColors(contentColor = Black, containerColor = LightGray),
        modifier = modifier.padding(horizontal = 3.dp),
        onClick = { onClick() }) {
        Text(text)
    }
}

@Composable
fun ShirtOrderTitle(
    title: String,
    subtitle: String) {
    Text(
        text = title,
        style = TextStyle(
            color = White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic
        ), modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 12.dp)
    )
    Text(
        text = subtitle,
        style = TextStyle(
            color = Color(0xFFFF6700),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun ShirtOrderContainer(designs: List<Design>
    ) {
        val currentActiveButton = remember { mutableIntStateOf(0) }
        LazyColumn(){
            item{ Spacer(modifier = Modifier.height(10.dp))}
            if (designs.isEmpty()) {
                item {
                    Text("No shirts available", modifier = Modifier.padding(16.dp))
                }
            } else {
            items(designs.size){index ->
                    ShirtButton(
                        shirtName = designs[index].name,
                        isActive = currentActiveButton.value == index,
                        imageUrl = designs[index].imageUrl
                    ) {}
                    Spacer(modifier = Modifier.height(15.dp))
                }
                currentActiveButton.value = 0
            }
        }
}

@Composable
fun ShirtButton(shirtName: String,
                isActive: Boolean,
                imageUrl: String,
                onClick: () -> Unit) {
    Button(shape = RoundedCornerShape(7.dp),
        elevation = null,
        colors = if (isActive) ButtonDefaults.buttonColors(
            contentColor = White, containerColor = Black
        ) else ButtonDefaults.buttonColors(contentColor = Black, containerColor = Color(0xFFFF6A2F)),
        modifier = Modifier
            .width(360.dp)
            .height(100.dp)
            .fillMaxHeight(),
        border = BorderStroke(2.dp, Color.Gray),
        onClick = { onClick() }) {
        Row(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            Text(
                text = shirtName.uppercase(), style = MaterialTheme.typography.bodyLarge.copy(
                    //color = Color.White,
                    //fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ), modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "uhhh",
                modifier = Modifier.size(100.dp),
                alignment = Alignment.CenterEnd
            )

        }
    }
}

@Composable
fun ShirtList(shirts: List<Shirt>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(shirts) { shirt ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(shirt.design.name, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                Text(shirt.size)
                Spacer(modifier = Modifier.width(8.dp))
                Text(String.format("$%.2f", shirt.price))
            }
        }
    }
}

@Composable
fun ShirtRow(shirt: Shirt) {

}

@Composable
fun AddShirtsButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(50.dp),
        horizontalArrangement = Arrangement.Center
    ){
        Button(
            onClick = { },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(LightGray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        ){ Text(text = "Dodaj majice",
            color = Color.Black,
            fontSize = 20.sp) }
    }
}