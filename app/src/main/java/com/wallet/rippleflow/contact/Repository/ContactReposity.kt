package com.wallet.rippleflow.contact.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.wallet.rippleflow.contact.Model.Contact
import com.wallet.rippleflow.RVDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ContactReposity {

    companion object {
        var rvdatabase: RVDatabase?= null

        private fun initDB(context: Context) : RVDatabase {
            return RVDatabase.getInstance(context)
        }

        fun insertContact(context: Context, contact: Contact) {
            rvdatabase = initDB(context)

            CoroutineScope(IO).launch {
                rvdatabase!!.ContactDao().addContact(contact)
            }
        }

        fun getAllContactData(context: Context): LiveData<List<Contact>> {
            rvdatabase = initDB(context)
            return rvdatabase!!.ContactDao().getAllContact()
        }

        fun deleteContact(context: Context, contact: Contact) {
            rvdatabase = initDB(context)
            CoroutineScope(IO).launch {
                rvdatabase!!.ContactDao().deleteContact(contact)
            }
        }

        fun deleteAllDataContact(context: Context) {
            rvdatabase = initDB(context)
            CoroutineScope(IO).launch {
                rvdatabase!!.clearAllTables()
            }
        }
    }
}