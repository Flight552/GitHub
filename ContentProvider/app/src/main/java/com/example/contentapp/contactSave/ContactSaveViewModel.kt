package com.example.contentapp.contactSave

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.contentapp.R
import com.example.contentapp.data.IncorrectFormException
import com.example.contentapp.repository.ContactSaveInfoRepository
import com.example.contentapp.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ContactSaveViewModel(application: Application) : AndroidViewModel(application) {
    private val saveRepository: ContactSaveInfoRepository = ContactSaveInfoRepository(application)
    private var saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    private var saveErrorLiveEvent = SingleLiveEvent<Int>()
    val successLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent

    val errorLiveEvent: LiveData<Int>
        get() = saveErrorLiveEvent

    fun save(name: String, surname: String, phone: String, email: String) {
        viewModelScope.launch {
            try {
                saveRepository.saveContact(name, surname, phone, email)
                saveSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Log.e("ContactSaveViewModel", "contact add error", t)
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