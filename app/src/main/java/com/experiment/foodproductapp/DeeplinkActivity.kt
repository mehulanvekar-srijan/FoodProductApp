package com.experiment.foodproductapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.experiment.foodproductapp.ui.theme.FoodProductAppTheme
import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel
import com.experiment.foodproductapp.views.ProductDetailsPage
import org.koin.androidx.compose.koinViewModel

class DeeplinkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var navHostController: NavHostController? = null
        Log.d("testbs", "onCreate: DeeplinkActivity activity created")

        setContent{

            navHostController = rememberNavController()

            val navHostControllerLambda: () -> NavHostController =
                { navHostController as NavHostController }

            val uri = "https://www.foodproductapp.com"

            FoodProductAppTheme {
                NavHost(
                    navController = navHostController as NavHostController,
                    startDestination = "dp",
                ){

                    composable(
                        route = "dp",
                        deepLinks = listOf(navDeepLink { uriPattern = "$uri/{email}/{id}"}),
                        arguments = listOf(
                            navArgument("email") { type = NavType.StringType },
                            navArgument("id") { type = NavType.IntType },
                        )
                    ) {
                        ProductDetailsPage(
                            email = it.arguments?.getString("email"),
                            id = it.arguments?.getInt("id"),
                            navHostControllerLambda = navHostControllerLambda,
                        )
                    }

                }
            }

        }
    }
}