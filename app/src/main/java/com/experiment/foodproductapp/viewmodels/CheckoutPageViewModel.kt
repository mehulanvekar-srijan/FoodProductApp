package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
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
    private val databaseRepository: DatabaseRepository,
    private val validatePinCode: ValidatePincode = ValidatePincode(),
    private val validateAddressLine1: ValidateName = ValidateName(),
    private val validateAddressLine2: ValidateName = ValidateName(),
    private val validateCity: ValidateName = ValidateName(),
    private val validateState: ValidateName = ValidateName()
) : ViewModel() {

    init {
        Log.d("testDI", "CheckoutPageViewModel: ${databaseRepository.hashCode()}")
    }

    private val _user: MutableState<User> = mutableStateOf(User())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _sum = mutableStateOf(0)
    val sum = _sum

    private val _state = mutableStateOf(CheckoutFormState())
    val state = _state

    fun fetchUserDetails(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = databaseRepository.getUserByEmail(email = email)
            _state.value = _state.value.copy(
                firstName = _user.value.firstName,
                email = _user.value.email,
                lastName = _user.value.lastName,
                phoneNumber = _user.value.phoneNumber
            )
            if (_user.value.addressLine1 != null) {
                _state.value = _state.value.copy(
                    pincode = _user.value.pincode.toString(),
                    addressLine1 = _user.value.addressLine1.toString(),
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
                _state.value = _state.value.copy(pincode = event.pincode)
            }
            is CheckoutFormEvent.AddressLine1Changed -> {
                _state.value = _state.value.copy(addressLine1 = event.add1)
            }
            is CheckoutFormEvent.AddressLine2Changed -> {
                _state.value = _state.value.copy(addressLine2 = event.add2)
            }
            is CheckoutFormEvent.CityChanged -> {
                _state.value = _state.value.copy(city = event.city)
            }
            is CheckoutFormEvent.StateChanged -> {
                _state.value = _state.value.copy(state = event.state)
            }
            is CheckoutFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val pinCodeResult = validatePinCode.execute(_state.value.pincode)
        val addressLine1Result = validateAddressLine1.execute(_state.value.addressLine1)
        val addressLine2Result = validateAddressLine2.execute(_state.value.addressLine2)
        val cityResult = validateCity.execute(_state.value.city)
        val stateResult = validateState.execute(_state.value.state)

        val hasError = listOf(
            pinCodeResult,
            addressLine1Result,
            addressLine2Result,
            cityResult,
            stateResult
        ).any { !it.successful }

        if (hasError) {
            _state.value = _state.value.copy(
                pincodeError = pinCodeResult.errorMessage,
                addressLine1Error = addressLine1Result.errorMessage,
                addressLine2Error = addressLine2Result.errorMessage,
                cityError = cityResult.errorMessage,
                stateError = stateResult.errorMessage
            )
            return
        }

        _state.value = _state.value.copy(
            pincodeError = null,
            addressLine1Error = null,
            addressLine2Error = null,
            cityError = null,
            stateError = null
        )
        viewModelScope.launch(Dispatchers.IO) {
            val success = try {
            databaseRepository.updateAddressByEmail(
                _state.value.email,
                _state.value.pincode,
                _state.value.addressLine1,
                _state.value.addressLine2,
                _state.value.city,
                _state.value.state
            )
                true
            }catch (e: android.database.sqlite.SQLiteConstraintException) {
                false
            }
            if(success) {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

//    suspend fun onSuccess():Boolean {
//        val job = viewModelScope.launch(Dispatchers.IO) {
//            databaseRepository.updateAddressByEmail(
//                _state.value.email,
//                _state.value.pincode,
//                _state.value.addressLine1,
//                _state.value.addressLine2,
//                _state.value.city,
//                _state.value.state
//            )
//        }
//        job.join()
//
//        return true

//        Log.d(
//            "testredeemAmount",
//            "CheckoutPageViewModel: email=${_state.value.email} , finalSum=${sum.value * 100} , points=${points}"
//        )

//        navHostController.navigate(
//            Screen.PaymentScreen.routeWithData(
//                email = _state.value.email,
//                phoneNumber = _state.value.phoneNumber,
//                sum = _sum.value * 100,
//                points = points
//            )
//        )
//        Log.d("TAG", (sum.value * 100).toString())
//    }
}