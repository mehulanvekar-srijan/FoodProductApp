package com.experiment.foodproductapp.domain.event

sealed class ProfileFormEvent{
        data class PhoneNumberChanged(val number:String): ProfileFormEvent()
        data class CalenderChanged(val date:String): ProfileFormEvent()
        data class FirstNameChanged(val firstName:String): ProfileFormEvent()
        data class LastNameChanged(val lastName:String): ProfileFormEvent()
        data class EmailChanged(val email:String): ProfileFormEvent()
        data class PasswordChanged(val password:String): ProfileFormEvent()
        object Submit: ProfileFormEvent()
}
