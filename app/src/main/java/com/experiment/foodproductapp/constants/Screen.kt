package com.experiment.foodproductapp.constants

sealed class Screen(val route:String){
    object SplashScreen : Screen("SplashScreen")    // Splash Screen
    object SignUpScreen : Screen("SignUpScreen")    // SignUp Screen
}