package com.experiment.foodproductapp.domain.event

sealed class SignInFormEvent {
    data class EmailChanged(val email: String): SignInFormEvent()
    object Login: SignInFormEvent()
}
