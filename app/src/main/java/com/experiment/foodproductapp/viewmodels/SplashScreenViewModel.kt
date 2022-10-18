package com.experiment.foodproductapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import kotlinx.coroutines.*

class SplashScreenViewModel : ViewModel() {

    val splashDuration: Long = 3000  // Milliseconds

    fun execute(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {

            delay(splashDuration)

            withContext(Dispatchers.Main) {
                navHostController.navigate(Screen.SignInScreen.route){
                    popUpTo(Screen.SplashScreen.route){ inclusive = true }
                }
            }
        }
    }

}