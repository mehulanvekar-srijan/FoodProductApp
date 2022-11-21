package com.experiment.foodproductapp.domain.use_case

class ValidatePincode {
    fun execute(number: String): ValidationResult {
        if (number.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "This field can't be empty"
            )
        }
        if (number.length<6) {
            return ValidationResult(
                successful = false,
                errorMessage = "Pincode must be of 6 digits"
            )
        }
        if (number.length>6) {
            return ValidationResult(
                successful = false,
                errorMessage = "This is not a valid pincode"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}