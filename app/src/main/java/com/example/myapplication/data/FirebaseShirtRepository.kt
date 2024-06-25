package com.example.myapplication.data

class FirestoreShirtRepository : ShirtRepository {
    private val db = Firebase.firestore.collection("shirts")

    override suspend fun addShirt(shirt: Shirt) {
        try {
            db.add(shirt)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firestore", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                }
        } catch (e: Exception) {
            throw RuntimeException("Error adding shirt to Firestore", e)
        }
    }

    override suspend fun getShirts(): List<Shirt> {
        return try {
            val snapshot = db.get().await()
            snapshot.toObjects(Shirt::class.java)
        } catch (e: Exception) {
            throw RuntimeException("Error fetching shirts from Firestore", e)
        }
    }

    override suspend fun updateShirt(shirtId: String, newShirt: Shirt) {
        try {
            db.document(shirtId).set(newShirt)
                .addOnSuccessListener {
                    Log.d("Firestore", "DocumentSnapshot successfully updated!")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error updating document", e)
                }
        } catch (e: Exception) {
            throw RuntimeException("Error updating shirt in Firestore", e)
        }
    }

    override suspend fun deleteShirt(shirtId: String) {
        try {
            db.document(shirtId).delete()
                .addOnSuccessListener {
                    Log.d("Firestore", "DocumentSnapshot successfully deleted!")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error deleting document", e)
                }
        } catch (e: Exception) {
            throw RuntimeException("Error deleting shirt from Firestore", e)
        }
    }
}
