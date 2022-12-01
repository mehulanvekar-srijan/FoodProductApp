package com.experiment.foodproductapp.domain.use_case

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if (password.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password cant be empty"
            )
        }
        if (password.length<8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must be 8 characters long"
            )
        }
        val containsLettersAndDigits= password.any{it.isDigit()} && password.any{it.isLetter()}
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least 1 special  character or number"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}