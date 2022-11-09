package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.*

class SplashScreenViewModel : ViewModel() {

    val splashDuration: Long = 3000  // Milliseconds

    fun execute(context: Context,navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {

            delay(splashDuration)

            val loggedInEmail: String? = DatabaseRepository(context).getLoggedInUser()

            if(loggedInEmail == null){
                withContext(Dispatchers.Main) {
                    navHostController.navigate(Screen.SignInScreen.route){
                        popUpTo(Screen.SplashScreen.route){ inclusive = true }
                    }
                }
            }
            else{
                withContext(Dispatchers.Main) {
                    navHostController.navigate(Screen.HomeScreen.routeWithData(loggedInEmail)){
                        popUpTo(Screen.SplashScreen.route){ inclusive = true }
                    }
                }
            }
        }
    }

}