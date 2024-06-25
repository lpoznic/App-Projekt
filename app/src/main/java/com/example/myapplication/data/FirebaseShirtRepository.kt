package com.example.myapplication.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreShirtRepository : ShirtRepository {
    private val db = Firebase.firestore.collection("shirts")

    override suspend fun addShirt(shirt: Shirt) {
            db.add(shirt)
    }

    override suspend fun getShirts(): List<Shirt> {
            val snapshot = db.get().await()
            return snapshot.toObjects(Shirt::class.java);
    }

    override suspend fun updateShirt(shirtId: String, newShirt: Shirt) {
            val res = db.document(shirtId).set(newShirt)
    }

    override suspend fun deleteShirt(shirtId: String) {
            db.document(shirtId).delete()
    }
}
