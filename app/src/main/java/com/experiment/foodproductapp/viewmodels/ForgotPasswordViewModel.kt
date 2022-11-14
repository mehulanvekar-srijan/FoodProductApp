package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.BuildConfig
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


class ForgotPasswordViewModel : ViewModel() {

    private var otp = ""

    private val _inputEmail = mutableStateOf("")
    val inputEmail = _inputEmail

    private val _inputPassword = mutableStateOf("")
    val inputPassword = _inputPassword

    private val _inputOtp = mutableStateOf("")

    fun setEmail(input: String) {
        _inputEmail.value = input
    }

    fun setOtp(input: String) {
        _inputOtp.value = input
    }

    fun setPassword(input: String) {
        _inputPassword.value = input
    }

    suspend fun isUserRegistered(context: Context): Boolean {

        Log.d("testFP", "isUserRegistered: ${inputEmail.value}")

        val deferred: Deferred<Boolean> = viewModelScope.async(Dispatchers.IO){
            val result = DatabaseRepository(context).getUserByEmail(_inputEmail.value)
            Log.d("testFP", "deferred: $result")
            if(result == null) false else true
        }

        return deferred.await()
    }



    fun sendOtp(){
        otp = ( (Math.random() * 9000).toInt() + 1000 ).toString()
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaTypeOrNull()
        val content = """{"personalizations": [ { "to": [ { "email": "${inputEmail.value}" } ], "subject": "OTP" } ], "from": {"email": "noobdeshwar@gmail.com" },"content": [{ "type": "text/plain","value": "Your otp is $otp" }] }"""
        val body = RequestBody.create(mediaType,content)
        val request = Request.Builder()
            .url("https://rapidprod-sendgrid-v1.p.rapidapi.com/mail/send")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", BuildConfig.EMAIL_API_KEY)
            .addHeader("X-RapidAPI-Host", "rapidprod-sendgrid-v1.p.rapidapi.com")
            .build()

        viewModelScope.launch(Dispatchers.IO){
            //val response = client.newCall(request).execute()
            Log.d("testFP", "sendOtp: $content")
        }
    }

    fun verifyOtp(): Boolean =  if(_inputOtp.value == otp) true else false

    fun changePassword(context: Context){
        viewModelScope.launch(Dispatchers.IO){
            DatabaseRepository(context).updatePassword(_inputEmail.value,_inputPassword.value)
        }
    }
}