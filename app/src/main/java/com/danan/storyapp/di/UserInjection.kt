package com.danan.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.danan.storyapp.data.ApiConfig
import com.danan.storyapp.data.SessionPreferences
import com.danan.storyapp.data.UserRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object UserInjection {

    fun provideRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }

    fun providePreferences(context: Context): SessionPreferences {
        return SessionPreferences.getInstance(context.dataStore)
    }

}