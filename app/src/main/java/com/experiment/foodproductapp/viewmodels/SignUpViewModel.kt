package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.database.entity.User
import com.experiment.foodproductapp.domain.event.SignupFormEvent
import com.experiment.foodproductapp.domain.use_case.*
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.states.SignUpFormState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val databaseRepository: DatabaseRepository,
    private val validateFirstName: ValidateName = ValidateName(),
    private val validateLastName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateConfirmPassword: ValidateConfirmPassword = ValidateConfirmPassword(),
    private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber(),
    private val validateDateChange:ValidateName = ValidateName()
) : ViewModel() {

    init {
        Log.d("testDI", "SignUpViewModel: ${databaseRepository.hashCode()}")
    }

    private val _state = mutableStateOf(SignUpFormState())
    val state = _state

    private val _passwordVisible = mutableStateOf(false)
    val passwordVisible = _passwordVisible

    private val _confirmPasswordVisible = mutableStateOf(false)
    val confirmPasswordVisible = _confirmPasswordVisible

    private val validationEventChannel= Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: SignupFormEvent) {
        when (event) {
            is SignupFormEvent.FirstNameChanged -> {
                _state.value = _state.value.copy(firstName = event.firstName)
            }
            is SignupFormEvent.LastNameChanged -> {
                _state.value = _state.value.copy(lastName = event.lastName)
            }
            is SignupFormEvent.CalenderChanged -> {
                _state.value = _state.value.copy(date = event.date)
            }
            is SignupFormEvent.PhoneNumberChanged -> {
                _state.value = _state.value.copy(phoneNumber = event.number)
            }
            is SignupFormEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is SignupFormEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is SignupFormEvent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(repeatedPassword = event.confirmPassword)
            }
            is SignupFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {

        val firstNameResult = validateFirstName.execute(_state.value.firstName)
        val lastNameResult = validateLastName.execute(_state.value.lastName)
        val dateResult = validateDateChange.execute(_state.value.date)
        val phoneNumberResult = validatePhoneNumber.execute(_state.value.phoneNumber)
        val emailResult = validateEmail.execute(_state.value.email)
        val passwordResult = validatePassword.execute(_state.value.password)
        val repeatedPasswordResult =
            validateConfirmPassword.execute(_state.value.password, _state.value.repeatedPassword)

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
            _state.value = _state.value.copy(
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
            Log.d("date",_state.value.date)
            Log.d("phone",_state.value.phoneNumber)
        }
    }

    fun passwordChange() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun confirmPasswordChange() {
        _confirmPasswordVisible.value = !_confirmPasswordVisible.value
    }

    suspend fun navigateOnSuccess(context: Context, navHostController: NavHostController) {

        var success: Boolean? = null

        val job = viewModelScope.launch(Dispatchers.IO){
            val user = User(
                firstName = _state.value.firstName,
                lastName = _state.value.lastName,
                password = _state.value.password,
                email = _state.value.email,
                phoneNumber = _state.value.phoneNumber,
                dob = _state.value.date,
            )

            success = try {
                databaseRepository.addUser(user)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Registration Successful", Toast.LENGTH_SHORT).show()
                }
                true
            }
            catch (e: android.database.sqlite.SQLiteConstraintException){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Email already registered", Toast.LENGTH_SHORT).show()
                }
                false
            }
        }

        job.join()

        if(success != null && success == true){
            //Update login status
            viewModelScope.launch(Dispatchers.IO){
                databaseRepository.updateLoginStatus(email = _state.value.email,loggedIn = true)
            }

            //Navigate
            navHostController.navigate(Screen.HomeScreen.routeWithData(_state.value.email)){
                popUpTo(Screen.SignInScreen.route){inclusive=true}
            }
        }
    }
}