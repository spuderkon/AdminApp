package com.example.myapplication.ui.delivery

import adapters.ActiveDeliveryAdapter
import adapters.CartAdapter
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDeliveryBinding
import com.example.myapplication.toastNotification
import data.models.Cart
import data.models.Delivery
import retrofit.services.CartService
import retrofit.services.DeliveryService

class DeliveryFragment : Fragment() {

    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var deliveryService: DeliveryService
    private lateinit var viewModel: DeliveryViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartService: CartService
    private lateinit var adapter: CartAdapter
    private lateinit var _context: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this.requireActivity())[DeliveryViewModel::class.java]
        deliveryService = DeliveryService()
        _context = this.requireContext()
        adapter = CartAdapter.create(_context)
        cartService = CartService()

        binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        recyclerView = binding.rcViewCarts
        initialization()
        return binding.root
    }

    private fun initialization(){
        if(viewModel.delivery.isInitialized){

            viewModel.delivery.observe(viewLifecycleOwner, Observer {
                data ->

                refreshDeliveryData(data)

            })

        }
        else{

            //handle no data set

        }

        if(viewModel.carts.isInitialized){

            viewModel.carts.observe(viewLifecycleOwner, Observer {
                data ->

                refreshRecyclerView(data)

            })

        }
        else{

            refreshCarts()

        }
    }


    private fun refreshDeliveryData(item: Delivery){
        
        binding.apply {

            tvOrderId.text = item.orderId.toString()
            tvOrderAddress.text = item.order?.address?.address
            tvDateArrive.text = item.dateArrive
            tvCourier.text = item.user?.getFullName()
            tvTotalPrice.text = item.order?.totalPrice.toString()

        }
        
    }

    private fun refreshCarts(){
        cartService.getCartByOrder(viewModel.delivery.value?.id!!, _context).observe(viewLifecycleOwner, Observer {
            data ->

            if (data != null){

                refreshRecyclerView(data)

            }

        })
    }

    private fun refreshRecyclerView(items: List<Cart>){

        adapter.refreshCarts(items.toMutableList())

        recyclerView.layoutManager = LinearLayoutManager(_context)
        recyclerView.adapter = adapter


        binding.tvOrderWeight.text = getOrderWeight(items).toString()

    }

    private fun getOrderWeight(items: List<Cart>): Double{

        var weight: Double = 0.0

        items.forEach {

            if (it.product?.unit?.name == "Грамм"){
                weight += it.product?.unit?.measure!! * it.amount!!
            }

        }

        return weight / 1000
    }

}                                                                               