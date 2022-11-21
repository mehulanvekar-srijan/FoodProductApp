package com.experiment.foodproductapp.domain.use_case

class EmptyPassword {
    fun execute(password: String): ValidationResult {
        if (password.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password cant be empty"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}