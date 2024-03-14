package com.example.myapplication.ui.activedeliveries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.models.Delivery

class ActiveDeliveriesViewModel : ViewModel() {

    private val _activeDeliveries = MutableLiveData<MutableList<Delivery>>()
    val activeDeliveries: LiveData<MutableList<Delivery>>
        get() = _activeDeliveries

    fun setActiveDelivers(value: MutableList<Delivery>){
        _activeDeliveries.value = value
    }

}