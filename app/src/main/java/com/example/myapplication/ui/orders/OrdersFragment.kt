package com.example.myapplication.ui.orders

import adapters.OrderAdapter
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
import com.example.myapplication.databinding.FragmentOrdersBinding
import com.example.myapplication.ui.orderdetails.OrderDetailsViewModel
import data.models.Order
import retrofit.services.CartService
import retrofit.services.OrderService


class OrdersFragment : Fragment() {

    private lateinit var orderDetailsViewModel: OrderDetailsViewModel
    private lateinit var binding: FragmentOrdersBinding
    private lateinit var orderService: OrderService
    private lateinit var viewModel: OrdersViewModel
    private lateinit var cartService: CartService
    private lateinit var _context: Context
    private lateinit var adapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        cartService = CartService()
        orderService = OrderService()
        adapter = OrderAdapter.create()
        _context = this.requireContext()

        viewModel = ViewModelProvider(this.requireActivity())[OrdersViewModel::class.java]
        orderDetailsViewModel = ViewModelProvider(this.requireActivity())[OrderDetailsViewModel::class.java]

        binding = FragmentOrdersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initialization()
    }

    private fun initialization(){

        if(viewModel.orders.isInitialized){

            refreshRecyclerView(viewModel.orders.value!!)

        }
        else{

            refreshOrders()

        }

    }


    private fun refreshOrders(){
        orderService.getNotInDelivery(this.requireContext()).observe(viewLifecycleOwner, Observer {
                data ->

            if(data != null){

                viewModel.setOrders(data)

                refreshRecyclerView(data)

            }

        })
    }

    private fun refreshRecyclerView(items: List<Order>){

        adapter.refreshItems(items)

        adapter.setOnClickListener(object : OrderAdapter.OnClickListener {

            override fun onClick(position: Int, model: Order) {

                cartService.getCartByOrder(model.id!!, _context).observe(viewLifecycleOwner, Observer {
                    data ->

                    if(data!=null){

                        orderDetailsViewModel.setCarts(data)
                        orderDetailsViewModel.setOrder(model)

                        findNavController().navigate(R.id.action_nav_orders_to_nav_order_details)

                    }

                })

            }

        })

        binding.apply {

            rcViewOrders.layoutManager = LinearLayoutManager(context)
            rcViewOrders.adapter = adapter

        }
    }
}