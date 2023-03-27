package com.wallet.rippleflow.contact.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wallet.rippleflow.contact.Repository.ContactReposity
import com.wallet.rippleflow.contact.Model.Contact

class ContactViewModel: ViewModel() {
    fun insert(context: Context, contact: Contact) =
        ContactReposity.insertContact(context, contact)

    fun getAllContactData(context: Context): LiveData<List<Contact>> {
        return ContactReposity.getAllContactData(context)
    }

    fun deleteContact(context: Context, contact: Contact) =
        ContactReposity.deleteContact(context, contact)

    fun deleteAllDataContact(context: Context) =
        ContactReposity.deleteAllDataContact(context)
}