package com.example.memtone.model

import androidx.lifecycle.LiveData
import com.github.javafaker.Faker

typealias ContactsListener = (contacts : List<Contact>) -> Unit


class ContactService{
    private var contacts = mutableListOf<Contact>()

    private val listeners = mutableListOf<ContactsListener>()

    private lateinit var contactsDao: TransactionDao

    /*init {
        val faker = Faker.instance()
        contacts = (1..50).map {
            Contact(
                name = faker.name().name(),
                address = faker.address().streetName()
            )
        }.toMutableList()
    }*/

    fun getContacts() : List<Contact> {
        return contacts
    }


    fun deleteContact(contact: Contact) {
        //TODO delete with index in room
        val indexToDelete = contacts.indexOfFirst {
            it.id == contact.id
        }
        if (indexToDelete != -1) {
            contacts.removeAt(indexToDelete)
        }
        notifyChanges()
    }
    fun addListener(listener: ContactsListener) {
        listeners.add(listener)
        listener.invoke(contacts)
    }
    fun removeListener(listener: ContactsListener) {
        listeners.remove(listener)
    }
    private fun notifyChanges() {
        listeners.forEach { it.invoke(contacts) }
    }
}
