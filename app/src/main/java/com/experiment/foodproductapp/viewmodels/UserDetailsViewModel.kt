package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.database.entity.User
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.domain.event.UserDetailsFormEvent
import com.experiment.foodproductapp.domain.use_case.*
import com.experiment.foodproductapp.states.UserDetailsFormState
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class UserDetailsViewModel(
    private val databaseRepository: DatabaseRepository,
    private val validateFirstName: ValidateName = ValidateName(),
    private val validateLastName: ValidateName = ValidateName(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber(),
    private val validateDateChange: ValidateName = ValidateName()
) : ViewModel() {

    init {
        Log.d("testDI", "UserDetailsViewModel: ${databaseRepository.hashCode()}")
    }

    private val _user: MutableState<User> = mutableStateOf(User())

    private val _dialogBox: MutableState<Boolean> = mutableStateOf(false)
    val dialogBox: State<Boolean> = _dialogBox

    private val _passwordVisibility = mutableStateOf(false)
    val passwordVisibility = _passwordVisibility

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _state = mutableStateOf(UserDetailsFormState())
    val state = _state

    fun changeDialogBoxStatus(input: Boolean){
        _dialogBox.value = input
    }

    fun onEvent(event: UserDetailsFormEvent) {
        when (event) {
            is UserDetailsFormEvent.FirstNameChanged -> {
                _state.value = _state.value.copy(firstName = event.firstName)
            }
            is UserDetailsFormEvent.LastNameChanged -> {
                _state.value = _state.value.copy(lastName = event.lastName)
            }
            is UserDetailsFormEvent.CalenderChanged -> {
                _state.value = _state.value.copy(date = event.date)
            }
            is UserDetailsFormEvent.PhoneNumberChanged -> {
                _state.value = _state.value.copy(phoneNumber = event.number)
            }
            is UserDetailsFormEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is UserDetailsFormEvent.Submit -> {
                submitData()
            }
            else -> {}
        }
    }

    private fun submitData() {
        val firstNameResult = validateFirstName.execute(_state.value.firstName)
        val lastNameResult = validateLastName.execute(_state.value.lastName)
        val dateResult = validateDateChange.execute(_state.value.date)
        val phoneNumberResult = validatePhoneNumber.execute(_state.value.phoneNumber)
        val passwordResult = validatePassword.execute(_state.value.password)

        val hasError = listOf(
            firstNameResult,
            dateResult,
            phoneNumberResult,
            lastNameResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            _state.value = _state.value.copy(
                firstNameError = firstNameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage,
                phoneNumberError = phoneNumberResult.errorMessage,
                dateError = dateResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }

        _state.value = _state.value.copy(
            firstNameError = null,
            lastNameError = null,
            phoneNumberError = null,
            dateError = null,
            passwordError = null
        )
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.updateUserByEmail(
                _state.value.email,
                _state.value.firstName,
                _state.value.lastName,
                _state.value.date,
                _state.value.password,
                _state.value.phoneNumber
            )

            validationEventChannel.send(ValidationEvent.Success)
        }
    }

//    suspend fun onSuccess():Boolean {
//        val job =viewModelScope.launch(Dispatchers.IO) {
//            databaseRepository.updateUserByEmail(
//                _state.value.email,
//                _state.value.firstName,
//                _state.value.lastName,
//                _state.value.date,
//                _state.value.password,
//                _state.value.phoneNumber
//            )
//            withContext(Dispatchers.Main) {
//                Toast.makeText(context, "Updation Successful", Toast.LENGTH_LONG).show()
//            }
//        }
//        job.join()
//
//        return true
//    }

    fun execute(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = databaseRepository.getUserByEmail(email = email)
            _state.value = _state.value.copy(
                firstName = _user.value.firstName,
                lastName = _user.value.lastName,
                email = _user.value.email,
                password = _user.value.password,
                date = _user.value.dob,
                phoneNumber = _user.value.phoneNumber
            )
        }
    }

    val hasImage = mutableStateOf(false)
    val imageUri = mutableStateOf<Uri?>(null)

    fun initProfilePicture(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val imagePath: String? = databaseRepository.getImagePath(email)
            if (imagePath != null) {
                imageUri.value = Uri.parse(imagePath)
                hasImage.value = true
            } else hasImage.value = false
            Log.d("testCam", "1 - initProfilePicture: imgPath =  ${imageUri.value}")
        }
    }

    fun updateUserProfilePictureInDatabase(
        email: String,
        uri: Uri?,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.updateUserProfilePicture(email, uri.toString())
        }
    }

    //navigation moved to view
//    fun navigateToRewards(email: String, navHostController: NavHostController) {
//        navHostController.navigate(Screen.Rewards.routeWithData(email))
//    }

    fun logOutUser(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.updateLoginStatus(email = email, loggedIn = false)
            validationEventChannel.send(ValidationEvent.Failure)
        }
//        navHostController.navigate(Screen.SignInScreen.route) {
//            popUpTo(Screen.HomeScreen.route) { inclusive = true }
//        }
    }
}