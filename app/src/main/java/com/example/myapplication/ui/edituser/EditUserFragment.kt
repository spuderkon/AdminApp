package com.example.myapplication.ui.edituser

import adapters.UserAdapter
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEditUserBinding
import com.example.myapplication.toastNotification
import com.example.myapplication.ui.assigncourier.AssignCourierViewModel
import data.SharedViewModel
import data.models.Role
import data.models.User
import data.modelsDTO.PostUser
import retrofit.services.RoleService
import retrofit.services.UserService

class EditUserFragment : Fragment() {

    private lateinit var _context: Context
    private lateinit var userService: UserService
    private lateinit var viewModel: EditUserViewModel
    private lateinit var roleService: RoleService
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentEditUserBinding
    private lateinit var acViewModel: AssignCourierViewModel
    private lateinit var adapter: ArrayAdapter<Role>
    private var selectedRole: Role? = null

    /*

       Сделать проверку на заполненность всех полей

    */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        userService = UserService()
        roleService = RoleService()
        _context = this.requireContext()

        acViewModel = ViewModelProvider(this.requireActivity())[AssignCourierViewModel::class.java]
        viewModel = ViewModelProvider(this.requireActivity())[EditUserViewModel::class.java]
        sharedViewModel = ViewModelProvider(this.requireActivity())[SharedViewModel::class.java]
        binding = FragmentEditUserBinding.inflate(inflater, container, false)

        initialization()

        binding.apply {

            bSave.setOnClickListener {

                if (viewModel.option.value == "create") {

                    userService.add(

                        PostUser(etName.text.toString(), etSurname.text.toString(), etLastname.text.toString(),
                                 etEmail.text.toString(), getClearPhone(etPhoneNumber.text.toString()),
                                 selectedRole?.id!!, etPassword.text.toString()
                        ), _context ).observe(viewLifecycleOwner, Observer {
                            data ->

                        if (data!=null){

                            sharedViewModel.addCourier(data)

                            toastNotification.showLongMessage(_context, "Пользователь успешно создан")
                            parentFragmentManager.popBackStack()

                        }
                    })

                }
                else{



                }
            }
        }

        return binding.root
    }

    private fun initialization(){

        if(viewModel.option.value == "create"){

            binding.llPassword.visibility = View.VISIBLE

        }
        else{

            binding.llAddress.visibility = View.VISIBLE

            if(viewModel.selectedEmployee.isInitialized){

                viewModel.selectedEmployee.observe(viewLifecycleOwner, Observer {
                        data ->

                    refreshUserData(data)

                })

            }

        }

        if (viewModel.roles.isInitialized){

            refreshRoleAdapter(viewModel.roles.value!!)

        }
        else{

            refreshRoles()

        }
    }

    private fun refreshUser(id: Int){
        userService.get(id, _context).observe(viewLifecycleOwner, Observer {
                data ->

            if(data!=null)
            {

                sharedViewModel.setSelectedEmployee(data)
                refreshUserData(data)

            }

        })
    }

    private fun refreshRoles(){
        roleService.getAll(_context).observe(viewLifecycleOwner, Observer {
            data ->

            if (data!=null){

                viewModel.setRoles(data)

                refreshRoleAdapter(data)

            }
        })
    }

    private fun refreshRoleAdapter(items: List<Role>){

        adapter = ArrayAdapter<Role>(_context,R.layout.list_role_item, items)

        binding.apply {


            acRole.setAdapter(adapter)
            acRole.setOnItemClickListener { _, _, position, _ ->
                val selectedTeam: Role? = adapter.getItem(position)
                selectedRole = selectedTeam
            }

        }

    }

    private fun refreshUserData(item: User){

        binding.apply {

            etName.setText(item.name)
            etSurname.setText(item.surname)
            etLastname.setText(item.lastname)
            etEmail.setText(item.email)
            etPhoneNumber.setText(item.phoneNumber)

            /*viewModel.roles.observe(viewLifecycleOwner, Observer {
                data ->

            })*/

            etAddress.setText(item.address?.address)

        }

    }

    private fun getClearPhone(value: String): String{
        return value.replace("-" ,"").replace("(", "").replace(")", "")
    }
}