package com.cabila0046.assessment3.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cabila0046.assessment3.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

class UserDataStore(private val context: Context) {
    companion object {
        private val USER_NAME = stringPreferencesKey("nama")
        private val USER_EMAIL = stringPreferencesKey("email")
        private val USER_PHOTO = stringPreferencesKey("photoUrl")
    }
    val userFlow: Flow<User> = context.dataStore.data.map { preferences ->
        User(
           name = preferences[USER_NAME] ?: "",
           email = preferences[USER_EMAIL] ?: "",
           photoUrl = preferences[USER_PHOTO] ?: "",

        )
    }
    suspend fun saveData(user: User) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = user.name
            preferences[USER_EMAIL] = user.email
            preferences[USER_PHOTO] = user.photoUrl
        }
    }
}