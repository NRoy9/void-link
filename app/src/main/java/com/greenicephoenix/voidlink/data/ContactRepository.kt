package com.greenicephoenix.voidlink.data

import android.content.Context
import android.provider.ContactsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ContactRepository(private val context: Context, private val contactDao: ContactDao) {

    // A live stream of contacts from our local database
    val allContacts: Flow<List<ContactEntity>> = contactDao.getAllContacts()

    /**
     * This function reaches into the Android System, grabs all contacts,
     * cleans them up, and saves them to our Room DB.
     */
    suspend fun refreshContacts() = withContext(Dispatchers.IO) {
        val contactList = mutableListOf<ContactEntity>()
        val contentResolver = context.contentResolver

        // Query the Android Contacts ContentProvider
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val id = it.getString(idIndex)
                val name = it.getString(nameIndex) ?: "Unknown"
                val number = it.getString(numberIndex) ?: ""

                // Strip everything except digits for T9 search efficiency
                val normalized = number.filter { char -> char.isDigit() }

                contactList.add(
                    ContactEntity(
                        id = id,
                        displayName = name,
                        phoneNumber = number,
                        normalizedNumber = normalized
                    )
                )
            }
        }

        if (contactList.isNotEmpty()) {
            contactDao.clearAll() // Clear old cache
            contactDao.insertContacts(contactList) // Insert fresh data
        }
    }
}