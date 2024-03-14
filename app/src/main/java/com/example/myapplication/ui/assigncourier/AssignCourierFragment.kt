package com.example.myapplication.ui.assigncourier

import adapters.CourierAdapter
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentAssignCourierBinding
import com.example.myapplication.toastNotification
import com.example.myapplication.ui.orderdetails.OrderDetailsViewModel
import com.example.myapplication.ui.orders.OrdersViewModel
import data.SharedViewModel
import data.models.User
import data.modelsDTO.PostDelivery
import retrofit.services.DeliveryService
import retrofit.services.UserService
import services.DialogService

class AssignCourierFragment : Fragment() {

    private lateinit var binding: FragmentAssignCourierBinding
    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var odViewModel: OrderDetailsViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var deliveryService: DeliveryService
    private lateinit var dialogService: DialogService
    private lateinit var userService: UserService
    private lateinit var adapter: CourierAdapter
    private lateinit var selectedCourier: User
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
        dialogService = DialogService.create(_context)
        ordersViewModel = ViewModelProvider(this.requireActivity())[OrdersViewModel::class.java]
        sharedViewModel = ViewModelProvider(this.requireActivity())[SharedViewModel::class.java]
        odViewModel = ViewModelProvider(this.requireActivity())[OrderDetailsViewModel::class.java]

        binding = FragmentAssignCourierBinding.inflate(inflater, container, false)

        binding.bAssignCourier.setOnClickListener{

            val r = Runnable {

                deliveryService.assignCourier(

                    PostDelivery(
                        odViewModel.order.value?.id!!,
                        selectedCourier.id!!,
                        binding.tvArriveTime.text.toString()
                    ),
                    _context

                ).observe(viewLifecycleOwner, Observer {
                    value ->

                    if (value != null){

                        toastNotification.showLongMessage(_context, "Заказ успешно назначен")
                        ordersViewModel.removeOrderFromList(odViewModel.order.value!!)
                        parentFragmentManager.popBackStack()
                    }

                })

            }


            dialogService.getDialog(
                "Назначение",
                "Назначить ${selectedCourier.getFullName()} на заказ №${ odViewModel.order.value?.id}",
                r
            ).show()
        }

        binding.tvArriveTime.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.bAssignCourier.isEnabled = adapter.getSelectedCourier() != null
                        && binding.tvArriveTime.text!!.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        initialization()

        return binding.root
    }

    private fun initialization(){

        if(sharedViewModel.couriers.isInitialized && odViewModel.order.isInitialized){

            refreshRecyclerView(sharedViewModel.couriers.value!!)

        }
        else{

            refreshCouriers()

        }

    }

    private fun refreshCouriers(){
        userService.getCouriers(_context).observe(viewLifecycleOwner, Observer {
                data ->

            if (data != null) {

                sharedViewModel.setCouriers(data)
                refreshRecyclerView(data)

            }

        })
    }

    private fun refreshRecyclerView(items: List<User>){

        adapter.refreshCouriers(items)

        adapter.setOnClickListener(object : CourierAdapter.OnClickListener {

            override fun onClick(position: Int, model: User) {

                selectedCourier = model

            }

        })

        binding.apply {

            rcViewCouriers.layoutManager = LinearLayoutManager(_context)
            rcViewCouriers.adapter = adapter

        }
    }

}