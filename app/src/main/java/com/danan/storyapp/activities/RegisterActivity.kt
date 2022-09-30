package com.danan.storyapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.danan.storyapp.R
import com.danan.storyapp.data.Result
import com.danan.storyapp.databinding.ActivityRegisterBinding
import com.danan.storyapp.viewmodels.RegisterViewModel
import com.danan.storyapp.viewmodels.UserViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val userViewModelFactory: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        registerViewModel =
            ViewModelProvider(this, userViewModelFactory)[RegisterViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val name = binding.edRegisterName.text.toString().trim()
        val email = binding.edRegisterEmail.text.toString().trim()
        val password = binding.edRegisterPassword.text.toString().trim()
        when {
            name.isEmpty() -> {
                binding.edRegisterName.error = resources.getString(
                    R.string.msg_cannot_empty,
                    resources.getString(R.string.prompt_name)
                )
            }
            email.isEmpty() -> {
                binding.edRegisterEmail.error = resources.getString(
                    R.string.msg_cannot_empty,
                    resources.getString(R.string.prompt_email)
                )
            }
            password.isEmpty() -> {
                binding.edRegisterPassword.error =
                    resources.getString(
                        R.string.msg_cannot_empty,
                        resources.getString(R.string.prompt_password)
                    )
            }
            else -> {
                registerViewModel.register(name, email, password).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                val user = result.data
                                if (user.error) {
                                    Toast.makeText(this, user.message, Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        resources.getString(R.string.msg_success_register),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Intent(this, LoginActivity::class.java).also {
                                        startActivity(it)
                                    }
                                }
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.msg_error_register),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

}