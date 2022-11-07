package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    suspend fun isUserRegistered(context: Context, email: String) {
        val deferred: Deferred<Boolean> = viewModelScope.async(Dispatchers.IO){
            val result = DatabaseRepository(context).getUserByEmail(email)
            result != null
        }

        deferred.await()
    }

    fun sendOtp(){
        val otp = (Math.random() * 9000).toInt() + 1000
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaTypeOrNull()
        val content = """{"personalizations": [ { "to": [ { "email": "anvekarmehul@gmail.com" } ], "subject": "OTP" } ], "from": {"email": "noobdeshwar@gmail.com" },"content": [{ "type": "text/plain","value": "Your otp is $otp" }] }"""
        val body = RequestBody.create(mediaType,content)
        val request = Request.Builder()
            .url("https://rapidprod-sendgrid-v1.p.rapidapi.com/mail/send")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", "183c81c00dmsh801338fe53ee411p13ccf8jsna84ec8addb52")
            .addHeader("X-RapidAPI-Host", "rapidprod-sendgrid-v1.p.rapidapi.com")
            .build()

        viewModelScope.launch(Dispatchers.IO){
            //val response = client.newCall(request).execute()
            Log.d("testOTP", "sendOtp: $content")
        }
    }
}