package com.example.contentapp.repository

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import com.example.contentapp.data.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepository(private val context: Context) {

    private val TAG = "ContactRepository"

    suspend fun getAllContacts(): List<Contact> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
            getContactsFromCursor(it)
        }.orEmpty()
    }

    suspend fun getSingleContact(id: Long): Contact? = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            "${ContactsContract.Contacts._ID} = ?",
            arrayOf(id.toString()),
            null
        )?.use {
            getSingleContactFromCursor(it)
        }
    }

    private fun getSingleContactFromCursor(cursor: Cursor): Contact {
        cursor.moveToFirst()
        val indexName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
        val indexSurname =
            cursor.getColumnIndex("phonetic_name")
        val name = cursor.getString(indexName).orEmpty()
        val surname = cursor.getString(indexSurname).orEmpty()
        Log.d("getSingleContactFromCursor", "surname - $surname")
        val indexID = cursor.getColumnIndex(ContactsContract.Contacts._ID)
        val id = cursor.getLong(indexID)
        return Contact(
                id = id,
                name = name,
                surname = surname,
                phone = getContactPhones(id),
                email = getContactEmails(id)
            )

    }

    suspend fun deleteContact(id: Long) {
        withContext(Dispatchers.IO) {
           val r = context.contentResolver.delete(
                ContactsContract.RawContacts.CONTENT_URI,
                "${ContactsContract.RawContacts._ID} = ?",
                arrayOf(id.toString())
            )
        }
    }


    private fun getContactsFromCursor(cursor: Cursor): List<Contact> {
        if (!cursor.moveToFirst()) return emptyList()
        val list: MutableList<Contact> = mutableListOf()
        do {
            val indexName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val indexSurname =
                cursor.getColumnIndex(ContactsContract.Contacts.PHONETIC_NAME)
            val name = cursor.getString(indexName).orEmpty()
            val surname = cursor.getString(indexSurname).orEmpty()
            val indexID = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val id = cursor.getLong(indexID)
            list.add(
                Contact(
                    id = id,
                    name = name,
                    surname = surname,
                    phone = getContactPhones(id),
                    email = getContactEmails(id)
                )
            )
        } while (cursor.moveToNext())
        return list
    }


    private fun getContactPhones(id: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(id.toString()),
            null
        )?.use {
            getPhonesFromCursor(it)
        }.orEmpty()
    }

    private fun getPhonesFromCursor(cursor: Cursor): List<String> {
        if (!cursor.moveToFirst()) return emptyList()
        val list: MutableList<String> = mutableListOf()
        do {
            val indexPhone = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val phone = cursor.getString(indexPhone)
            list.add(phone)
        } while (cursor.moveToNext())
        return list
    }

    private fun getContactEmails(id: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(id.toString()),
            null
        )?.use {
            getEmailsFromCursor(it)
        }.orEmpty()
    }

    private fun getEmailsFromCursor(cursor: Cursor): List<String> {
        if (!cursor.moveToFirst()) return emptyList()
        val list: MutableList<String> = mutableListOf()
        do {
            val indexEmail = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
            val email = cursor.getString(indexEmail)
            list.add(email)
        } while (cursor.moveToNext())
        return list
    }
}