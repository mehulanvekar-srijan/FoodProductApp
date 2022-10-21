package com.experiment.foodproductapp.constants

sealed class Screen(val route:String){
    object SplashScreen : Screen("SplashScreen")
    object SignUpScreen : Screen("SignUpScreen")
    object SignInScreen : Screen("SignInScreen")
    object ForgotPassword : Screen("ForgotPassword")
    object UserDetails : Screen("UserDetails/{email}"){
        fun routeWithDate(data: String) = "UserDetails/$data"
    }
}