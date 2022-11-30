package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.database.entity.User
import com.experiment.foodproductapp.domain.event.SignInFormEvent
import com.experiment.foodproductapp.domain.use_case.EmptyPassword
import com.experiment.foodproductapp.domain.use_case.ValidateEmail
import com.experiment.foodproductapp.domain.use_case.ValidationResult
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.states.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(
    private val databaseRepository: DatabaseRepository,
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: EmptyPassword = EmptyPassword(),
) : ViewModel() {

    private val _state = mutableStateOf(SignInState())
    val state = _state

    private val _error = mutableStateOf(false)
    val error = _error

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        Log.d("testDI", "SignInViewModel: ${databaseRepository.hashCode()}")
    }

    private val _passwordVisibility = mutableStateOf(false)
    val passwordVisibility = _passwordVisibility

    //moved navigation to view
//    fun navigate(navHostController: NavHostController,route: String) {
//        when(route){
//            Screen.SignUpScreen.route -> { navHostController.navigate(Screen.SignUpScreen.route) }
//            Screen.ForgotPassword.route -> { navHostController.navigate(Screen.ForgotPassword.route) }
//        }
//    }

    fun onEvent(event: SignInFormEvent) {
        when (event) {
            is SignInFormEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is SignInFormEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is SignInFormEvent.Login -> {
                loginUser()
            }
        }
    }

    private fun loginUser() {
        val emailResult = validateEmail.execute(_state.value.email)
        val passwordResult = validatePassword.execute(_state.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            _state.value = _state.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            Log.d("passwordEroor", "loginUser: ${passwordResult.errorMessage}")
            return
        } else {
            _state.value = _state.value.copy(emailError = null)
            _state.value = _state.value.copy(passwordError = null)
            viewModelScope.launch(Dispatchers.IO) {

                val user: User?
                user = databaseRepository.getUserByEmail(_state.value.email)

                if (user != null) {
                    if (user.email == _state.value.email && user.password == _state.value.password) {
                        databaseRepository.updateLoginStatus(
                            email = _state.value.email,
                            loggedIn = true
                        )
                        validationEventChannel.send(ValidationEvent.Success)

//                        withContext(Dispatchers.Main) {
//
//                            Toast.makeText(context, "log in successfull", Toast.LENGTH_LONG).show()
//
//                            navHostController.navigate(Screen.HomeScreen.routeWithData(user.email)) {
//                                popUpTo(Screen.SignInScreen.route) { inclusive = true }
//                            }
//                        }

                    } else {
                        validationEventChannel.send(ValidationEvent.Failure)
                        _error.value = false
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(
//                                context,
//                                "incorrect email or password",
//                                Toast.LENGTH_LONG
//                            )
//                                .show()
//                        }
                    }
                } else {
                    validationEventChannel.send(ValidationEvent.Failure)
                    _error.value = true
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(context, "incorrect email or password", Toast.LENGTH_LONG)
//                            .show()
//                    }
                }
            }
        }
    }

}