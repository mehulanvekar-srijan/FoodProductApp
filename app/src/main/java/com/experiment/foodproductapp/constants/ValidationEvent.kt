package com.experiment.foodproductapp.constants


sealed class ValidationEvent{
    object Success: ValidationEvent()
    object Failure : ValidationEvent()
}
