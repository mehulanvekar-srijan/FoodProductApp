package com.experiment.foodproductapp.domain.use_case

class ValidatePhoneNumber {
    fun execute(number: String): ValidationResult {
        if (number.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "This field can't be empty"
            )
        }
        if (number.length<10) {
            return ValidationResult(
                successful = false,
                errorMessage = "The phone number must be of atleast 10 digits"
            )
        }
        if (number.length>10) {
            return ValidationResult(
                successful = false,
                errorMessage = "This is not a valid phone number"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}