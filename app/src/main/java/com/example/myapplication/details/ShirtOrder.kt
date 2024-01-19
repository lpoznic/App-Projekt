package com.example.myapplication.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R



@Composable
@Preview(showBackground = true)
fun ShirtOrderContent() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        ShirtOrderTitle(
            title = stringResource(id = R.string.shirts_title),
            subtitle = stringResource(id = R.string.shirts_subtitle)
        )
    }
}

@Composable
fun ShirtOrderTitle(
    title: String,
    subtitle: String){
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