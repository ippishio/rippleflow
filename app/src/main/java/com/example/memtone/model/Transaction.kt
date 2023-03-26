package com.example.memtone.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transactions")
data class Transaction(
   @PrimaryKey(autoGenerate = true) var id: Long = 0,
   @ColumnInfo var address: String?,
   @ColumnInfo var date: String?,
   @ColumnInfo var sum: String?
)
