package com.danan.storyapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.danan.storyapp.data.SessionPreferences
import com.danan.storyapp.data.StoryRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val storyRepo: StoryRepository,
    private val sessionPref: SessionPreferences
) : ViewModel() {

    fun getToken(): LiveData<String> {
        return sessionPref.getToken().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            sessionPref.logout()
        }
    }

    fun getStories(token: String) = storyRepo.getStories(token)

}