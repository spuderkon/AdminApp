package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.ActivityLoginBinding
import retrofit.services.AuthService
import services.TokenService

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authService: AuthService

    private fun initValues(){

        authService = AuthService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initValues()
        binding.apply {
            bLogin.setOnClickListener{
                authService.authorize(phoneNumber.text.toString(), password.text.toString(), this@LoginActivity).observe(this@LoginActivity, Observer {
                    value ->
                    if(value != null) {
                        if(TokenService.isAdmin(value))
                        {
                            tokenService = TokenService.create(value, logoActivity.getSharedPreferences("token", MODE_PRIVATE))
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                        else{
                            //TO DO BAN
                        }
                    }
                })
            }
        }
    }


}