package com.example.myapplication.ui.couriers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.models.Delivery
import data.models.User

class CouriersViewModel : ViewModel() {

    /*private val _courierDelivers = MutableLiveData<List<Delivery>>()
    val courierDelivers: LiveData<List<Delivery>>
        get() = _courierDelivers

    fun setCourierDelivers(value: List<Delivery>){
        _courierDelivers.value = value
    }*/

    private val _couriers = MutableLiveData<List<User>>()
    val couriers: LiveData<List<User>>
        get() = _couriers

    fun setCouriers(value: List<User>){
        _couriers.value = value
    }
}