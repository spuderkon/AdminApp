package data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.models.Delivery
import data.models.User

class SharedViewModel: ViewModel() {

    private val _me = MutableLiveData<User>()
    val me: LiveData<User>
        get() = _me

    fun setMe(value: User){
        _me.value = value
    }

    private val _courierDeliveries = MutableLiveData<MutableList<Delivery>>()
    val courierDeliveries: LiveData<MutableList<Delivery>>
        get() = _courierDeliveries

    fun setCourierDelivers(value: MutableList<Delivery>){
        _courierDeliveries.value = value
    }

    private val _employees = MutableLiveData<MutableList<User>>()
    val employees: LiveData<MutableList<User>>
        get() = _employees

    fun setEmployees(value: MutableList<User>){
        _employees.value = value
    }

    private val _couriers = MutableLiveData<List<User>>()
    val couriers: LiveData<List<User>>
        get() = _couriers

    fun setCouriers(value: List<User>){
        _couriers.value = value
    }

    fun addCourier(value: User){
        _employees.value?.add(value)
    }

    private val _selectedEmployee = MutableLiveData<User>()
    val selectedEmployee: LiveData<User>
        get() = _selectedEmployee

    fun setSelectedEmployee(value: User){
        _selectedEmployee.value = value
    }
}