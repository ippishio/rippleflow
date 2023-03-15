package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import org.json.JSONObject
import org.json.JSONTokener

//rwS7dDsP8H3eafWosKSgNzSBQbGAHfKe9z
//sEdVSeKU5vPryw9QKWu2qx7ALDTm98V
val walletAddress = "rwS7dDsP8H3eafWosKSgNzSBQbGAHfKe9z";
val walletSecret = "sEdVSeKU5vPryw9QKWu2qx7ALDTm98V";
class TransferActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
    }
    fun sendXRP(reciever: String, amount: Float): String {
        var status: String = "none"
        Fuel.post("https://s.altnet.rippletest.net:51234/")
            .jsonBody("{ \"method\" : \"submit\"," +
                    "\"params\" : [" +
                    "{\"offline\": false," +
                    "\"secret\": \"" + walletSecret +"\","+
                    "\"tx_json\": {"+
                    "\"Account\": \"" +walletAddress+"\"," +
                    "\"Amount\": {" +
                    "\"currency\": \"XRP\"," +
                    "\"issuer\": \""+walletAddress+"\"," +
                    "\"value\": \""+(amount*1000000).toInt().toString()+"\"},"+
                    "\"Destination\":\""+ reciever+"\","+
                    "\"TransactionType\": \"Payment\""+
                    "}, \"fee_mult_max\": 1000"+
                    "}]}")
            .also { println(it) }
            .response { result -> run {
                val (bytes, error) = result
                if(bytes != null) {
                    println(String(bytes))
                    val jsonObject = JSONTokener(String(bytes)).nextValue() as JSONObject
                    status = jsonObject.getString("result")

                }
            }}
        println(status)
        return status
    }
    fun makeTransferClick(view: View){
        val newToast = Toast.makeText(this, "Переведено", Toast.LENGTH_SHORT)
        newToast.show()
        //sendXRP("rKYqT6pM7bLWShGUL9hQsHZjuucaBhTSYz", 50.0f)
        var walletAddress = XRPL().classicAddress;
        println(walletAddress)
    }
}