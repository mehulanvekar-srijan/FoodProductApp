package com.experiment.foodproductapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.actor.ScopeReference
import com.experiment.foodproductapp.actor.TheActor
import com.experiment.foodproductapp.actor.route.RouteState
import com.experiment.foodproductapp.actor.route.routeActor
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.domain.homeScreenStartDestinationRoute
import com.experiment.foodproductapp.domain.rootStartDestinationRoute
import com.experiment.foodproductapp.domain.userScreenStartDestinationRoute
import com.experiment.foodproductapp.ui.theme.FoodProductAppTheme
import com.experiment.foodproductapp.viewmodels.OrderDetailsViewModel
import com.experiment.foodproductapp.views.*
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.androidx.compose.koinViewModel

val backStack = mutableListOf<NavBackStackEntry>()
var mBackQueue = ArrayDeque<NavBackStackEntry>()


class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    var navHostController: NavHostController? = null

    override fun onPause() {
        super.onPause()
        navHostController?.backQueue?.forEach {
            if(it.destination.route != null) {
                Log.d("testBS", "onPause:backstack navHostController route=${it.destination.route} | ")
                backStack.add(element = it)
                mBackQueue.add(element = it)
            }
        }
        Log.d("testbs", "onPause: Start Routes u=${userScreenStartDestinationRoute.value} h=${homeScreenStartDestinationRoute.value}")
//        Log.d("testBS", "onPause:backstack navHostController2 -> ")
//        navHostController2?.backQueue?.forEach {
//            if(it.destination.route != null) {
//                Log.d("testBS", "onPause:backstack navHostController2 route=${it.destination.route} | ")
//                //backStack.add(element = it)
//                //mBackQueue.add(element = it)
//            }
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("testbs", "onCreate: called action=${intent.action} dataString=${intent.dataString} data=${intent.data}")
        //intent.data = Uri.parse("https://www.foodproductapp.com/meh@ul.com/10")
        //Log.d("testbs", "onCreate: called action=${intent.action} dataString=${intent.dataString} data=${intent.data}")


        setContent {

            navHostController = rememberNavController()
//            navHostController1 = rememberNavController()
//            navHostController2 = rememberNavController()

            Log.d("testbs", "onCreate: navHostController = ${navHostController.hashCode()} | mBackQueue = $mBackQueue")

            val navHostControllerLambda: () -> NavHostController =
                { navHostController as NavHostController }

//            val navHostControllerLambda1: () -> NavHostController =
//                { navHostController1 as NavHostController }
//
//            val navHostControllerLambda2: () -> NavHostController =
//                { navHostController2 as NavHostController }

            val orderScreenViewModel: OrderDetailsViewModel = koinViewModel()
            val uri = "https://www.foodproductapp.com"


            FoodProductAppTheme {

                NavHost(
                    navController = navHostController as NavHostController,
                    startDestination = rootStartDestinationRoute.value
                ) {


                    //=====

                    navigation(
                        route = "RootNavigation",
                        startDestination = "HomeScreenX/{email}",
                    ){

                        composable( //HomeScreen
                            route = "HomeScreenX/{email}",
                            deepLinks = listOf(navDeepLink { uriPattern = "$uri/{email}" }),
                            arguments = listOf(navArgument("email") { type = NavType.StringType })
                        ) {
                            HomeScreenPage(
                                email = it.arguments?.getString("email"),
                                navHostControllerLambda = navHostControllerLambda,
                            )

                        }


                        navigation( // PS UD LS CS
                            route = "HomeScreenOptions",
                            startDestination = homeScreenStartDestinationRoute.value,
                        ){

                            composable( // ProductDetails PD -
                                route = "HomeToProductDetails/{email}/{id}",
                                deepLinks = listOf(navDeepLink { uriPattern = "$uri/{email}/{id}" }),
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


                            navigation( //  UserDetails UD -
                                route = "UserDetailsNavigation",
                                startDestination = "UserDetailsScreenX/{email}"
                            ){

                                composable( // UserDetails UD
                                    route = "UserDetailsScreenX/{email}",
                                    arguments = listOf(navArgument("email") { type = NavType.StringType })
                                ) {
                                    UserDetails(navHostControllerLambda, it.arguments?.getString("email"))
                                }

                                navigation( // OD RS
                                    route = "UserDetailsOptions",
                                    startDestination = userScreenStartDestinationRoute.value,
                                ){

                                    composable(
                                        route = "UserDetailsToRewardsScreen/{email}",
                                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                                    ) {
                                        Log.d("testbs", "composable: Reward")
                                        Reward(
                                            email = it.arguments?.getString("email"),
                                            navHostControllerLambda = navHostControllerLambda
                                        )
                                    }

                                    composable(
                                        route = "UserDetailsToOrderDetailsScreen/{email}",
                                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                                    ) {
                                        OrderDetails(
                                            email = it.arguments?.getString("email"),
                                            navHostControllerLambda = navHostControllerLambda,
                                            orderDetailsViewModel = orderScreenViewModel
                                        )
                                    }

//                                    composable( // ProductDetails PD -
//                                        route = "ForbiddenRoute/{email}/{id}",
//                                        deepLinks = listOf(navDeepLink { uriPattern = "$uri/{email}/{id}" }),
//                                        arguments = listOf(
//                                            navArgument("email") { type = NavType.StringType },
//                                            navArgument("id") { type = NavType.IntType },
//                                        )
//                                    ) {
//                                        ProductDetailsPage(
//                                            email = it.arguments?.getString("email"),
//                                            id = it.arguments?.getInt("id"),
//                                            navHostControllerLambda = navHostControllerLambda,
//                                        )
//                                    }

                                }
                            }

//                            composable( // UserDetails UD
//                                route = "HomeToProductDetails5/{email}",
//                                arguments = listOf(navArgument("email") { type = NavType.StringType })
//                            ) {
//                                UserDetails(navHostControllerLambda, it.arguments?.getString("email"))
//                            }



                            composable( // LikedScreen LS
                                route = "HomeToLikedScreen/{email}",
                                arguments = listOf(navArgument("email") { type = NavType.StringType })
                            ) {
                                FavouriteProductsPage(
                                    it.arguments?.getString("email"),
                                    navHostControllerLambda
                                )
                            }



                            navigation( // CartScreen CS
                                route = "CartScreenNavigation",
                                startDestination = "HomeToCartScreen/{email}"
                            ){

                                composable( // CartScreen CS
                                    route = "HomeToCartScreen/{email}",
                                    arguments = listOf(navArgument("email") { type = NavType.StringType })
                                ) {
                                    ProductCart(
                                        it.arguments?.getString("email"),
                                        navHostControllerLambda
                                    )
                                }

                                navigation( // RS
                                    route = "CartScreenPaths",
                                    startDestination = "CartToReward/{email}",
                                ){

                                    composable(
                                        route = "CartToReward/{email}",
                                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                                    ) {
                                        Reward(
                                            it.arguments?.getString("email"),
                                            navHostControllerLambda
                                        )
                                    }

                                }
                            }

//                            composable( // CartScreen CS
//                                route = "HomeToCartScreen/{email}",
//                                arguments = listOf(navArgument("email") { type = NavType.StringType })
//                            ) {
//                                ProductCart(
//                                    it.arguments?.getString("email"),
//                                    navHostControllerLambda
//                                )
//                            }

                        }

                    }


                    //=====

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

//                    composable(
//                        route = Screen.HomeScreen.route,
//                        deepLinks = listOf(navDeepLink { uriPattern = "$uri/{email}" }),
//                        arguments = listOf(navArgument("email") { type = NavType.StringType })
//                    ) {
//                        HomeScreenPage(
//                            email = it.arguments?.getString("email"),
//                            navHostControllerLambda = navHostControllerLambda,
//                        )
//                    }

                    composable(route = Screen.ForgotPassword.route) {
                        ForgotPassword(navHostControllerLambda)
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
                        route = Screen.RewardsDetailsPage.route,
                        arguments = listOf(navArgument("points") { type = NavType.IntType })
                    ) {
                        RewardDetails(
                            it.arguments?.getInt("points"),
                            navHostControllerLambda
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

//                    composable(
//                        route = Screen.UserDetails.route,
//                        arguments = listOf(navArgument("email") { type = NavType.StringType })
//                    ) {
//                        UserDetails(navHostControllerLambda, it.arguments?.getString("email"))
//                    }

//                    composable(
//                        route = Screen.ProductCart.route,
//                        arguments = listOf(navArgument("email") { type = NavType.StringType })
//                    ) {
//                        ProductCart(
//                            it.arguments?.getString("email"),
//                            navHostControllerLambda
//                        )
//                    }

//                    composable(
//                        route = Screen.ProductDetailsScreen.route,
//                        deepLinks = listOf(navDeepLink { uriPattern = "$uri/{email}/{id}" }),
//                        arguments = listOf(
//                            navArgument("email") { type = NavType.StringType },
//                            navArgument("id") { type = NavType.IntType },
//                        )
//                    ) {
//                        ProductDetailsPage(
//                            email = it.arguments?.getString("email"),
//                            id = it.arguments?.getInt("id"),
//                            navHostControllerLambda = navHostControllerLambda,
//                        )
//                    }

//                    composable(
//                        route = Screen.OrderDetails.route,
//                        arguments = listOf(navArgument("email") { type = NavType.StringType })
//                    ) {
//                        OrderDetails(
//                            it.arguments?.getString("email"),
//                            navHostControllerLambda,
//                            orderDetailsViewModel = orderScreenViewModel
//                        )
//                    }

//                    composable(
//                        route = Screen.Rewards.route,
//                        arguments = listOf(navArgument("email") { type = NavType.StringType })
//                    ) {
//                        Reward(
//                            it.arguments?.getString("email"),
//                            navHostControllerLambda
//                        )
//                    }

//                    composable(
//                        route = Screen.FavouriteProductsScreen.route,
//                        arguments = listOf(navArgument("email") { type = NavType.StringType })
//                    ) {
//                        FavouriteProductsPage(
//                            it.arguments?.getString("email"),
//                            navHostControllerLambda
//                        )
//                    }
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

    override fun onDestroy() {
        super.onDestroy()
        navHostController?.backQueue?.forEach {
            if(it.destination.route != null) {
                Log.d("testBS", "onDestroy:backstack navHostController1 route=${it.destination.route} | ")
                backStack.add(element = it)
                mBackQueue.add(element = it)
            }
        }
//        navHostController2?.backQueue?.forEach {
//            if(it.destination.route != null) {
//                Log.d("testBS", "onDestroy:backstack navHostController2 route=${it.destination.route} | ")
//                //backStack.add(element = it)
//                //mBackQueue.add(element = it)
//            }
//        }

        Log.d("testbs", "onDestroy: backStack = $backStack ")
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
