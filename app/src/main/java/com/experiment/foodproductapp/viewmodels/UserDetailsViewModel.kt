package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.User
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.domain.event.ProfileFormEvent
import com.experiment.foodproductapp.states.ProfileFormState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailsViewModel : ViewModel() {

    private val _originalEmail = mutableStateOf("")
    val originalEmail = _originalEmail

    private val _user: MutableState<User> = mutableStateOf(User())
    val user: State<User> = _user

    var state by mutableStateOf(ProfileFormState())

    fun onEvent(context: Context, event: ProfileFormEvent) {
        when (event) {
            is ProfileFormEvent.FirstNameChanged -> {
                _user.value = _user.value.copy(firstName = event.firstName)
            }
            is ProfileFormEvent.LastNameChanged -> {
                _user.value = _user.value.copy(lastName = event.lastName)
            }
            is ProfileFormEvent.CalenderChanged -> {
                _user.value = _user.value.copy(dob = event.date)
            }
            is ProfileFormEvent.PhoneNumberChanged -> {
                _user.value = _user.value.copy(phoneNumber = event.number)
            }
            is ProfileFormEvent.EmailChanged -> {
                _user.value = _user.value.copy(email = event.email)
            }
            is ProfileFormEvent.PasswordChanged -> {
                _user.value = _user.value.copy(password = event.password)
            }
            is ProfileFormEvent.Submit -> {
                submitData(context)
            }
        }
    }

    private fun submitData(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = DatabaseRepository(context)
            database.updateUserByEmail(
                originalEmail.value,
                user.value.firstName,
                user.value.lastName,
                user.value.email,
                user.value.dob,
                user.value.password,
                user.value.phoneNumber
            )
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Updation Successful", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun execute(context: Context, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = DatabaseRepository(context)
            _user.value = database.getUserByEmail(email = email)
            _originalEmail.value = _user.value.email
        }
    }
}