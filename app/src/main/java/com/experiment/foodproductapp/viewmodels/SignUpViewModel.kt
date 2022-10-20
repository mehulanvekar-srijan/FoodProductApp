package com.experiment.foodproductapp.viewmodels

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.User
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.domain.use_case.*
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.states.SignUpFormState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.util.*

class SignUpViewModel(
    private val validateFirstName: ValidateName = ValidateName(),
    private val validateLastName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateConfirmPassword: ValidateConfirmPassword = ValidateConfirmPassword(),
    private val validatePhoneNumber: ValidatePhoneNumber= ValidatePhoneNumber(),
    private val validateDateChange:ValidateName= ValidateName()
) : ViewModel() {
    var state by mutableStateOf(SignUpFormState())

//    val mYear: Int
//    val mMonth: Int
//    val mDay: Int
//
//    // Initializing a Calendar
//    val mCalendar = Calendar.getInstance()
//
//    // Fetching current year, month and day
//    mYear = mCalendar.get(Calendar.YEAR)
//    mMonth = mCalendar.get(Calendar.MONTH)
//    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
//
//    val mDatePickerDialog = DatePickerDialog(
//        context,
//        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
//            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
//        }, mYear, mMonth, mDay
//    )

    private val _passwordVisible = mutableStateOf(false)
    val passwordVisible = _passwordVisible

    private val _confirmPasswordVisible = mutableStateOf(false)
    val confirmPasswordVisible = _confirmPasswordVisible

    private val validationEventChannel= Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: SignupFormEvent) {
        when (event) {
            is SignupFormEvent.FirstNameChanged -> {
                state = state.copy(firstName = event.firstName)
            }
            is SignupFormEvent.LastNameChanged -> {
                state = state.copy(lastName = event.lastName)
            }
            is SignupFormEvent.CalenderChanged -> {
                state = state.copy(date = event.date)
            }
            is SignupFormEvent.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.number)
            }
            is SignupFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is SignupFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is SignupFormEvent.ConfirmPasswordChanged -> {
                state = state.copy(repeatedPassword = event.confirmPassword)
            }
            is SignupFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {

        val firstNameResult = validateFirstName.execute(state.firstName)
        val lastNameResult = validateLastName.execute(state.lastName)
        val dateResult = validateDateChange.execute(state.date)
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult =
            validateConfirmPassword.execute(state.password, state.repeatedPassword)

        val hasError = listOf(
            firstNameResult,
            dateResult,
            phoneNumberResult,
            lastNameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(

                firstNameError = firstNameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage,
                phoneNumberError = phoneNumberResult.errorMessage,
                dateError = dateResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage
            )
            return
        }
        viewModelScope.launch{
            validationEventChannel.send(ValidationEvent.Success)
            Log.d("date",state.date)
            Log.d("phone",state.phoneNumber)
        }
    }

    fun passwordchange() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun confirmpasswordchange() {
        _confirmPasswordVisible.value = !_confirmPasswordVisible.value
    }

    suspend fun navigateOnSucces(context: Context,navHostController: NavHostController) {

        val job = viewModelScope.launch(Dispatchers.IO){
            val user = User()
            user.firstName=state.firstName
            user.lastName=state.lastName
            user.password=state.password
            user.email=state.email
            user.phoneNumber=state.phoneNumber
            user.dob=state.date
            val database = DatabaseRepository(context)
            database.addUser(user)
        }

        job.join()
        navHostController.navigate(Screen.SignInScreen.route){
            popUpTo(Screen.SignUpScreen.route){inclusive=true}
            popUpTo(Screen.SignInScreen.route){inclusive=true}
        }
    }

    sealed class ValidationEvent{
        object Success:ValidationEvent()
    }

}