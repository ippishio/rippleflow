package com.example.memtone

import android.app.Application
import android.content.Context
import com.example.memtone.model.ContactDao
import com.example.memtone.model.ContactService
import com.example.memtone.model.RVDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application(){
    val contactService: ContactService = ContactService()
}