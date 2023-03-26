package com.example.memtone.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addContact(contact: Contact)

    @Query("SELECT * FROM contact")
    fun getAllContact() : List<Contact>

    @Delete
    suspend fun deleteContact(contact: Contact)
}
