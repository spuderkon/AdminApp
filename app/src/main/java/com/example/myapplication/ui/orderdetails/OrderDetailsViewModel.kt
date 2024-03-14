package com.example.myapplication.ui.orderdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.models.Cart
import data.models.Order

class OrderDetailsViewModel : ViewModel() {

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order>
        get() = _order

    fun setOrder(value: Order){
        _order.value = value
    }

    private val _carts = MutableLiveData<List<Cart>>()
    val carts: LiveData<List<Cart>>
        get() = _carts

    fun setCarts(value: List<Cart>){
        _carts.value = value
    }

}