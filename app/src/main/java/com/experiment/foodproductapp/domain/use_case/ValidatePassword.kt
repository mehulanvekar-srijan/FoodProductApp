package com.experiment.foodproductapp.domain.use_case

import android.util.Patterns

class ValidatePassword {
    fun execute(password: String): SignUpValidationResult {
        if (password.length<8) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "The password must be 8 characters long"
            )
        }
        val containsLettersAndDigits= password.any{it.isDigit()} && password.any{it.isLetter()}
        if (!containsLettersAndDigits) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least 1 letter & digit"
            )
        }
        return SignUpValidationResult(
            successful = true
        )
    }
}