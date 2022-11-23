package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.BuildConfig
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.domain.event.ForgotPasswordFormEvent
import com.experiment.foodproductapp.domain.use_case.ValidateConfirmPassword
import com.experiment.foodproductapp.domain.use_case.ValidateEmail
import com.experiment.foodproductapp.domain.use_case.ValidatePassword
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.states.ForgotPasswordState
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


class ForgotPasswordViewModel(
    private val databaseRepository: DatabaseRepository,
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateConfirmPassword: ValidateConfirmPassword = ValidateConfirmPassword(),
) : ViewModel() {

    init {
        Log.d("testDI", "ForgotPasswordViewModel: ${databaseRepository.hashCode()}")
    }

    private var otp = ""

    val showEnterEmail = mutableStateOf(true)
    val showEnterOTP = mutableStateOf(false)
    val showEnterPasswordTextField = mutableStateOf(false)

    private val _passwordVisible = mutableStateOf(false)
    val passwordVisible = _passwordVisible

    fun passwordVisibilityChange() {
        _passwordVisible.value = !_passwordVisible.value
    }

    private val _confirmPasswordVisible = mutableStateOf(false)
    val confirmPasswordVisible = _confirmPasswordVisible

    fun confirmPasswordVisibilityChange() {
        _confirmPasswordVisible.value = !_confirmPasswordVisible.value
    }

    val status = mutableStateOf(false)

    var state by mutableStateOf(ForgotPasswordState())

    fun onEvent(event: ForgotPasswordFormEvent, context: Context) {
        when (event) {
            is ForgotPasswordFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is ForgotPasswordFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is ForgotPasswordFormEvent.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = event.confirmPassword)
            }
            is ForgotPasswordFormEvent.Next -> {
                validateUser(context = context)
            }
            is ForgotPasswordFormEvent.Set -> {
                validatePassword(context)
            }
        }
    }

    private fun validatePassword(context: Context) {
        val passwordResult = validatePassword.execute(state.password)
        val confirmPasswordResult =
            validateConfirmPassword.execute(state.password, state.confirmPassword)

        val passwordHasNoError = passwordResult.successful
        if (passwordHasNoError) {

            val confirmPasswordHasNoError = confirmPasswordResult.successful

            if (confirmPasswordHasNoError) {
                changePassword(context)
            } else {
                Toast.makeText(
                    context, confirmPasswordResult.errorMessage, Toast.LENGTH_SHORT
                ).show()
            }

        } else {
            Toast.makeText(
                context, passwordResult.errorMessage, Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun validateUser(context: Context) {
        val emailResult = validateEmail.execute(state.email)

        val hasNoError = emailResult.successful

        if (hasNoError) {
            viewModelScope.launch {
                val status = isUserRegistered()
                if (status) {
                    showEnterOTP.value = true
                    showEnterEmail.value = false
                    sendOtp()
                } else {
                    Toast.makeText(
                        context,
                        R.string.email_not_registered_string,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {

            Toast.makeText(
                context, emailResult.errorMessage, Toast.LENGTH_SHORT
            ).show()
        }
    }


    private val _inputOtp = mutableStateOf("")

    fun setOtp(input: String) {
        _inputOtp.value = input
    }

    private suspend fun isUserRegistered(): Boolean {

        Log.d("testFP", "isUserRegistered: ${state.email}")

        val deferred: Deferred<Boolean> = viewModelScope.async(Dispatchers.IO) {
            val result = databaseRepository.getUserByEmail(state.email)
            Log.d("testFP", "deferred: $result")
            if (result == null) false else true
        }

        return deferred.await()
    }

    private fun sendOtp() {
        otp = ((Math.random() * 9000).toInt() + 1000).toString()
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaTypeOrNull()
        val content =
            """{"personalizations": [ { "to": [ { "email": "${state.email}" } ], "subject": "OTP" } ], "from": {"email": "mehul.anvekar@srijan.net" },"content": [{ "type": "text/plain","value": "Your otp is $otp" }] }"""
        val body = RequestBody.create(mediaType, content)
        val request = Request.Builder()
            .url("https://rapidprod-sendgrid-v1.p.rapidapi.com/mail/send")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", BuildConfig.EMAIL_API_KEY)
            .addHeader("X-RapidAPI-Host", "rapidprod-sendgrid-v1.p.rapidapi.com")
            .build()

        viewModelScope.launch(Dispatchers.IO) {
//            val response = client.newCall(request).execute()
            Log.d("testFP", "sendOtp: $otp content=$content")
        }
    }

    fun verifyOtp(): Boolean = if (_inputOtp.value == otp) true else false

    private fun changePassword(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.updatePassword(state.email, state.password)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Password updated",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}