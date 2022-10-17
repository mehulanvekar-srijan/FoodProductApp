package com.experiment.foodproductapp.domain.use_case

import android.util.Patterns

class ValidateEmail {
    fun execute(email: String): SignUpValidationResult {
        if (email.isBlank()) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "Thats not a valid email"
            )
        }
        return SignUpValidationResult(
            successful = true
        )
    }
}