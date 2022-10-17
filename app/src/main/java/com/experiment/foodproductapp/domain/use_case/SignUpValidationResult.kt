package com.experiment.foodproductapp.domain.use_case

data class SignUpValidationResult(
    val successful: Boolean,
    val errorMessage: String?= null
)
