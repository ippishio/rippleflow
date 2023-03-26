package com.example.memtone.model

import androidx.room.*

@Dao
interface TransactionDao {
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransaction() : List<Transaction>

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}