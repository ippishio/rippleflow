package com.wallet.rippleflow.transaction.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.wallet.rippleflow.RVDatabase
import com.wallet.rippleflow.contact.Model.Contact
import com.wallet.rippleflow.transaction.Model.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionRepository {

    companion object {
        var rvdatabase: RVDatabase?= null

        private fun initDB(context: Context) : RVDatabase {
            return RVDatabase.getInstance(context)
        }

        fun insertTransaction(context: Context, transaction: Transaction) {
            rvdatabase = initDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                rvdatabase!!.TransactionDao().addTransaction(transaction)
            }
        }

        fun getAllTransactionData(context: Context): LiveData<List<Transaction>> {
            rvdatabase = initDB(context)
            return rvdatabase!!.TransactionDao().getAllTransaction()
        }

        fun deleteTransaction(context: Context, transaction: Transaction) {
            rvdatabase = initDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                rvdatabase!!.TransactionDao().deleteTransaction(transaction)
            }
        }

        fun deleteAllDataTransaction(context: Context) {
            rvdatabase = initDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                rvdatabase!!.clearAllTables()
            }
        }
    }
}