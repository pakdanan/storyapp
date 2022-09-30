package com.danan.storyapp.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.danan.storyapp.R
import com.danan.storyapp.adapter.ListStoryAdapter
import com.danan.storyapp.databinding.ActivityMainBinding
import com.danan.storyapp.viewmodels.MainViewModel
import com.danan.storyapp.viewmodels.StoryViewModelFactory
import com.danan.storyapp.data.Result
import com.danan.storyapp.data.response.StoryItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_StoryApp)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvStories.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvStories.layoutManager = LinearLayoutManager(this)
        }

        binding.rvStories.adapter = ListStoryAdapter(arrayListOf<StoryItem>())

        val storyViewModelFactory: StoryViewModelFactory = StoryViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(
            this,
            storyViewModelFactory
        )[MainViewModel::class.java]

        mainViewModel.getToken().observe(this) { token: String ->
            if (token == "") {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                showStories(token)
            }
        }

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, StoryActivity::class.java))
        }

    }

    private fun showStories(token: String) {
        mainViewModel.getStories(token).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val stories = result.data.listStory
                        val listStoryAdapter = ListStoryAdapter(stories as ArrayList<StoryItem>)
                        binding.rvStories.adapter = listStoryAdapter
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Failure : " + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                mainViewModel.logout()
                true
            }
            R.id.setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> true
        }
    }

}