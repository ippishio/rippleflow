package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import org.json.JSONTokener
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {


    var balanceDollars: TextView? = null;
    var balanceDollarsVal: Float? = null
    var balanceXRP: TextView? = null;
    var balanceXRPVal: Float = 10.5f;

    override fun onCreate(savedInstanceState1: Bundle?) {
        super.onCreate(savedInstanceState1)
        setContentView(R.layout.activity_main)
        balanceDollars = findViewById(R.id.balanceDollars)
        //balance_dollars?.setText(convert_to_dollars(balance_XRP_val.toString()))
        convertToDollars(balanceXRPVal.toString())
        balanceXRP = findViewById(R.id.balanceXrp)
        balanceXRP?.setText("$balanceXRPVal XRP")

    }


    fun convertToDollars(balance: String){
        var ansT: String? = null;
        Thread {
            var ans: String?
            ans = URL("https://api3.binance.com/api/v3/avgPrice?symbol=XRPUSDT").readText()
            runOnUiThread {
                if(ans != null) {
                    val jsonObject = JSONTokener(ans).nextValue() as JSONObject

                    val df = DecimalFormat("#.##")
                    df.roundingMode = RoundingMode.DOWN
                    ansT = df.format(jsonObject.getString("price").toFloat() * balance.toFloat())

                    balanceDollars?.setText("$$ansT USD")

                    balanceDollarsVal = jsonObject.getString("price").toFloat() * balance.toFloat()
                }
            }
        }.start()
    }
    fun button1Fun(view: View){
        val transferIntent = Intent(this, TransferActivity::class.java)
        startActivity(transferIntent)
    }

    fun button2Fun(view: View){
        val myToast = Toast.makeText(this, "Переведено по NFC", Toast.LENGTH_SHORT)
        myToast.show()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}