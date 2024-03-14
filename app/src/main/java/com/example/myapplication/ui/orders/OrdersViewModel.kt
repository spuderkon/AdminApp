package com.example.myapplication.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.models.Order

class OrdersViewModel : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    fun setOrders(value: List<Order>){
        _orders.value = value
    }

    fun removeOrderFromList(value : Order){
        val oldList = _orders.value.orEmpty()
        _orders.value = ArrayList(oldList.filterIndexed { index, _ -> index != oldList.indexOf(value) })
    }

}