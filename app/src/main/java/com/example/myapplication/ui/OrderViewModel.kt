package com.example.myapplication.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Order
import com.example.myapplication.data.Shirt;
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow

sealed class OrderState{
    object Loading : OrderState()
    data class Success(val orders: List<Order>) : OrderState()
    //data class Error(val message: String) : OrderState()
}


class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    val state = MutableStateFlow<OrderState>(OrderState.Loading)

    val name = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val selectedShirts = MutableLiveData<List<Shirt>>(emptyList())

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            _orders.value = orderRepository.getOrders()
        }
    }

    fun addOrder(order: Order) {
        viewModelScope.launch {
            orderRepository.addOrder(order)
            loadOrders()  // Reload orders after adding
        }
    }

    fun updateOrder(order: Order) {
        viewModelScope.launch {
            orderRepository.updateOrder(order)
            loadOrders()  // Reload orders after updating
        }
    }

    fun deleteOrder(orderId: String) {
        viewModelScope.launch {
            orderRepository.deleteOrder(orderId)
            loadOrders()  // Reload orders after deleting
        }
    }

    fun setOrderDetails(name: String, address: String, email: String, phone: String, shirts: List<Shirt>) {
        this.name.value = name
        this.address.value = address
        this.email.value = email
        this.phone.value = phone
        this.selectedShirts.value = shirts
    }

     fun getOrders(){
         viewModelScope.launch {
             val result = orderRepository.getOrders()
             state.value = OrderState.Success(result)
         }
     }
}
