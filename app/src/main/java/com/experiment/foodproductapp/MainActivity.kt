package com.experiment.foodproductapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.ui.theme.FoodProductAppTheme
import com.experiment.foodproductapp.views.SignupPage
import com.experiment.foodproductapp.views.SplashScreenPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            FoodProductAppTheme {
                NavHost(
                    navController = navHostController,
                    startDestination = Screen.SplashScreen.route
                ) {
                    composable(route = Screen.SplashScreen.route) {
                        SplashScreenPage(navHostController)
                    }
                    composable(route = Screen.SignUpScreen.route) {
                        SignupPage()
                    }
                }
            }
        }
    }
}
