package com.experiment.foodproductapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.experiment.foodproductapp.constants.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenViewModel : ViewModel() {

    val splashDuration: Long = 3000  // Milliseconds

    fun execute(navController: NavController) {
        viewModelScope.launch(Dispatchers.IO) {

            delay(splashDuration)

            withContext(Dispatchers.Main) {
                navController.navigate(route = Screen.SignUpScreen.route){
                    popUpTo(Screen.SplashScreen.route){inclusive=true}
                }

            }
        }
    }

}