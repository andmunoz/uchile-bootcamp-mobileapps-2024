package com.example.milistadecompras.helpers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class ProfileDataStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("profileDS")

    companion object {
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val EMAIL_ADDRESS = stringPreferencesKey("email_address")
        val PHONE_NUMBER = longPreferencesKey("phone_number")
    }

    suspend fun getFirstName() = context.dataStore.data.map { it[FIRST_NAME] ?: ""}
    suspend fun getLastName() = context.dataStore.data.map { it[LAST_NAME] ?: ""}
    suspend fun getEmailAddress() = context.dataStore.data.map { it[EMAIL_ADDRESS] ?: ""}
    suspend fun getPhoneNumber() = context.dataStore.data.map { it[PHONE_NUMBER] ?: 0}

    suspend fun setFirstName(value: String) {
        context.dataStore.edit { it[FIRST_NAME] = value }
    }

    suspend fun setLastName(value: String) {
        context.dataStore.edit { it[LAST_NAME] = value }
    }

    suspend fun setEmailAddress(value: String) {
        context.dataStore.edit { it[EMAIL_ADDRESS] = value }
    }

    suspend fun setPhoneNumber(value: Long) {
        context.dataStore.edit { it[PHONE_NUMBER] = value }
    }
}