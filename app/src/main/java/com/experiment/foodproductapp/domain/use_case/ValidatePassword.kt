package com.experiment.foodproductapp.domain.use_case

import android.util.Patterns

class ValidatePassword {
    fun execute(password: String): SignUpValidationResult {
        if (password.length<8) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of atleast 8 characters"
            )
        }
        val containsLettersAndDigits= password.any{it.isDigit()} && password.any{it.isLetter()}
        if (!containsLettersAndDigits) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }
        return SignUpValidationResult(
            successful = true
        )
    }
}