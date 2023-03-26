package com.example.memtone.transaction

import android.content.Context
import com.example.memtone.transaction.Model.Transaction

interface TransactionActionListener {

    fun onTransactionClick(transaction: Transaction)

    fun onTransactionMoreClick(transaction: Transaction)

}