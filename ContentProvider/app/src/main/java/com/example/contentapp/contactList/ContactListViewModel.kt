package com.example.contentapp.contactList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contentapp.data.Contact
import com.example.contentapp.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactListViewModel(application: Application): AndroidViewModel(application) {
    private val repository = ContactRepository(application)

    private val contactsMutableLiveData = MutableLiveData<List<Contact>>()

    val contactsLiveData: LiveData<List<Contact>>
        get() = contactsMutableLiveData

    fun loadList() {
        viewModelScope.launch {
            try {
                contactsMutableLiveData.postValue(repository.getAllContacts())
                Log.e("ContactListViewModel", repository.getAllContacts().toString())

            } catch (t: Throwable) {
                Log.e("ContactListViewModel", "contact list error", t)
                contactsMutableLiveData.postValue(emptyList())
            }
        }
    }
}