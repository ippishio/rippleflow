package com.wallet.rippleflow.transaction.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wallet.rippleflow.transaction.Model.Transaction

@Dao
interface TransactionDao {
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions")
    fun getAllTransaction() : LiveData<List<Transaction>>

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}