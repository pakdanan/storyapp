package com.danan.storyapp.viewmodels

import androidx.lifecycle.ViewModel
import com.danan.storyapp.data.UserRepository

class RegisterViewModel(private val repo: UserRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) =
        repo.register(name, email, password)
}