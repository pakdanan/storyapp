package com.danan.storyapp.di

import com.danan.storyapp.data.ApiConfig
import com.danan.storyapp.data.StoryRepository

object StoryInjection {

    fun provideRepository(): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(apiService)
    }

}