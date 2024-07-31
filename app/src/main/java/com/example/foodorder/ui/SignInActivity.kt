package com.example.foodorder.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.foodorder.R
import com.example.foodorder.model.SignInRequest
import com.example.foodorder.model.SignInResponse

class SignInActivity : AppCompatActivity() {

    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        val usernameEditText = findViewById<EditText>(R.id.et_username)
        val passwordEditText = findViewById<EditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateInput(username, password)) {
                viewModel.signIn(SignInRequest(username, password))
            } else {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }

    private fun observeViewModel() {
        viewModel.signInResponse.observe(this, { response ->
            response?.let {
                Log.i("SignInActivity", "User ID: ${it.data.user_id}")
                Log.i("SignInActivity", "Token: ${it.data.token}")
                Log.i("SignInActivity", "Username: ${it.data.username}")
                Log.i("SignInActivity", "Response Status: ${it.status}")
                // Handle successful sign-in, e.g., navigate to the main screen
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.error.observe(this, { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
