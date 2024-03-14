package com.example.myapplication.ui.activedeliveries

import adapters.ActiveDeliveryAdapter
import adapters.DeliveryAdapter
import adapters.UserAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentActiveDeliveriesBinding
import com.example.myapplication.ui.delivery.DeliveryViewModel
import data.models.Delivery
import data.models.User
import retrofit.services.CartService
import retrofit.services.DeliveryService

class ActiveDeliveriesFragment : Fragment() {

    private lateinit var binding: FragmentActiveDeliveriesBinding
    private lateinit var deliveryViewModel: DeliveryViewModel
    private lateinit var viewModel: ActiveDeliveriesViewModel
    private lateinit var deliveryService: DeliveryService
    private lateinit var adapter: ActiveDeliveryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartService: CartService
    private lateinit var _context: Context


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        cartService = CartService()
        _context = this.requireContext()
        deliveryService = DeliveryService()
        adapter = ActiveDeliveryAdapter.create()
        viewModel = ViewModelProvider(this.requireActivity())[ActiveDeliveriesViewModel::class.java]
        deliveryViewModel = ViewModelProvider(this.requireActivity())[DeliveryViewModel::class.java]
        binding = FragmentActiveDeliveriesBinding.inflate(inflater, container, false)
        recyclerView = binding.rcViewActiveDelivers


       /* val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        initialization()
        return binding.root
    }

    private fun initialization(){
        if(viewModel.activeDeliveries.isInitialized){

            viewModel.activeDeliveries.observe(viewLifecycleOwner, Observer {
                data ->

                refreshRecyclerView(data)

            })

        }
        else{

            refreshDeliveries()

        }
    }

    private fun refreshDeliveries(){
        deliveryService.getActive(_context).observe(viewLifecycleOwner, Observer {
            data ->

            if(data!=null){

                viewModel.setActiveDelivers(data.toMutableList())
                refreshRecyclerView(data)

            }
        })
    }

    private fun refreshRecyclerView(items: List<Delivery>){

        adapter.refreshDelivers(items.toMutableList())
        adapter.setOnClickListener(object: ActiveDeliveryAdapter.OnClickListener{

            override fun onClick(position: Int, model: Delivery) {

                cartService.getCartByOrder(model.orderId!!,_context).observe(viewLifecycleOwner, Observer {
                    data ->

                    if (data!=null){

                        deliveryViewModel.setDelivery(model)
                        deliveryViewModel.setCarts(data)
                        findNavController().navigate(R.id.action_nav_active_deliveries_to_nav_delivery)

                    }
                })
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(_context)
        recyclerView.adapter = adapter

    }
}