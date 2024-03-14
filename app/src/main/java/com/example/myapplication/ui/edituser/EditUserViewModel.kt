package com.example.myapplication.ui.edituser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.models.Role
import data.models.User

class EditUserViewModel: ViewModel() {

    private val _option = MutableLiveData<String>()
    val option: LiveData<String>
        get() = _option

    fun setOption(value: String){
        _option.value = value
    }

    private val _selectedEmployee = MutableLiveData<User>()
    val selectedEmployee: LiveData<User>
        get() = _selectedEmployee

    fun setSelectedEmployee(value: User){
        _selectedEmployee.value = value
    }

    private val _roles = MutableLiveData<List<Role>>()
    val roles: LiveData<List<Role>>
        get() = _roles

    fun setRoles(value: List<Role>){
        _roles.value = value
    }

}