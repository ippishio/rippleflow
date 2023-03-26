package com.example.memtone.model

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ContactRepository {
    companion object {
        var rvdatabase: RVDatabase ?= null

        private fun initDB(context: Context): RVDatabase {
            return RVDatabase.getInstance(context)
        }

        fun insert(context: Context, contact: Contact) {
            rvdatabase = initDB(context)
            CoroutineScope(IO).launch {
                rvdatabase!!.ContactDao().addContact(contact)
            }
        }

        fun getAllContactData(context: Context): List<Contact> {
            rvdatabase = initDB(context)
            return rvdatabase!!.ContactDao().getAllContact()
        }
    }
}