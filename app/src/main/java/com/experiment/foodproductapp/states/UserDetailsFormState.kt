package com.experiment.foodproductapp.states

data class UserDetailsFormState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val email: String = "",
    val password: String = "",
    val passwordError: String? = null,
    val date: String = "",
    val dateError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
)
