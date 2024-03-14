package com.example.myapplication.ui.courierdeliveries

import adapters.DeliveryAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentCourierDeliveriesBinding
import data.SharedViewModel
import data.models.Delivery
import data.models.User
import retrofit.services.DeliveryService

class CourierDeliveriesFragment : Fragment() {

    private lateinit var binding: FragmentCourierDeliveriesBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var _context: Context
    private lateinit var adapter: DeliveryAdapter
    private var deliveryService: DeliveryService = DeliveryService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _context = this.requireContext()
        deliveryService = DeliveryService()
        adapter = DeliveryAdapter.create()
        sharedViewModel = ViewModelProvider(this.requireActivity())[SharedViewModel::class.java]
        binding = FragmentCourierDeliveriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        refreshDelivery()
    }

    private fun refreshDelivery(){
        sharedViewModel.courierDeliveries.observe(viewLifecycleOwner, Observer{
            data ->

            if(!data.isNullOrEmpty()){

                refreshRecyclerView(data)

            }

        })

    }

    private fun refreshRecyclerView(items: MutableList<Delivery>){
        adapter.refreshDelivers(items)
        adapter.setOnClickListener(object: DeliveryAdapter.OnClickListener{
            override fun onClick(position: Int, model: User) {

            }
        })
        binding.apply {
            rcViewDelivers.layoutManager = LinearLayoutManager(_context)
            rcViewDelivers.adapter = adapter
        }
    }
}