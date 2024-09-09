import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.example.myapplication.data.Order
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addOrder(order: Order, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val ordersCollection = db.collection("orders")
        ordersCollection.add(order)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    suspend fun updateOrder(order: Order) {
        try {
            val orderRef = db.collection("orders").document(order.id)
            orderRef.set(order).await()
            Log.d("MyApp", "Order successfully updated!")
        } catch (e: Exception) {
            Log.d("OrderUpdate", "Error updating order", e)
        }
    }

    suspend fun getOrders(): List<Order> {
        val ordersCollection = db.collection("orders")
        return try {
            val snapshot = ordersCollection.get().await()
            snapshot.toObjects(Order::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
