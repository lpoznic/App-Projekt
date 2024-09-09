package com.example.myapplication

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.home.HomeScreenContent

import com.example.myapplication.data.Design
import com.example.myapplication.data.Order
import com.example.myapplication.data.Shirt
import com.example.myapplication.details.OrderCreatorContent
import com.example.myapplication.details.ShirtOrderContent
import com.google.gson.Gson


sealed class Screens(val route: String) {
    object HomeScreen : Screens("home")
    object OrderCreator : Screens("orderCreator")
    object ShirtOrder : Screens("shirtOrder")
}

fun getOrderDetailsPath(orderId: String): String {
    return "${Screens.OrderCreator.route}?orderId=$orderId"
}

fun getOrderDetailsPath(order: Order): String {
    val gson = Gson()
    val orderJson = gson.toJson(order)
    return "${Screens.OrderCreator.route}?orderJson=$orderJson"
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    Log.d("MyApp", "Navigation is reached")
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(Screens.HomeScreen.route) {
            HomeScreenContent(
                navController = navController
            )
        }

        composable(
            route = Screens.OrderCreator.route,
            arguments = listOf(
                navArgument("order") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val orderJson = backStackEntry.arguments?.getString("order")
            val order = Gson().fromJson(orderJson, Order::class.java)
            OrderCreatorContent(navController, order)
        }

        composable(
            route = Screens.ShirtOrder.route,
            arguments = listOf(
                navArgument("shirts") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val shirtsJson = backStackEntry.arguments?.getString("shirts")
            val shirts = Gson().fromJson(shirtsJson, Array<Shirt>::class.java).toList()
            ShirtOrderContent(navController, shirts, fetchAvailableDesigns())
        }
    }
}

fun fetchAvailableDesigns(): List<Design> {
    return listOf(Design(name = "Linija", imageUrl = "https://lpoznic.github.io/Web-projekt/image/shirt_one.png"),
        Design(name = "Ascii", imageUrl = "https://lpoznic.github.io/Web-projekt/image/shirt_two.png"),
        Design(name = "Ćošak", imageUrl = "https://lpoznic.github.io/Web-projekt/image/shirt_three.png"),
        Design(name = "Imenica", imageUrl = "https://lpoznic.github.io/Web-projekt/image/shirt_four.png"),
        Design(name = "Logo", imageUrl = "https://lpoznic.github.io/Web-projekt/image/shirt_five.png"))
}
