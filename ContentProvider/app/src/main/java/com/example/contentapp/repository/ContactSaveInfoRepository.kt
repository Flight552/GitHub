package com.example.contentapp.repository

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.regex.Pattern

class ContactSaveInfoRepository(private val context: Context) {

    private val TAG = "ContactSaveInfoRepository"


    private val phonePattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}\$")

    private fun saveRawContact(): Long {
        val uri = context.contentResolver.insert(
            ContactsContract.RawContacts.CONTENT_URI,
            ContentValues()
        )
        Log.d("saveRawContact", "$uri")
        return uri?.lastPathSegment?.toLongOrNull() ?: -1
    }

    suspend fun saveContact(name: String, surname: String, phone: String, email: String) =
        withContext(Dispatchers.IO) {
            if (phonePattern.matcher(phone).matches()
                    .not() || name.isBlank() || surname.isBlank()
            ) {
                throw IOException()
            }
            val contactID = saveRawContact()
            saveContactName(contactID, name)
            saveContactSurname(contactID, surname)
            saveContactPhone(contactID, phone)
            if (email.isNotEmpty()) {
                saveContactEmail(contactID, email)
            }
            Log.d("ContactRepositorySave", "name: $name, surname: $surname, phone: $phone")
        }

    private fun saveContactName(id: Long, name: String) {

        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
        }
        Log.d("ContactRepositoryContentValues", contentValues.toString())
        context.contentResolver.insert(
            ContactsContract.Data.CONTENT_URI,
            contentValues
        )
    }

    private fun saveContactSurname(id: Long, surname: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
               put(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_NAME, surname)
        }

        context.contentResolver.insert(
            ContactsContract.Data.CONTENT_URI,
            contentValues
        )
    }

    private fun saveContactPhone(id: Long, phone: String) {

        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        }
        context.contentResolver.insert(
            ContactsContract.Data.CONTENT_URI,
            contentValues
        )
    }

    private fun saveContactEmail(id: Long, email: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, id)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
        }
        context.contentResolver.insert(
            ContactsContract.Data.CONTENT_URI,
            contentValues
        )
    }
}