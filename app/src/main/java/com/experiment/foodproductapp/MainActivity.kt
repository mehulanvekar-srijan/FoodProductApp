package com.experiment.foodproductapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.experiment.foodproductapp.actor.ScopeReference
import com.experiment.foodproductapp.actor.TheActor
import com.experiment.foodproductapp.actor.route.RouteState
import com.experiment.foodproductapp.actor.route.routeActor
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.entity.User
import com.experiment.foodproductapp.ui.theme.FoodProductAppTheme
import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel
import com.experiment.foodproductapp.viewmodels.MainViewModel
import com.experiment.foodproductapp.viewmodels.OrderDetailsViewModel
import com.experiment.foodproductapp.views.*
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    var navHostController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            navHostController = rememberNavController()

            val navHostControllerLambda: () -> NavHostController =
                { navHostController as NavHostController }

            val homeScreenViewModel: HomeScreenViewModel = koinViewModel()
            val orderScreenViewModel: OrderDetailsViewModel = koinViewModel()
            val uri = "https://www.foodproductapp.com"

            FoodProductAppTheme {
                NavHost(
                    navController = navHostController as NavHostController,
                    startDestination = Screen.SplashScreen.route
                ) {
                    composable(route = Screen.SplashScreen.route) {
                        SplashScreenPage(navHostControllerLambda)
                    }
                    composable(
                        route = Screen.SignUpScreen.route,
                        deepLinks = listOf(navDeepLink { uriPattern = "$uri/signup" }),
                    ) {
                        SignupPage(navHostControllerLambda)
                    }
                    composable(
                        route = Screen.SignInScreen.route,
                        deepLinks = listOf(navDeepLink { uriPattern = "$uri/signin" }),
                    ) {
                        SignInPage(navHostControllerLambda)
                    }
                    composable(route = Screen.ForgotPassword.route) {
                        ForgotPassword(navHostControllerLambda)
                    }
                    composable(
                        route = Screen.UserDetails.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                    ) {
                        UserDetails(navHostControllerLambda, it.arguments?.getString("email"))
                    }
                    composable(
                        route = Screen.HomeScreen.route,
                        deepLinks = listOf(navDeepLink { uriPattern = "$uri/{email}" }),
                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                    ) {
                        HomeScreenPage(
                            email = it.arguments?.getString("email"),
                            navHostControllerLambda = navHostControllerLambda,
                            homeScreenViewModel = homeScreenViewModel
                        )
                    }

                    composable(
                        route = Screen.ProductCart.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                    ) {
                        ProductCart(
                            it.arguments?.getString("email"),
                            navHostControllerLambda
                        )
                    }
                    composable(
                        route = Screen.CheckoutPage.route,
                        arguments = listOf(
                            navArgument("email") { type = NavType.StringType },
                            navArgument("sum") { type = NavType.IntType },
                            navArgument("points") { type = NavType.IntType },
                        )
                    ) {
                        CheckoutPage(
                            it.arguments?.getString("email"),
                            it.arguments?.getInt("sum"),
                            it.arguments?.getInt("points"),
                            navHostControllerLambda
                        )
                    }
                    composable(route = Screen.ProductDetailsScreen.route,
                        deepLinks = listOf(navDeepLink { uriPattern = "$uri/{id}"
                        })
                    ) {
                        ProductDetailsPage(
                            navHostControllerLambda,
                            homeScreenViewModel = homeScreenViewModel
                        )
                    }
                    composable(
                        route = Screen.PaymentScreen.route,
                        arguments = listOf(
                            navArgument("email") { type = NavType.StringType },
                            navArgument("phoneNumber") { type = NavType.StringType },
                            navArgument("sum") { type = NavType.IntType },
                            navArgument("points") { type = NavType.IntType },
                        )
                    ) {
                        PaymentScreen(
                            navHostControllerLambda = navHostControllerLambda,
                            email = it.arguments?.getString("email"),
                            phoneNumber = it.arguments?.getString("phoneNumber"),
                            sum = it.arguments?.getInt("sum"),
                            points = it.arguments?.getInt("points")
                        ) { this@MainActivity }
                    }
                    composable(
                        route = Screen.OrderDetails.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                    ) {
                        OrderDetails(
                            it.arguments?.getString("email"),
                            navHostControllerLambda,
                            orderDetailsViewModel = orderScreenViewModel
                        )
                    }

                    composable(route = Screen.OrderDescriptionPage.route) {
                        OrderDescriptionPage(
                            navHostControllerLambda,
                            orderDetailsViewModel = orderScreenViewModel
                        )
                    }

                    composable(route = Screen.MapScreen.route) {
                        MapScreen()
                    }

                    composable(
                        route = Screen.Rewards.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                    ) {
                        Reward(
                            it.arguments?.getString("email"),
                            navHostControllerLambda
                        )
                    }
                    composable(
                        route = Screen.RewardsDetailsPage.route,
                        arguments = listOf(navArgument("points") { type = NavType.IntType })
                    ) {
                        RewardDetails(
                            it.arguments?.getInt("points"),
                            navHostControllerLambda
                        )
                    }
                    composable(
                        route = Screen.FavouriteProductsScreen.route,
                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                    ) {
                        FavouriteProductsPage(
                            it.arguments?.getString("email"),
                            navHostControllerLambda
                        )
                    }
                }
            }
        }

        with(ScopeReference(CoroutineScope(Dispatchers.Main + Job()))) {
            TheActor.run {
                Log.d("testActors", "0. TheActor.run{} : called")
                routeActor().toActor(RouteState.NotNavigated)
            }.start()
        }
    }


    val status by lazy { mutableStateOf<Boolean?>(null) }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Log.d("testredeemAmount", "onPaymentSuccess: p0=$p0 p1=$p1")
        status.value = true
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Log.d("testredeemAmount", "onPaymentError: p0=$p0 p1=$p1 p2=$p2")
        status.value = false
    }
}
