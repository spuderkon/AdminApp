package com.example.myapplication.ui.couriers

import adapters.CourierAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCouriersBinding
import com.example.myapplication.ui.edituser.EditUserViewModel
import data.SharedViewModel
import data.models.User
import retrofit.services.DeliveryService
import retrofit.services.UserService

class CouriersFragment : Fragment() {

    private lateinit var editUserViewModel: EditUserViewModel
    private lateinit var viewModel: CouriersViewModel
    private lateinit var binding: FragmentCouriersBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var deliveryService: DeliveryService
    private lateinit var userService: UserService
    private lateinit var adapter: CourierAdapter
    private lateinit var _context: Context


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        userService = UserService()
        _context = this.requireContext()
        adapter = CourierAdapter.create()
        deliveryService = DeliveryService()
        viewModel = ViewModelProvider(this.requireActivity())[CouriersViewModel::class.java]
        editUserViewModel = ViewModelProvider(this.requireActivity())[EditUserViewModel::class.java]
        sharedViewModel = ViewModelProvider(this.requireActivity())[SharedViewModel::class.java]
        binding = FragmentCouriersBinding.inflate(inflater, container, false)

        binding.bAddCourier.setOnClickListener {

            editUserViewModel.setOption("create")
            findNavController().navigate(R.id.action_nav_couriers_to_nav_edit_user)

        }

        initialization()

        return binding.root
    }


    private fun initialization(){

        if (viewModel.couriers.isInitialized){

            refreshRecyclerView(viewModel.couriers.value!!)

        }
        else{
            refreshCouriers()
        }

    }

    private fun refreshCouriers(){
        userService.getCouriers(_context).observe(viewLifecycleOwner, Observer {
                data ->

            if(data != null){

                viewModel.setCouriers(data)
                refreshRecyclerView(data)

            }

        })
    }

    private fun refreshRecyclerView(items: List<User>){

        adapter.refreshCouriers(items.toMutableList())

        adapter.setOnClickListener(object: CourierAdapter.OnClickListener{
            override fun onClick(position: Int, model: User) {

                deliveryService.getCourierActive(model.id!!, _context).observe(viewLifecycleOwner, Observer {
                        data ->

                    if(data!= null){
                        sharedViewModel.setCourierDelivers(data.toMutableList())
                        findNavController().navigate(R.id.action_nav_couriers_to_nav_courierDelivers)
                    }

                })

            }
        })
        binding.apply {

            rcViewCouriers.layoutManager = LinearLayoutManager(_context)
            rcViewCouriers.adapter = adapter

        }
    }
}