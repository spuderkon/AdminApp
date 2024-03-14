package com.example.myapplication.ui.orderdetails

import adapters.CartAdapter
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
import com.example.myapplication.databinding.FragmentOrderDetailsBinding
import com.example.myapplication.ui.orders.OrdersViewModel
import data.SharedViewModel
import data.models.Cart
import data.models.Order
import retrofit.services.CartService
import retrofit.services.OrderService
import retrofit.services.UserService
import services.DialogService

class OrderDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var viewModel: OrderDetailsViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var userService: UserService
    private lateinit var dialogService: DialogService
    private lateinit var orderService: OrderService
    private lateinit var cartService: CartService
    private lateinit var adapter: CartAdapter
    private lateinit var _context: Context
    private lateinit var order: Order

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        cartService = CartService()
        userService = UserService()
        orderService = OrderService()

        _context = this.requireContext()

        adapter = CartAdapter.create(_context)
        dialogService = DialogService.create(_context)

        ordersViewModel = ViewModelProvider(this.requireActivity())[OrdersViewModel::class.java]
        viewModel = ViewModelProvider(this.requireActivity())[OrderDetailsViewModel::class.java]
        sharedViewModel = ViewModelProvider(this.requireActivity())[SharedViewModel::class.java]

        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)

        initialization()

        binding.bGoToACA.setOnClickListener {

           userService.getCouriers(_context).observe(viewLifecycleOwner, Observer {
               data ->

               if(data != null){

                   sharedViewModel.setCouriers(data)

                   findNavController().navigate(R.id.action_nav_order_details_to_nav_assign_courier)

               }
           })

        }

        binding.bRejectOrderButton.setOnClickListener{

            val r = Runnable {

                orderService.rejectOrder(order.id!!, _context).observe(
                    this.viewLifecycleOwner, Observer {
                        value ->

                        if (value) {

                            ordersViewModel.removeOrderFromList(order)
                            parentFragmentManager.popBackStack()

                        }
                    })

            }
            dialogService.getDialog(
                "Отклонение", "Вы действительно хотите отклонить заказ №${order.id}", r
            ).show()
        }

        return binding.root
    }

    private fun initialization(){

        if(viewModel.order.isInitialized && viewModel.carts.isInitialized){

            order = viewModel.order.value!!
            refreshRecyclerView(viewModel.carts.value!!)

        }
        else{

            refreshCarts()

        }
    }

    private fun refreshCarts(){

        cartService.getCartByOrder(order.id!!, _context).observe(viewLifecycleOwner, Observer {
            data ->

            if (data != null) {

                viewModel.setCarts(data)
                refreshRecyclerView(data)

            }

        })

    }

    private fun refreshRecyclerView(items: List<Cart>){

        adapter.refreshCarts(items)

        binding.apply {

            orderId.text = order.id.toString()
            orderDate.text = order.orderDate
            rcViewOrders.layoutManager = LinearLayoutManager(_context)
            rcViewOrders.adapter = adapter

        }

    }
}