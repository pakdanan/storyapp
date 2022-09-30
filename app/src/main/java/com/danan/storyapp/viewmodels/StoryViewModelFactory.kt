package com.danan.storyapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danan.storyapp.data.SessionPreferences
import com.danan.storyapp.data.StoryRepository
import com.danan.storyapp.di.StoryInjection
import com.danan.storyapp.di.UserInjection

class StoryViewModelFactory private constructor(
    private val storyRepo: StoryRepository,
    private val sessionPref: SessionPreferences
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(storyRepo, sessionPref) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(storyRepo, sessionPref) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: StoryViewModelFactory? = null
        fun getInstance(context: Context): StoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: StoryViewModelFactory(
                    StoryInjection.provideRepository(),
                    UserInjection.providePreferences(context)
                )
            }.also { instance = it }
    }

}