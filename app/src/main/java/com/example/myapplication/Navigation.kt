package com.example.myapplication
/*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

object Routes {

    const val SCREEN_ALL_ORDERS = "orderList"
    const val SCREEN_ORDER_DETAILS = "orderDetails/{orderId}"

    fun getOrderDetailsPath(orderId: String):String{
        return "orderDetails/$orderId"
    }

    @Composable
    fun NavigationController(){
        val navController = rememberNavController()
        NavHost(
            navcontroller = navController,
            startDestination = Routes.SCREEN_ALL_ORDERS
        ){
            composable(Routes.SCREEN_ALL_ORDERS){
                HomeScreen(navController)
            }
            composable(
                Routes.SCREEN_ORDER_DETAILS,
                arguments = listOf(
                    navArgument("orderId"){
                        type = NavType.StringType
                    }
                )
            ){ backStackEntry ->
                backStackEntry.arguments?.getString("orderId")?.let { idFromArguments ->
                    DetailsPage(
                        navController = navController,
                        orderId = idFromArguments
                    )
                }
            }
        }
    }
}*/