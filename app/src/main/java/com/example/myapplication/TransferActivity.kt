package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class TransferActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
    }

    fun makeTransferClick(view: View){
        val newToast = Toast.makeText(this, "Переведено", Toast.LENGTH_SHORT)
        newToast.show()
    }
}