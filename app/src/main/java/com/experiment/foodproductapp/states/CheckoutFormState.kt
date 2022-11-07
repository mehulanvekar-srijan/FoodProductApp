package com.experiment.foodproductapp.states

data class CheckoutFormState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String="",
    val pincode: String = "",
    val pincodeError: String? = null,
    val addressLine1: String="",
    val addressLine1Error: String? = null,
    val addressLine2: String="",
    val addressLine2Error: String? = null,
    val city: String="",
    val cityError: String? = null,
    val state: String="",
    val stateError: String? = null,
)
