package com.experiment.foodproductapp.viewmodels

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.User
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.states.SignUpFormState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsViewModel : ViewModel() {

    private val _user: MutableState<User> = mutableStateOf(User())
    val user: State<User> = _user

    var state by mutableStateOf(User())

    private val _visibilityBt = mutableStateOf(true)
    val visibilityBt = _visibilityBt

    fun edit(){
        _visibilityBt.value = ! _visibilityBt.value
    }

    fun execute(context: Context, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = DatabaseRepository(context)
            _user.value = database.getUserByEmail(email = email)
        }
    }
}