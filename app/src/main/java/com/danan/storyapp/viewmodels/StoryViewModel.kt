package com.danan.storyapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.danan.storyapp.data.SessionPreferences
import com.danan.storyapp.data.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(
    private val storyRepo: StoryRepository,
    private val sessionPref: SessionPreferences
) :
    ViewModel() {

    fun getToken(): LiveData<String> {
        return sessionPref.getToken().asLiveData()
    }

    fun addStory(token: String, imageMultipart: MultipartBody.Part, desc: RequestBody) =
        storyRepo.addStory(token, imageMultipart, desc)

}