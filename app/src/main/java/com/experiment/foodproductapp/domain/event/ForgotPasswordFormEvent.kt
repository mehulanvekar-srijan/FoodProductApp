package com.experiment.foodproductapp.domain.event

sealed class ForgotPasswordFormEvent{
    data class EmailChanged(val email:String):ForgotPasswordFormEvent()
    data class PasswordChanged(val password:String):ForgotPasswordFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword:String):ForgotPasswordFormEvent()
    object Set: ForgotPasswordFormEvent()
    object Next: ForgotPasswordFormEvent()
}
