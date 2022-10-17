package com.experiment.foodproductapp.domain.use_case

class ValidateConfirmPassword {
    fun execute(password: String, repeatedPassword: String): SignUpValidationResult {
        if (password!= repeatedPassword) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }
        return SignUpValidationResult(
            successful = true
        )
    }
}