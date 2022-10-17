package com.experiment.foodproductapp.domain.use_case

class ValidateName {
    fun execute(name: String): SignUpValidationResult {
        if (name.isEmpty()) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "This field can't be empty"
            )
        }

        return SignUpValidationResult(
            successful = true
        )
    }
}