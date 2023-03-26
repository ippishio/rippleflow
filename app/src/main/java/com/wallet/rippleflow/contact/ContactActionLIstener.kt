package com.wallet.rippleflow.contact

import com.wallet.rippleflow.contact.Model.Contact

interface ContactActionListener {

    fun onContactClick(contact: Contact)

    fun onContactMoreClick(contact: Contact)

}