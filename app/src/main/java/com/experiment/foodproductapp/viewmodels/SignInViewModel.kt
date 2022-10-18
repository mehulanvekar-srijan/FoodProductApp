package com.experiment.foodproductapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import kotlinx.coroutines.launch

class SignInViewModel() : ViewModel() {
    fun navigate(navHostController: NavHostController) {
            navHostController.navigate(Screen.SignUpScreen.route){
              //  popUpTo(Screen.SignInScreen.route){inclusive=true}
            }
    }
}