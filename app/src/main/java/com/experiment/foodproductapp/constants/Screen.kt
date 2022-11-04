package com.experiment.foodproductapp.constants

sealed class Screen(val route:String) {
    object SplashScreen : Screen("SplashScreen")
    object SignUpScreen : Screen("SignUpScreen")
    object SignInScreen : Screen("SignInScreen")
    object ForgotPassword : Screen("ForgotPassword")

    object ProductDetailsScreen : Screen("ProductDetailsScreen")

    object UserDetails : Screen("UserDetails/{email}"){
        fun routeWithDate(data: String) = "UserDetails/$data"
    }
    object HomeScreen : Screen("HomeScreen/{email}"){
        fun routeWithDate(data: String) = "HomeScreen/$data"
    }
    object ProductCart : Screen("ProductCart")
    object PaymentScreen : Screen("PaymentScreen")
}