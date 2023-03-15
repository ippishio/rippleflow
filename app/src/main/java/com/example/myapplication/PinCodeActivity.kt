package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.StartActivity.Companion.prefs

class PinCodeActivity : AppCompatActivity() {
    //var textView: TextView? = null;
    var pinView: TextView? = null;
    var pin: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_code)
        pinView = findViewById(R.id.textView5)
        //textView = findViewById(R.id.textView4)
        //textView?.setText(StartActivity.get_a().toString())
    }

    fun buttonPin1(view: View){
        pin = pin + '1'

        pinView?.setText(pinView?.text.toString() + " .")
    }
    fun buttonPin2(view: View){
        pin = pin + '2'
        pinView?.setText(pinView?.text.toString() + " .")
    }
    fun buttonPin3(view: View){
        pin = pin + '3'
        pinView?.setText(pinView?.text.toString() + " .")
    }
    fun buttonPin4(view: View){
        pin = pin + '4'
        pinView?.setText(pinView?.text.toString() + " .")
    }
    fun buttonPin5(view: View){
        pin = pin + '5'
        pinView?.setText(pinView?.text.toString() + " .")
    }
    fun buttonPin6(view: View){
        pin = pin + '6'
        pinView?.setText(pinView?.text.toString() + " .")
    }
    fun buttonPin7(view: View){
        pin = pin + '7'
        pinView?.setText(pinView?.text.toString() + " .")
    }
    fun buttonPin8(view: View){
        pin = pin + '8'
        pinView?.setText(pinView?.text.toString() + " .")
    }
    fun buttonPin9(view: View){
        pin = pin + '9'
        pinView?.setText(pinView?.text.toString() + " .")
    }
    fun buttonPin0(view: View){
        pin = pin + '0'
        pinView?.setText(pinView?.text.toString() + " .")
    }
    fun buttonPinBack(view: View){
        if (pin?.length != 0) {
            pin = pin?.dropLast(1)
            pinView?.setText(pinView?.text?.dropLast(2))
        }
    }
    fun buttonPinEnter(view: View){

        var pin_correct: String? = prefs!!.getString("pinCode", "");

        //println(pin_correct?.length)

        if (pin_correct == pin){
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
        } else{
            pin = ""
            val myToast = Toast.makeText(this, "Неверный пароль", Toast.LENGTH_SHORT)
            myToast.show()
            pinView?.setText("")
        }
    }
}