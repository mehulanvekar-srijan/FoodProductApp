package com.experiment.foodproductapp.domain.event

sealed class CheckoutFormEvent{
    data class PinCodeChanged(val pincode:String): CheckoutFormEvent()
    data class AddressLine1Changed(val add1:String): CheckoutFormEvent()
    data class AddressLine2Changed(val add2:String): CheckoutFormEvent()
    data class CityChanged(val city:String): CheckoutFormEvent()
    data class StateChanged(val state:String): CheckoutFormEvent()
    object Submit: CheckoutFormEvent()
}
