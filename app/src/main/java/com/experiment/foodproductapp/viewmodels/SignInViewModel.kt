package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.User
import com.experiment.foodproductapp.domain.event.SignInFormEvent
import com.experiment.foodproductapp.domain.use_case.EmptyPassword
import com.experiment.foodproductapp.domain.use_case.ValidateEmail
import com.experiment.foodproductapp.domain.use_case.ValidatePassword
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.states.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: EmptyPassword = EmptyPassword()
) : ViewModel() {
    var state by mutableStateOf(SignInState())

    private val _passwordVisibility =  mutableStateOf(false)
    val passwordVisibility = _passwordVisibility

    fun navigate(navHostController: NavHostController,route: String) {
        when(route){
            Screen.SignUpScreen.route -> { navHostController.navigate(Screen.SignUpScreen.route) }
            Screen.ForgotPassword.route -> { navHostController.navigate(Screen.ForgotPassword.route) }
        }
    }

    fun onEvent(context: Context, event: SignInFormEvent, navHostController: NavHostController) {
        when (event) {
            is SignInFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is SignInFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is SignInFormEvent.Login -> {
                loginUser(context,navHostController)
            }
        }
    }

    private fun loginUser(context: Context,navHostController: NavHostController) {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            Log.d("passwordEroor", "loginUser: ${passwordResult.errorMessage}")
            return
        } else {
            state = state.copy(emailError = null)
            state = state.copy(passwordError = null)
        }

        viewModelScope.launch(Dispatchers.IO) {

            val user : User?
            val database = DatabaseRepository(context)
            user = database.getUserByEmail(state.email)

            if (user != null) {
                if (user.email == state.email && user.password == state.password ) {

                    withContext(Dispatchers.Main) {

                        Toast.makeText(context, "log in successfull", Toast.LENGTH_LONG).show()

                        navHostController.navigate(Screen.UserDetails.routeWithDate(user.email)) {
                            popUpTo(Screen.SignInScreen.route) { inclusive = true }
                        }
                    }

                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "incorrect email or password", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "incorrect email or password", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    sealed class ValidationEvent {
        object Succcess : ValidationEvent()
    }
}