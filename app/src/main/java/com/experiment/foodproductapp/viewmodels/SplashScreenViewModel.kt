package com.experiment.foodproductapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.experiment.foodproductapp.constants.Screen
import kotlinx.coroutines.*

class SplashScreenViewModel : ViewModel() {

    private var job: Job? = null
    val splashDuration: Long = 5000  // Milliseconds

    fun execute(navController: NavController) {
        job = viewModelScope.launch(Dispatchers.IO) {

            delay(splashDuration)

            withContext(Dispatchers.Main) {
                navController.navigate(route = Screen.SignInScreen.route){
                    popUpTo(Screen.SplashScreen.route){inclusive=true}
                }

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}