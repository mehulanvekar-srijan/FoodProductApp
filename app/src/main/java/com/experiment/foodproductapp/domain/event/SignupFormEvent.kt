package com.experiment.foodproductapp.domain.event

sealed class SignupFormEvent{
    data class FirstNameChanged(val firstName:String):SignupFormEvent()
    data class LastNameChanged(val lastName:String):SignupFormEvent()
    data class EmailChanged(val email:String):SignupFormEvent()
    data class PasswordChanged(val password:String):SignupFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword:String):SignupFormEvent()
    object Submit: SignupFormEvent()
}
