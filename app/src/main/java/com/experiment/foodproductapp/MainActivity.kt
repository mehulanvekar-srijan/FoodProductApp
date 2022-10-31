package com.experiment.foodproductapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.ui.theme.FoodProductAppTheme
import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel
import com.experiment.foodproductapp.views.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            val navHostControllerLambda : () -> NavHostController = { navHostController }
            val HomeScreenViewModel: HomeScreenViewModel = viewModel()

            FoodProductAppTheme {
                NavHost(
                    navController = navHostController,
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
                        UserDetails(it.arguments?.getString("email"))
                    }
                    composable(
                        route = Screen.HomeScreen.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType } )
                    ) {
                        HomeScreenPage(it.arguments?.getString("email"),navHostControllerLambda, homeScreenViewModel = HomeScreenViewModel)
                    }

                    composable(route = Screen.ProductCart.route) {
                        ProductCart(navHostControllerLambda)
                    }
                    composable(route = Screen.ProductDetailsScreen.route) {
                        //val result =
                        //    navHostController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
                       // Log.d(TAG, "onCreate: ")

                       // ProductDetailsPage (result)
                        ProductDetailsPage (navHostControllerLambda,homeScreenViewModel = HomeScreenViewModel)
                    }
                }
            }
        }
    }
}
