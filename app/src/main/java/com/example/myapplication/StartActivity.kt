package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class StartActivity : AppCompatActivity() {

    companion object{
        var prefs: SharedPreferences? = null
        fun setIsRegisteredTrue(){
            prefs!!.edit().putBoolean("is_registered", true).apply()
        }
        fun setIsRegisteredFalse(){
            prefs!!.edit().putBoolean("is_registered", false).apply()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences("myapplication", MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()

        if (prefs!!.getBoolean("is_registered", true)) {

            val registrationIntent = Intent(this, RegistrationActivity::class.java)
            startActivity(registrationIntent)

        } else{

            val pinCodeIntent = Intent(this, PinCodeActivity::class.java)
            startActivity(pinCodeIntent)

        }
    }




}