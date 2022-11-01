package com.experiment.foodproductapp.domain.event

sealed class UserDetailsFormEvent{
        data class PhoneNumberChanged(val number:String): UserDetailsFormEvent()
        data class CalenderChanged(val date:String): UserDetailsFormEvent()
        data class FirstNameChanged(val firstName:String): UserDetailsFormEvent()
        data class LastNameChanged(val lastName:String): UserDetailsFormEvent()
        data class EmailChanged(val email:String): UserDetailsFormEvent()
        data class PasswordChanged(val password:String): UserDetailsFormEvent()
        object Submit: UserDetailsFormEvent()
}
