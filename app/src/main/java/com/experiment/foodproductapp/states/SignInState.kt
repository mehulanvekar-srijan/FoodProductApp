package com.experiment.foodproductapp.states

data class SignInState(
    val email: String = "",
    val password: String="",
    val emailError: String ?= null,
    val passwordError: String ?= null,
)
