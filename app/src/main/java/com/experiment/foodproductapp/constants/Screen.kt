package com.experiment.foodproductapp.constants

sealed class Screen(val route:String) {
    object SplashScreen : Screen("SplashScreen")
    object SignUpScreen : Screen("SignUpScreen")
    object SignInScreen : Screen("SignInScreen")
    object ForgotPassword : Screen("ForgotPassword")

    object ProductDetailsScreen : Screen("ProductDetailsScreen")

    object UserDetails : Screen("UserDetails/{email}"){
        fun routeWithData(data: String) = "UserDetails/$data"
    }
    object HomeScreen : Screen("HomeScreen/{email}"){
        fun routeWithData(data: String) = "HomeScreen/$data"
    }
    object ProductCart : Screen("ProductCart/{email}"){
        fun routeWithData(data: String) = "ProductCart/$data"
    }
    object OrderDetails : Screen("OrderDetails/{email}"){
        fun routeWithData(data: String) = "OrderDetails/$data"
    }
    object CheckoutPage : Screen("CheckoutPage/{email}/{sum}"){
        fun routeWithData(email: String,sum:Int) = "CheckoutPage/$email/$sum"
    }
    object PaymentScreen : Screen("PaymentScreen/{email}/{phoneNumber}/{sum}"){
        fun routeWithData(email: String,phoneNumber:String,sum:Int) = "PaymentScreen/$email/$phoneNumber/$sum"
    }
}