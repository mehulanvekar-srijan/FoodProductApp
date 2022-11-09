package com.experiment.foodproductapp.domain.use_case

class ValidatePincode {
    fun execute(number: String): SignUpValidationResult {
        if (number.isEmpty()) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "This field can't be empty"
            )
        }
        if (number.length<6) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "Pincode must be of 6 digits"
            )
        }
        if (number.length>6) {
            return SignUpValidationResult(
                successful = false,
                errorMessage = "This is not a valid pincode"
            )
        }

        return SignUpValidationResult(
            successful = true
        )
    }
}