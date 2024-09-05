package com.example.myapplication.data

import android.util.Log
import androidx.compose.runtime.Composable
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentReference

import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class FirestoreShirtRepository : ShirtRepository {
    private val db = FirebaseFirestore.getInstance().collection("shirts") // Get Firestore instance

    override suspend fun addShirt(shirt: Shirt) {
        try {
            val documentReference = db.add(shirt).await() // Await the task to get the DocumentReference
            Log.d("Firestore", "DocumentSnapshot added with ID: ${documentReference.id}")
        } catch (e: FirebaseFirestoreException) { // Catch the specific exception type
            throw RuntimeException("Error adding shirt to Firestore", e)
        }
    }

    override suspend fun getShirts(): List<Shirt> {
        return try {
            val snapshot = db.get().await()
            snapshot.toObjects(Shirt::class.java)
        } catch (e: FirebaseFirestoreException) {
            throw RuntimeException("Error fetching shirts from Firestore", e)
        }
    }

    override suspend fun updateShirt(shirtId: String, newShirt: Shirt) {
        try {
            db.document(shirtId).set(newShirt).await() // Await the task for completion
            Log.d("Firestore", "DocumentSnapshot successfully updated!")
        } catch (e: FirebaseFirestoreException) {
            throw RuntimeException("Error updating shirt in Firestore", e)
        }
    }

    override suspend fun deleteShirt(shirtId: String) {
        try {
            db.document(shirtId).delete().await() // Await the task for completion
            Log.d("Firestore", "DocumentSnapshot successfully deleted!")
        } catch (e: FirebaseFirestoreException) {
            throw RuntimeException("Error deleting shirt from Firestore", e)
        }
    }
}
