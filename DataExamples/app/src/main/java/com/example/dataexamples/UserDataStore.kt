package com.example.dataexamples

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class UserDataStore(private val context: Context) {

    // Nos aseguramos que sea un singleton
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userDataStore")

    // Creamos una variable para cada key a almacenar
    companion object {
        val USER_ID = intPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

    // Creamos una función para cada key para obtener el valor
    fun getId() = context.dataStore.data.map { it[USER_ID] ?: 0 }
    fun getUsername() = context.dataStore.data.map { it[USER_NAME] ?: "" }
    fun getEmail() = context.dataStore.data.map { it[USER_EMAIL] ?: "" }
    fun getToken() = context.dataStore.data.map { it[USER_TOKEN] ?: "" }

    // Creamos una función para cada key para guardar el valor
    suspend fun setId(value: Int) { context.dataStore.edit { it[USER_ID] = value } }
    suspend fun setUsername(value: String) { context.dataStore.edit { it[USER_NAME] = value } }
    suspend fun setEmail(value: String) { context.dataStore.edit { it[USER_EMAIL] = value } }
    suspend fun setToken(value: String) { context.dataStore.edit { it[USER_TOKEN] = value } }

}