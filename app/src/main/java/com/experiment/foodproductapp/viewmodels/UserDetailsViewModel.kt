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
import com.experiment.foodproductapp.database.entity.User
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.domain.event.UserDetailsFormEvent
import com.experiment.foodproductapp.domain.use_case.*
import com.experiment.foodproductapp.states.UserDetailsFormState
import kotlinx.coroutines.*

class UserDetailsViewModel(private val validateFirstName: ValidateName = ValidateName(),
                           private val validateLastName: ValidateName = ValidateName(),
                           private val validatePassword: ValidatePassword = ValidatePassword(),
                           private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber(),
                           private val validateDateChange: ValidateName = ValidateName()
) : ViewModel() {



    private val _user: MutableState<User> = mutableStateOf(User())
    val user: State<User> = _user

    var state by mutableStateOf(UserDetailsFormState())

    fun onEvent(context: Context, event: UserDetailsFormEvent) {
        when (event) {
            is UserDetailsFormEvent.FirstNameChanged -> {
                state = state.copy(firstName = event.firstName)
            }
            is UserDetailsFormEvent.LastNameChanged -> {
                state = state.copy(lastName = event.lastName)
            }
            is UserDetailsFormEvent.CalenderChanged -> {
                state = state.copy(date = event.date)
            }
            is UserDetailsFormEvent.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.number)
            }
            is UserDetailsFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is UserDetailsFormEvent.Submit -> {
                submitData(context)
            }
        }
    }

    private fun submitData(context: Context) {
        val firstNameResult = validateFirstName.execute(state.firstName)
        val lastNameResult = validateLastName.execute(state.lastName)
        val dateResult = validateDateChange.execute(state.date)
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            firstNameResult,
            dateResult,
            phoneNumberResult,
            lastNameResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                firstNameError = firstNameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage,
                phoneNumberError = phoneNumberResult.errorMessage,
                dateError = dateResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }

        state = state.copy(
            firstNameError = null,
            lastNameError = null,
            phoneNumberError = null,
            dateError = null,
            passwordError = null
        )

        viewModelScope.launch(Dispatchers.IO) {
            val database = DatabaseRepository(context)
            database.updateUserByEmail(
                state.email,
                state.firstName,
                state.lastName,
                state.date,
                state.password,
                state.phoneNumber
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Updation Successful", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun execute(context: Context, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = DatabaseRepository(context)
            _user.value = database.getUserByEmail(email = email)
            state = state.copy(firstName = _user.value.firstName,
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

    fun initProfilePicture(
        context: Context,
        email:String,
    ) {
        viewModelScope.launch(Dispatchers.IO){
            val imagePath: String? = DatabaseRepository(context).getImagePath(email)
            if(imagePath != null){
                imageUri.value = Uri.parse(imagePath)
                hasImage.value = true
            }
            else hasImage.value = false
            Log.d("testCam", "1 - initProfilePicture: imgPath =  ${imageUri.value}")
        }
    }

    fun updateUserProfilePictureInDatabase(
        context: Context,
        email:String,
        uri: Uri?,
    ){
        viewModelScope.launch(Dispatchers.IO){
            DatabaseRepository(context).updateUserProfilePicture(email,uri.toString())
        }
    }

    fun navigateToRewards(email: String,navHostController: NavHostController){
        navHostController.navigate(Screen.Rewards.routeWithData(email))
    }


    fun logOutUser(email: String,context: Context,navHostController: NavHostController){
        viewModelScope.launch(Dispatchers.IO){
            DatabaseRepository(context).updateLoginStatus(email = email,loggedIn = false)
        }
        navHostController.navigate(Screen.SignInScreen.route){
            popUpTo(Screen.HomeScreen.route) { inclusive = true }
        }
    }
}