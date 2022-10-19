package com.experiment.foodproductapp.domain.use_case

class ValidatePhoneNumber {
    fun execute(number: String): SignUpValidationResult {
        if (number.isEmpty()) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "This field can't be empty"
            )
        }
        if (number.length<10) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "The phone number must be of atleast 10 digits"
            )
        }
        if (number.length>10) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "This is not a valid phone number"
            )
        }

        return SignUpValidationResult(
            successful = true
        )
    }
}