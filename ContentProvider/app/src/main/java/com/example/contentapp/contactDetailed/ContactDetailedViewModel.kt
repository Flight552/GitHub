package com.example.contentapp.contactDetailed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.contentapp.R
import com.example.contentapp.data.Contact
import com.example.contentapp.data.IncorrectFormException
import com.example.contentapp.repository.ContactRepository
import com.example.contentapp.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ContactDetailedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository = ContactRepository(application)
    private val contactSingleLiveData = SingleLiveEvent<Contact>()
    val singleContactLiveData: LiveData<Contact>
        get() = contactSingleLiveData

    private var saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    private var saveErrorLiveEvent = SingleLiveEvent<Int>()
    val successLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent

    val errorLiveEvent: LiveData<Int>
        get() = saveErrorLiveEvent

    fun singleContact(id: Long) {
        viewModelScope.launch {
            try {
                val contact = repository.getSingleContact(id)
                contactSingleLiveData.postValue(contact!!)
            } catch (t: Throwable) {
                showError(t)
            }
        }
    }

    fun deleteSelectedContact(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteContact(id)
                saveSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                showError(t)
            }
        }
    }

    private fun showError(t: Throwable) {
        saveErrorLiveEvent.postValue(
            when (t) {
                is IncorrectFormException -> R.string.contact_add_save_error_form
                else -> R.string.contact_add_save_error
            }
        )
    }
}