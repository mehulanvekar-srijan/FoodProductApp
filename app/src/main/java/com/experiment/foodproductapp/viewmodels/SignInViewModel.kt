package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.User
import com.experiment.foodproductapp.domain.event.SignInFormEvent
import com.experiment.foodproductapp.domain.use_case.ValidateEmail
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.states.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val validateEmail: ValidateEmail = ValidateEmail()) : ViewModel() {
    var state by mutableStateOf(SignInState())

    private val validationEmailChannel = Channel<ValidationEvent>()
    val validationEmail = validationEmailChannel.receiveAsFlow()

    fun navigate(navHostController: NavHostController) {
        navHostController.navigate(Screen.SignUpScreen.route) {
            //  popUpTo(Screen.SignInScreen.route){inclusive=true}
        }
    }

    fun OnEvent(context: Context,event: SignInFormEvent) {
        when (event) {
            is SignInFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is SignInFormEvent.PasswordChanged -> {
                state = state.copy(email = event.password)
            }
            is SignInFormEvent.Login -> {
                loginUser(context)
            }
        }
    }

    private fun loginUser(context: Context) {



        val emailResult = validateEmail.execute(state.email)
        val hasError = listOf(emailResult).any {
            !it.successful
        }
        if (hasError) {
            state = state.copy(emailError = emailResult.errorMessage)
            return
        }
        viewModelScope.launch {
            validationEmailChannel.send(ValidationEvent.Succcess)
            val database = DatabaseRepository(context)
            val user = database.getUserByEmail(state.email)
            Log.d("testRom", "navigateOnSucces: $user")
        }
    }

    sealed class ValidationEvent {
        object Succcess : ValidationEvent()
    }
}