package com.experiment.foodproductapp.states

data class ForgotPasswordState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)
