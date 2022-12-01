package com.experiment.foodproductapp.constants

sealed class Screen(val route:String) {
    object SplashScreen : Screen("SplashScreen")
    object SignUpScreen : Screen("SignUpScreen")
    object SignInScreen : Screen("SignInScreen")
    object ForgotPassword : Screen("ForgotPassword")

    object ProductDetailsScreen : Screen("ProductDetailsScreen")

    object OrderDescriptionPage : Screen("OrderDescriptionPage")

    object Rewards : Screen("RewardsPage/{email}"){
        fun routeWithData(email: String) = "RewardsPage/$email"
    }

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
    object CheckoutPage : Screen("CheckoutPage/{email}/{sum}/{points}"){
        fun routeWithData(email: String,sum:Int, points: Int) = "CheckoutPage/$email/$sum/$points"
    }
    object PaymentScreen : Screen("PaymentScreen/{email}/{phoneNumber}/{sum}/{points}"){
        fun routeWithData(email: String,phoneNumber:String,sum:Int, points: Int) = "PaymentScreen/$email/$phoneNumber/$sum/$points"
    }
    object MapScreen : Screen("MapScreen")

    object RewardsDetailsPage : Screen("RewardsDetailsPage/{points}"){
        fun routeWithData(points: Int) = "RewardsDetailsPage/$points"
    }
    object FavouriteProductsScreen : Screen("FavouriteProductsScreen/{email}"){
        fun routeWithData(data: String) = "FavouriteProductsScreen/$data"
    }
}