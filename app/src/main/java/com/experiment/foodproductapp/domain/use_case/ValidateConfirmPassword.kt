package com.experiment.foodproductapp.domain.use_case

class ValidateConfirmPassword {
    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (password!= repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}