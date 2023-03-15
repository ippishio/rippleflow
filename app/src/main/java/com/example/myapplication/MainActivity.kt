package com.example.myapplication

import android.content.Intent
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
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody


class MainActivity : AppCompatActivity() {

    val walletAddress = "rwS7dDsP8H3eafWosKSgNzSBQbGAHfKe9z";
    val walletSecret = "sEdVSeKU5vPryw9QKWu2qx7ALDTm98V";
    var balanceDollars: TextView? = null;
    var balanceDollarsVal: Float? = null
    var balanceXRP: TextView? = null;
    var balanceXRPVal: Float = 10.5f;

    override fun onCreate(savedInstanceState1: Bundle?) {
        super.onCreate(savedInstanceState1)
        setContentView(R.layout.activity_main)
        balanceDollars = findViewById(R.id.balanceDollars)
        //balance_dollars?.setText(convert_to_dollars(balance_XRP_val.toString()))
        getBalance()
        balanceXRP = findViewById(R.id.balanceXrp)
        balanceXRP?.setText("$balanceXRPVal XRP")

    }

    fun getBalance() {
        Fuel.post("https://s.altnet.rippletest.net:51234/")
            .jsonBody("{ \"method\" : \"account_info\", " +
                    "\"params\" : [" +
                    "{\"account\": \"" +walletAddress+"\"," +
                    "\"strict\": true,"+
                    "\"ledger_index\":\"current\","+
                    "\"queue\": true}"+
                    "] }")
            .also { println(it) }
            .response { result -> run {
                val (bytes, error) = result
                if(bytes != null) {
                    val jsonObject = JSONTokener(String(bytes)).nextValue() as JSONObject
                    balanceXRPVal= jsonObject.getJSONObject("result").getJSONObject("account_data").getString("Balance").toInt()/1000000.0f
                    println(balanceXRPVal)
                    balanceXRP?.setText("$balanceXRPVal XRP")
                    convertToDollars(balanceXRPVal.toString())
                }
            }}
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
        getBalance()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}