package com.example.discussiondatastore.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserLoginManager(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore("login-prefs")

    companion object {
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val USERNAME = preferencesKey<String>("USERNAME")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")
    }

    suspend fun saveDataLogin(
        username : String,
        password : String
    ) {
        dataStore.edit {
            it[USERNAME] = username
            it[PASSWORD] = password
        }
    }

    suspend fun setBoolean(boolean: Boolean){
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }

    suspend fun clearDataLogin(){
        dataStore.edit {
            it.clear()
        }
    }

    val password : Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    val username : Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val boolean : Flow<Boolean> = dataStore.data.map {
        it[BOOLEAN] ?: false
    }
}