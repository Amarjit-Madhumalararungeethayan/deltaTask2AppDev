package com.example.pingpong

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREF_NAME = "my"

class DataStoreR(context : Context) {

    private object PreferencesKey{
        val name = preferencesKey<Int>("my_num")
    }

    private val dataStore : DataStore<Preferences> = context.createDataStore(
        name = PREF_NAME
    )
    suspend fun saveTo(name:Int){
        dataStore.edit { preference ->
            preference[PreferencesKey.name] = name
        }
    }
    val read : Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException){
                Log.d("DataStore",exception.message.toString())
                emit(emptyPreferences())
        }else{
            throw exception
            }
        }.map { preference ->
            val myNum = preference[PreferencesKey.name]?:0
            myNum
        }
}