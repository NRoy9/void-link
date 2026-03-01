package com.greenicephoenix.voidlink.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val id: String, // The original Android Contact ID
    val displayName: String,
    val phoneNumber: String,
    val normalizedNumber: String, // Stripped of symbols for T9 search (e.g., 5551234)
    val lastTimeContacted: Long = 0
)