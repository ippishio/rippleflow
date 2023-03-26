package com.example.memtone.contact

import com.example.memtone.contact.Model.Contact

interface ContactActionListener {

    fun onContactClick(contact: Contact)

    fun onContactMoreClick(contact: Contact)

}