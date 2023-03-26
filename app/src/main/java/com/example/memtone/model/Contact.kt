package com.example.memtone.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class
Contact(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo var name: String?,
    @ColumnInfo var address: String?
)
/*
data class ContactD(
    val id: Long,
    val name: String,
    val address: String
)*/
