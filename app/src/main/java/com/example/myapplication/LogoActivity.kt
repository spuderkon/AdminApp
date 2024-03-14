package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLogoBinding
import notifiy.ToastNotification
import services.TokenService

class LogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoBinding

    private lateinit var sharedPreferences: SharedPreferences

    private var SPLASH_TIME_OUT: Long = 3000
    private lateinit var r: Runnable

    private fun initValues(){
        logoActivity = this
        toastNotification = ToastNotification
        sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)

        r = Runnable {
            if (TokenService.isAuth(sharedPreferences)) {
                tokenService = TokenService.create(sharedPreferences.getString("token", "empty").toString(), sharedPreferences)
                Log.d("LogoA", "Redirect to MainActivity")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Log.d("LogoA", "Redirect to LoginActivity")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initValues()


        //sharedPreferences.edit().clear().apply()
        Handler(Looper.getMainLooper()).postDelayed(r, (3000))
    }

}