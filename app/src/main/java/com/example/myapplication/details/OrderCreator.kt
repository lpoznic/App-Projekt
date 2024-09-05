package com.example.myapplication.details

// ... other imports
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import com.example.myapplication.R
import com.example.myapplication.data.FirestoreOrderRepository
import com.example.myapplication.data.Order
import com.example.myapplication.data.OrderRepository
import com.example.myapplication.data.Shirt

// ... (Other composables: OrderTitle, NameBar, AddressBar, EmailBar, PhoneBar)

@Composable
@Preview(showBackground = true)
fun OrderCreatorContent() {
    var customerName by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    val orders = remember { mutableStateListOf<Order>() }
    val shirts = remember { mutableStateListOf<Shirt>() }

    // Fetch shirts from Firestore
    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        try {
            val querySnapshot = db.collection("shirts").get().await()
            shirts.addAll(querySnapshot.toObjects(Shirt::class.java))
        } catch (e: Exception) {
            // Handle error
        }
    }

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
        NameBar(
            labelText = "Ime i prezime",
            onValueChange = { customerName = it }
        )
        AddressBar(
            labelText = "Adresa",
            onValueChange = { address = it }
        )
        EmailBar(
            labelText = "E-mail",
            onValueChange = { email = it }
        )
        PhoneBar(
            labelText = "Kontakt",
            onValueChange = { phoneNumber = it }
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
            Spacer(modifier = Modifier.width(70.dp))
            Button(
                onClick = { /*TODO: Navigate to edit list screen*/ },
                colors = ButtonDefaults.buttonColors(Color.DarkGray),
            ) {
                Text(text = "Uredi listu")
            }
        }


        ShirtContainer(shirts)

        // Button to create order (Add logic to create order in Firestore)

    }
}

@Composable
fun ShirtContainer(
    shirts: List<Shirt>
) {
    LazyColumn() {
        item { Spacer(modifier = Modifier.height(10.dp)) }
        items(shirts) { shirt ->
            ShirtCard(shirt = shirt)
            Spacer(modifier = Modifier.height(15.dp))
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
                text = shirt.name.uppercase(),
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
            AsyncImage(
                model = shirt.imageUrl,
                contentDescription = shirt.name,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Composable
fun AsyncImage(model: String, contentDescription: String, modifier: Any) {

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
                style = TextStyle(
                    color = Color(0xFFFF6700),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp)
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NameBar(
        labelText: String,
        onValueChange: (String) -> Unit,
        colors: TextFieldColors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedLabelColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    ) {
        val nameInput = remember { mutableStateOf("") }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            text = labelText,
            color = Color.White,
            textAlign = TextAlign.Start,
            fontSize = 22.sp
        )
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
    fun AddressBar(
        labelText: String,
        onValueChange: (String) -> Unit,
        colors: TextFieldColors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedLabelColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    ) {
        val addressInput = remember { mutableStateOf("") }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            text = labelText,
            color = Color.White,
            textAlign = TextAlign.Start,
            fontSize = 22.sp
        )
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
    fun EmailBar(
        labelText: String,
        onValueChange: (String) -> Unit,
        colors: TextFieldColors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedLabelColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    ) {
        val emailInput = remember { mutableStateOf("") }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            text = labelText,
            color = Color.White,
            textAlign = TextAlign.Start,
            fontSize = 22.sp
        )
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
    fun PhoneBar(
        labelText: String,
        onValueChange: (String) -> Unit,
        colors: TextFieldColors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedLabelColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    ) {
        val contactInput = remember { mutableStateOf("") }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 3.dp),
            text = labelText,
            color = Color.White,
            textAlign = TextAlign.Start,
            fontSize = 22.sp
        )
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