package com.example.memtone.transaction.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.memtone.transaction.Model.Transaction

@Dao
interface TransactionDao {
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions")
    fun getAllTransaction() : LiveData<List<Transaction>>

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}