package com.danan.storyapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.danan.storyapp.data.response.StoryItem
import com.danan.storyapp.databinding.ActivityDetailBinding
import com.danan.storyapp.withDateFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story = intent.getParcelableExtra<StoryItem>(EXTRA_STORY)
        binding.apply {
            tvDetailName.text = story?.name
            tvDetailDate.text = story?.createdAt?.withDateFormat()
            tvDetailDescription.text = story?.description
            Glide.with(binding.root)
                .load(story?.photoUrl)
                .into(ivDetailPhoto)
        }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }

}