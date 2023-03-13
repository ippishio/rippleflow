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
import java.math.RoundingMode
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {


    var balance_dollars: TextView? = null;
    var balance_dollars_val: Float? = null
    var balance_XRP: TextView? = null;
    var balance_XRP_val: Float = 10.5f;
    //var button1: Button = findViewById(R.id.button2);


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        balance_dollars = findViewById(R.id.balance_dollars)
        //balance_dollars?.setText(convert_to_dollars(balance_XRP_val.toString()))
        convert_to_dollars(balance_XRP_val.toString())
        balance_XRP = findViewById(R.id.balance_xrp)
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

                   // ans_t = String.format("%.2f", jsonObject.getString("price").toDouble() * balance.toInt())
                    val df = DecimalFormat("#.##")
                    df.roundingMode = RoundingMode.DOWN
                    ans_t = df.format(jsonObject.getString("price").toFloat() * balance.toFloat())

                    balance_dollars?.setText("$$ans_t USD")

                    balance_dollars_val = jsonObject.getString("price").toFloat() * balance.toFloat()

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