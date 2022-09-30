package com.danan.storyapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.danan.storyapp.data.SessionPreferences
import com.danan.storyapp.data.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repo: UserRepository,
    private val sessionPref: SessionPreferences
) : ViewModel() {

    fun save(token: String, userName: String) {
        viewModelScope.launch {
            sessionPref.save(token, userName)
        }
    }

    fun getToken(): LiveData<String> {
        return sessionPref.getToken().asLiveData()
    }

    fun login(email: String, password: String) = repo.login(email, password)

}