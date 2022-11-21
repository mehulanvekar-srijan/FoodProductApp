package com.experiment.foodproductapp.domain.use_case

class ValidateName {
    fun execute(name: String): ValidationResult {
        if (name.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "This field can't be empty"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}