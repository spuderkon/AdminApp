package com.example.myapplication.ui.delivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.models.Cart
import data.models.Delivery

class DeliveryViewModel : ViewModel() {


    private val _delivery = MutableLiveData<Delivery>()
    val delivery: LiveData<Delivery>
        get() = _delivery

    fun setDelivery(value: Delivery){
        _delivery.value = value
    }

    private val _carts = MutableLiveData<List<Cart>>()
    val carts: LiveData<List<Cart>>
        get() = _carts

    fun setCarts(value: List<Cart>){
        _carts.value = value
    }
}