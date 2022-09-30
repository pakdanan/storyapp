package com.danan.storyapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.danan.storyapp.R
import com.danan.storyapp.databinding.ActivityLoginBinding
import com.danan.storyapp.viewmodels.LoginViewModel
import com.danan.storyapp.viewmodels.UserViewModelFactory
import com.danan.storyapp.data.Result

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            login()
        }

        val userViewModelFactory: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(this, userViewModelFactory)[LoginViewModel::class.java]

        loginViewModel.getToken().observe(this) { token ->
            if (token.isNotEmpty()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }

    private fun login() {
        val email = binding.edLoginEmail.text.toString().trim()
        val password = binding.edLoginPassword.text.toString().trim()
        when {
            email.isEmpty() -> {
                binding.edLoginEmail.error = resources.getString(
                    R.string.msg_cannot_empty,
                    resources.getString(R.string.prompt_email)
                )
            }
            password.isEmpty() -> {
                binding.edLoginPassword.error =
                    resources.getString(
                        R.string.msg_cannot_empty,
                        resources.getString(R.string.prompt_password)
                    )
            }
            else -> {
                loginViewModel.login(email, password).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                val user = result.data
                                if (user.error) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        user.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    loginViewModel.save(
                                        user.loginResult?.token ?: "",
                                        user.loginResult?.name ?: ""
                                    )
                                }
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.msg_error_login),
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