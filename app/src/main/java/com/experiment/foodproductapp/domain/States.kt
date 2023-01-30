package com.experiment.foodproductapp.domain

import androidx.compose.runtime.mutableStateOf
import com.experiment.foodproductapp.constants.Screen

val rootStartDestinationRoute =  mutableStateOf(Screen.SplashScreen.route)
val homeScreenStartDestinationRoute =  mutableStateOf("HomeToProductDetails3/{email}/{id}")
val userScreenStartDestinationRoute =  mutableStateOf("UserDetailsToRewardsScreen/{email}")