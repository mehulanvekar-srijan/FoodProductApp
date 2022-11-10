package com.experiment.foodproductapp

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.ui.theme.FoodProductAppTheme
import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel
import com.experiment.foodproductapp.views.*
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener

class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    var navHostController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            navHostController = rememberNavController()

            val navHostControllerLambda : () -> NavHostController = { navHostController as NavHostController }
            val homeScreenViewModel: HomeScreenViewModel = viewModel()

            FoodProductAppTheme {
                NavHost(
                    navController = navHostController as NavHostController,
                    startDestination = Screen.SplashScreen.route
                ) {
                    composable(route = Screen.SplashScreen.route) {
                        SplashScreenPage(navHostControllerLambda)
                    }
                    composable(route = Screen.SignUpScreen.route) {
                        SignupPage(navHostControllerLambda)
                    }
                    composable(route = Screen.SignInScreen.route) {
                        SignInPage(navHostControllerLambda)
                    }
                    composable(route = Screen.ForgotPassword.route) {
                        ForgotPassword()
                    }
                    composable(
                        route = Screen.UserDetails.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType } )
                    ) {
                        UserDetails(navHostControllerLambda,it.arguments?.getString("email"))
                    }
                    composable(
                        route = Screen.HomeScreen.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType } )
                    ) {
                        HomeScreenPage(
                            it.arguments?.getString("email"),
                            navHostControllerLambda, homeScreenViewModel = homeScreenViewModel
                        )
                    }

                    composable(route = Screen.ProductCart.route,
                            arguments = listOf(navArgument("email") { type = NavType.StringType } )
                    ) {
                        ProductCart(
                            it.arguments?.getString("email"),
                            navHostControllerLambda)
                    }
                    composable(route = Screen.CheckoutPage.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType },
                            navArgument("sum") { type = NavType.IntType } )
                    ) {
                        CheckoutPage(
                            it.arguments?.getString("email"),
                            it.arguments?.getInt("sum"),
                            navHostControllerLambda)
                    }
                    composable(route = Screen.ProductDetailsScreen.route) {
                        ProductDetailsPage (
                            navHostControllerLambda,homeScreenViewModel = homeScreenViewModel
                        )
                    }
                    composable(route = Screen.PaymentScreen.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType },
                            navArgument("phoneNumber") { type = NavType.StringType },
                            navArgument("sum") { type = NavType.IntType } )) {
                        PaymentScreen(
                            navHostControllerLambda,
                            it.arguments?.getString("email"),
                            it.arguments?.getString("phoneNumber"),
                            it.arguments?.getInt("sum")
                        ) { this@MainActivity }
                    }
                    composable(route = Screen.OrderDetails.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType } )
                    ) {
                        OrderDetails(
                            it.arguments?.getString("email"),
                            navHostControllerLambda)
                    }
                }
            }
        }
    }


    val status by lazy {  mutableStateOf<Boolean?>(null)  }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        status.value = true
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        status.value = false
    }
}
