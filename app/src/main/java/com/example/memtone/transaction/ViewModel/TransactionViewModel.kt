package com.example.memtone.transaction.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.memtone.contact.Model.Contact
import com.example.memtone.contact.Repository.ContactReposity
import com.example.memtone.transaction.Model.Transaction
import com.example.memtone.transaction.Repository.TransactionRepository

class TransactionViewModel: ViewModel() {
    fun insert(context: Context, transaction: Transaction) =
        TransactionRepository.insertTransaction(context, transaction)

    fun getAllTransactionData(context: Context): LiveData<List<Transaction>> {
        return TransactionRepository.getAllTransactionData(context)
    }

    fun deleteTransaction(context: Context, transaction: Transaction) =
        TransactionRepository.deleteTransaction(context, transaction)

    fun deleteAllDataTransaction(context: Context) =
        TransactionRepository.deleteAllDataTransaction(context)
}