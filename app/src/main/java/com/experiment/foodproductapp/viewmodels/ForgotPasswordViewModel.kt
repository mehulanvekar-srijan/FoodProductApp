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
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.domain.event.ForgotPasswordFormEvent
import com.experiment.foodproductapp.domain.use_case.ValidateConfirmPassword
import com.experiment.foodproductapp.domain.use_case.ValidateEmail
import com.experiment.foodproductapp.domain.use_case.ValidatePassword
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.states.ForgotPasswordState
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.MediaType
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

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val otp = mutableStateOf("")

    private val _state = mutableStateOf(ForgotPasswordState())
    val state = _state

    private val _showEnterEmail = mutableStateOf(true)
    val showEnterEmail = _showEnterEmail

    private val _showEnterOTP = mutableStateOf(false)
    val showEnterOTP = _showEnterOTP

    private val _showEnterPasswordTextField = mutableStateOf(false)
    val showEnterPasswordTextField = _showEnterPasswordTextField

    private val _passwordVisible = mutableStateOf(false)
    val passwordVisible = _passwordVisible

    private val _confirmPasswordVisible = mutableStateOf(false)
    val confirmPasswordVisible = _confirmPasswordVisible

    private val _inputOtp = mutableStateOf("")

    fun passwordVisibilityChange() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun confirmPasswordVisibilityChange() {
        _confirmPasswordVisible.value = !_confirmPasswordVisible.value
    }

    fun onEvent(event: ForgotPasswordFormEvent) {
        when (event) {
            is ForgotPasswordFormEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is ForgotPasswordFormEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is ForgotPasswordFormEvent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(confirmPassword = event.confirmPassword)
            }
            is ForgotPasswordFormEvent.Next -> {
                validateUser()
            }
            is ForgotPasswordFormEvent.Set -> {
                validatePassword()
            }
        }
    }

    private fun validatePassword() {
        val passwordResult = validatePassword.execute(_state.value.password)
        val confirmPasswordResult =
            validateConfirmPassword.execute(_state.value.password, _state.value.confirmPassword)

        val passwordHasNoError = passwordResult.successful
        if (passwordHasNoError) {

            val confirmPasswordHasNoError = confirmPasswordResult.successful

            if (confirmPasswordHasNoError) {
                changePassword()
            } else {
                _state.value = _state.value.copy(passwordError = confirmPasswordResult.errorMessage)
                viewModelScope.launch {
                    validationEventChannel.send(ValidationEvent.Failure)
                }
            }

        } else {
            _state.value = _state.value.copy(passwordError = passwordResult.errorMessage)
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Failure)
            }
        }
    }

    private fun validateUser() {
        val emailResult = validateEmail.execute(_state.value.email)

        val hasNoError = emailResult.successful

        if (hasNoError) {
            viewModelScope.launch {
                val status = isUserRegistered()
                if (status) {
                    _showEnterOTP.value = true
                    _showEnterEmail.value = false
                    sendOtp()
                } else {
                    validationEventChannel.send(ValidationEvent.Success)
                }
            }
        } else {
            _state.value = _state.value.copy(emailError = emailResult.errorMessage)
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Failure)
            }
        }
    }

    fun setOtp(input: String) {
        _inputOtp.value = input
    }

    private suspend fun isUserRegistered(): Boolean {

        Log.d("testFP", "isUserRegistered: ${_state.value.email}")

        val deferred: Deferred<Boolean> = viewModelScope.async(Dispatchers.IO) {
            val result = databaseRepository.getUserByEmail(_state.value.email)
            Log.d("testFP", "deferred: $result")
            if (result == null) false else true
        }

        return deferred.await()
    }

    private fun sendOtp() {
        otp.value = ((Math.random() * 9000).toInt() + 1000).toString()
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaTypeOrNull()
        val content =
            """{"personalizations": [ { "to": [ { "email": "${_state.value.email}" } ], "subject": "OTP" } ], "from": { "email": "sahil.deosekar@srijan.net" },"content": [{ "type": "text/plain","value": "Your otp is ${otp.value}" }] }"""
        val body = RequestBody.create(mediaType, content)
        val request = Request.Builder()
            .url("https://rapidprod-sendgrid-v1.p.rapidapi.com/mail/send")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", BuildConfig.EMAIL_API_KEY)
            .addHeader("X-RapidAPI-Host", "rapidprod-sendgrid-v1.p.rapidapi.com")
            .build()

        viewModelScope.launch(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            Log.d("testFP", "sendOtp: ${otp.value} content=$content")
            Log.d("testFP", "sendOtp: response=$response")
            //response.body?.close()
        }
    }

    fun verifyOtp(): Boolean = (_inputOtp.value == otp.value)

    private fun changePassword() {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.updatePassword(_state.value.email, _state.value.password)
            validationEventChannel.send(ValidationEvent.Success)
//            withContext(Dispatchers.Main) {
//                Toast.makeText(
//                    context,
//                    "Password updated",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
        }
    }
}