package com.experiment.foodproductapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.domain.event.SignInFormEvent
import com.experiment.foodproductapp.domain.use_case.ValidateEmail
import com.experiment.foodproductapp.states.SignInState
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

    fun OnEvent(event: SignInFormEvent) {
        when (event) {
            is SignInFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is SignInFormEvent.Login -> {
                loginUser()
            }
        }
    }

    private fun loginUser() {
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
        }
    }

    sealed class ValidationEvent {
        object Succcess : ValidationEvent()
    }
}