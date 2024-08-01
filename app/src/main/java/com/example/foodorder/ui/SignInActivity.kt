package com.example.foodorder.ui

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorder.R
import com.example.foodorder.api.RetrofitClient
import com.example.foodorder.model.SignInRequest
import com.example.foodorder.model.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        Log.d("SignInActivity", "onCreate called")

        val usernameEditText: EditText = findViewById(R.id.et_username)
        val passwordEditText: EditText = findViewById(R.id.et_password)
        val signUpTextView: TextView = findViewById(R.id.action_text_sign_up)

        val text = "Belum punya Akun? Daftar Disini"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Navigate to sign up page
                val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }

        spannableString.setSpan(clickableSpan, 17, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(UnderlineSpan(), 17, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        signUpTextView.text = spannableString
        signUpTextView.movementMethod = android.text.method.LinkMovementMethod.getInstance()

        val loginButton: Button = findViewById(R.id.btn_login)
        loginButton.setOnClickListener {
            Log.d("SignInActivity", "Login button clicked")
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val request = SignInRequest(username, password)
                login(request)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(request: SignInRequest) {
        Log.d("SignInActivity", "Sending login request: $request")
        RetrofitClient.instance.login(request).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.statusCode == 200) {
                        Toast.makeText(this@SignInActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                        Log.d("SignInActivity", "Login Successful: ${loginResponse.data.username}")
                        // Navigate to the main page
                        val intent = Intent(this@SignInActivity, SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@SignInActivity, loginResponse?.message ?: "Login Failed", Toast.LENGTH_SHORT).show()
                        Log.e("SignInActivity", "Login Failed: ${response.errorBody()?.string()}")
                    }
                } else {
                    Toast.makeText(this@SignInActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                    Log.e("SignInActivity", "Login Failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Toast.makeText(this@SignInActivity, "Login Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("SignInActivity", "Login Error", t)
            }
        })
    }
}
