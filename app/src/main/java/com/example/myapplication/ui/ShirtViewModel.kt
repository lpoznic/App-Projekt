package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ShirtRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import Shirt

class ShirtViewModel(private val shirtRepository: ShirtRepository) : ViewModel() {
    private val _shirts = MutableLiveData<List<Shirt>>()
    val shirts: LiveData<List<Shirt>> get() = _shirts

    init {
        loadShirts()
    }

    private fun loadShirts() {
        viewModelScope.launch {
            _shirts.value = shirtRepository.getShirts()
        }
    }

    fun addShirt(shirt: Shirt) {
        viewModelScope.launch {
            shirtRepository.addShirt(shirt)
            loadShirts()  // Reload shirts after adding
        }
    }
}