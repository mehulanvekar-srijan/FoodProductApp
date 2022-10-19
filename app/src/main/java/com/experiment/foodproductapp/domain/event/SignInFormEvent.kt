package com.experiment.foodproductapp.domain.event

sealed class SignInFormEvent {
    data class EmailChanged(val email: String): SignInFormEvent()
    data class PasswordChanged(val password: String): SignInFormEvent()
    object Login: SignInFormEvent()
}
