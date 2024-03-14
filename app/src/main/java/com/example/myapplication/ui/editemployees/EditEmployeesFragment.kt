package com.example.myapplication.ui.editemployees

import adapters.UserAdapter
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEditEmployeesBinding
import data.SharedViewModel
import data.models.User
import retrofit.services.UserService


class EditEmployeesFragment : Fragment() {

    private lateinit var _context: Context
    private lateinit var binding: FragmentEditEmployeesBinding
    private lateinit var userService: UserService
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _context = this.requireContext()
        userService = UserService()
        sharedViewModel = ViewModelProvider(this.requireActivity())[SharedViewModel::class.java]
        adapter = UserAdapter.create()

        binding = FragmentEditEmployeesBinding.inflate(inflater, container, false)

        initialization()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }

    private fun initialization(){
        if(sharedViewModel.employees.isInitialized){

            sharedViewModel.employees.observe(viewLifecycleOwner, Observer {
                data ->

                refreshRecyclerView(data)

            })

        }
        else{

            refreshUsers()

        }
    }

    private fun refreshUsers(){
        userService.getEmployees(_context).observe(viewLifecycleOwner, Observer {
            data ->

            if(data!=null)
            {

                sharedViewModel.setEmployees(data.toMutableList())
                refreshRecyclerView(data.toMutableList())

            }

        })
    }

    private fun refreshRecyclerView(items: MutableList<User>){

        adapter.refreshUsers(items)

        adapter.setOnClickListener(object: UserAdapter.OnClickListener{
            override fun onClick(position: Int, model: User) {

                sharedViewModel.setSelectedEmployee(model)
                findNavController().navigate(R.id.action_nav_edit_users_to_nav_edit_user)

            }
        })

        binding.apply {

            rcViewUsers.layoutManager = LinearLayoutManager(_context)
            rcViewUsers.adapter = adapter

        }

    }
}