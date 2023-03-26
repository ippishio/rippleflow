package com.wallet.rippleflow.transaction

import android.content.Context
import com.wallet.rippleflow.transaction.Model.Transaction

interface TransactionActionListener {

    fun onTransactionClick(transaction: Transaction)

    fun onTransactionMoreClick(transaction: Transaction)

}