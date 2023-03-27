package com.wallet.rippleflow.contact.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wallet.rippleflow.contact.Model.Contact

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addContact(contact: Contact)

    @Query("SELECT * FROM contact")
    fun getAllContact() : LiveData<List<Contact>>

    @Delete
    suspend fun deleteContact(contact: Contact)
}
