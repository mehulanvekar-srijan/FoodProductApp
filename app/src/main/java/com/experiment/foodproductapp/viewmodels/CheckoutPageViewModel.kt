package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.entity.User
import com.experiment.foodproductapp.domain.event.CheckoutFormEvent
import com.experiment.foodproductapp.domain.use_case.ValidateName
import com.experiment.foodproductapp.domain.use_case.ValidatePincode
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.states.CheckoutFormState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CheckoutPageViewModel(
    private val validatePinCode: ValidatePincode = ValidatePincode(),
    private val validateAddressLine1: ValidateName = ValidateName(),
    private val validateAddressLine2: ValidateName = ValidateName(),
    private val validateCity: ValidateName = ValidateName(),
    private val validateState: ValidateName = ValidateName()
) : ViewModel() {
    private val _user: MutableState<User> = mutableStateOf(User())
    val user: State<User> = _user

    private val validationEventChannel= Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _sum = mutableStateOf(0)
    val sum = _sum

    var state by mutableStateOf(CheckoutFormState())

    fun fetchUserDetails(context: Context,email:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = DatabaseRepository(context)
            _user.value = database.getUserByEmail(email = email)
            state = state.copy(firstName = _user.value.firstName,
                email=_user.value.email,
                lastName = _user.value.lastName,
                phoneNumber = _user.value.phoneNumber
            )
            if(_user.value.addressLine1!=null)
            {
                state = state.copy(pincode = _user.value.pincode.toString(),
                    addressLine1 =_user.value.addressLine1.toString(),
                    addressLine2 = _user.value.addressLine2.toString(),
                    city = _user.value.city.toString(),
                    state = _user.value.state.toString()
                )
            }
        }
    }

    fun onEvent(event: CheckoutFormEvent) {
        when (event) {
            is CheckoutFormEvent.PinCodeChanged -> {
                state = state.copy(pincode = event.pincode)
            }
            is CheckoutFormEvent.AddressLine1Changed -> {
                state = state.copy(addressLine1 = event.add1)
            }
            is CheckoutFormEvent.AddressLine2Changed -> {
                state = state.copy(addressLine2 = event.add2)
            }
            is CheckoutFormEvent.CityChanged -> {
                state = state.copy(city = event.city)
            }
            is CheckoutFormEvent.StateChanged -> {
                state = state.copy(state = event.state)
            }
            is CheckoutFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val pinCodeResult = validatePinCode.execute(state.pincode)
        val addressLine1Result = validateAddressLine1.execute(state.addressLine1)
        val addressLine2Result = validateAddressLine2.execute(state.addressLine2)
        val cityResult = validateCity.execute(state.city)
        val stateResult = validateState.execute(state.state)

        val hasError = listOf(
            pinCodeResult,
            addressLine1Result,
            addressLine2Result,
            cityResult,
            stateResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                pincodeError = pinCodeResult.errorMessage,
                addressLine1Error = addressLine1Result.errorMessage,
                addressLine2Error = addressLine2Result.errorMessage,
                cityError = cityResult.errorMessage,
                stateError = stateResult.errorMessage
            )
            return
        }

        state = state.copy(
            pincodeError = null,
            addressLine1Error = null,
            addressLine2Error = null,
            cityError = null,
            stateError = null
        )
        viewModelScope.launch{
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    suspend fun navigateOnSuccess(context: Context, navHostController: NavHostController, points: Int) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            val database = DatabaseRepository(context)
            database.updateAddressByEmail(
                state.email,
                state.pincode,
                state.addressLine1,
                state.addressLine2,
                state.city,
                state.state
            )
        }
        job.join()

        Log.d("testredeemAmount", "CheckoutPageViewModel: email=${state.email} , finalSum=${sum.value * 100} , points=${points}")

        navHostController.navigate(
            Screen.PaymentScreen.routeWithData(
                email = state.email,
                phoneNumber = state.phoneNumber,
                sum = sum.value*100,
                points = points
            )
        )
        Log.d("TAG",(sum.value*100).toString())
    }

    sealed class ValidationEvent{
        object Success:ValidationEvent()
    }
}