package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.StartActivity.Companion.prefs


class RegistrationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    fun registerButtonClick(vew: View){
        var secretKeyView: TextView = findViewById(R.id.textView7)
        var pinView: TextView = findViewById(R.id.textView8)

        if (secretKeyView.text.isEmpty()){
            val myToast = Toast.makeText(this, "Введите пароль от кошелька", Toast.LENGTH_SHORT)
            myToast.show()
        }
        else if (pinView.text.isEmpty()){
            val myToast = Toast.makeText(this, "Придумайте ПИН-код", Toast.LENGTH_SHORT)
            myToast.show()
        } else{
            prefs!!.edit().putString("pinCode", pinView.text.toString()).apply()
            prefs!!.edit().putString("secretKey", secretKeyView.text.toString()).apply()
            prefs!!.edit().putBoolean("is_registered", false).apply()
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
    }

}