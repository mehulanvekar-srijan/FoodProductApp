package com.experiment.foodproductapp.domain.use_case

class EmptyPassword {
    fun execute(password: String): SignUpValidationResult {
        if (password.isEmpty()) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "The password cant be empty"
            )
        }
        return SignUpValidationResult(
            successful = true
        )
    }
}