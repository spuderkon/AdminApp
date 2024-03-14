package com.example.myapplication

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import data.SharedViewModel
import data.models.User
import retrofit.services.UserService

class MainActivity : AppCompatActivity()  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: NavigationView
    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        userService = UserService()
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navigationView : NavigationView  = findViewById(R.id.nav_view)
        val headerView : View = navigationView.getHeaderView(0)

        val navUsername : TextView = headerView.findViewById(R.id.tvUserName)
        refreshMyData(navUsername)


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_orders, R.id.nav_couriers, R.id.nav_active_deliveries, R.id.nav_edit_users
                ), drawerLayout
            )
        navView.menu
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun refreshMyData(name: TextView){
        userService.getMy(this).observe(this, Observer {
            data ->

                if(data != null) {
                    sharedViewModel.setMe(data)
                    name.text = data.getShortName()
                }
        })
    }
}