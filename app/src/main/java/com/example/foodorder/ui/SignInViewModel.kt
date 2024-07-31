package com.example.foodorder.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorder.api.RetrofitClient
import com.example.foodorder.model.SignInRequest
import com.example.foodorder.model.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel : ViewModel() {

    private val _signInResponse = MutableLiveData<SignInResponse>()
    val signInResponse: LiveData<SignInResponse> get() = _signInResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun signIn(request: SignInRequest) {
        val call = RetrofitClient.instance.login(request)
        call.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _signInResponse.value = it
                    }
                } else {
                    _error.value = "Login failed: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                _error.value = t.message ?: "An error occurred"
            }
        })
    }
}
