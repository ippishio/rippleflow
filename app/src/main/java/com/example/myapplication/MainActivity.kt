package com.example.myapplication

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import org.json.JSONTokener
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {


    var balance_dollars: TextView? = null;
    var balance_XRP: TextView? = null;
    var balance_XRP_val: Int = 10;
    //var button1: Button = findViewById(R.id.button2);


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        balance_dollars = findViewById(R.id.balance)
        //balance_dollars?.setText(convert_to_dollars(balance_XRP_val.toString()))
        convert_to_dollars(balance_XRP_val.toString())
        balance_XRP = findViewById(R.id.textView2)
        balance_XRP?.setText("$balance_XRP_val XRP")

    }

    fun convert_to_dollars(balance: String){
        var ans_t: String? = null;
        Thread {
            var ans: String?
            ans = URL("https://api3.binance.com/api/v3/avgPrice?symbol=XRPUSDT").readText()
            runOnUiThread {
                if(ans != null) {
                    val jsonObject = JSONTokener(ans).nextValue() as JSONObject

                    ans_t = (jsonObject.getString("price").toDouble() * balance.toInt()).toString()
                    balance_dollars?.setText("$ans_t $")
                    //val MyToast = Toast.makeText(this, , Toast.LENGTH_SHORT)
                    //MyToast.show()

                }
            }
        }.start()
    }
    fun Button1Fun(view: View){
//        val MyToast = Toast.makeText(this, URL("https://api3.binance.com/api/v3/avgPrice?symbol=XRPUSDT").readText(), Toast.LENGTH_SHORT)
        //MyToast.show()
        val MyToast = Toast.makeText(this, "Переведено", Toast.LENGTH_SHORT)
        MyToast.show()
    }

    fun Button2Fun(view: View){
        val MyToast = Toast.makeText(this, "Переведено по NFC", Toast.LENGTH_SHORT)
        MyToast.show()
    }




}