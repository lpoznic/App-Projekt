package com.example.myapplication.data


interface ShirtRepository {
    suspend fun addShirt(shirt: Shirt)
    suspend fun getShirts(): List<Shirt>
    suspend fun updateShirt(shirtId: String, newShirt: Shirt)
    suspend fun deleteShirt(shirtId: String)
}