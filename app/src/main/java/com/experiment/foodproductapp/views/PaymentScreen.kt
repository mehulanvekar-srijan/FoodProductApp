package com.experiment.foodproductapp.views

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.MainActivity
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.ui.theme.descriptionFontFamily
import com.experiment.foodproductapp.ui.theme.titleFontFamily
import com.experiment.foodproductapp.utility.payment
import kotlinx.coroutines.delay

@Composable
fun PaymentScreen(
    navHostControllerLambda: () -> NavHostController,
    email: String?,
    phoneNumber: String?,
    sum: Int?,
    activityLambda: () -> Activity,
) {
    Log.d("total1", sum.toString())
    val total = sum.toString()
    val mainActivity = activityLambda() as MainActivity

    LaunchedEffect(key1 = Unit) {
        if (sum != null) {
            payment(mainActivity, email.toString(), phoneNumber.toString(), total)
        }
    }

    if (mainActivity.status.value == true) {
        LaunchedEffect(key1 = Unit) {
            delay(3000)
            navHostControllerLambda().navigate(Screen.HomeScreen.routeWithData(email.toString())) {
                popUpTo(Screen.PaymentScreen.route) { inclusive = true }
                popUpTo(Screen.CheckoutPage.route) { inclusive = true }
                popUpTo(Screen.ProductCart.route) { inclusive = true }
                popUpTo(Screen.HomeScreen.route) { inclusive = true }
            }
            mainActivity.status.value = null
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_yellow_wave),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_assignment_turned_in_24),
                    contentDescription = "success"
                )
                Text(
                    text = "Order Placed !",
                    fontFamily = titleFontFamily,
                    fontSize = 24.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Your Order was Placed Successfully",
                    fontFamily = descriptionFontFamily,
                    color = Color.DarkGray
                )
            }
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "redirecting you to Home Page",
                    fontFamily = descriptionFontFamily,
                    color = Color.DarkGray
                )
            }
        }
    }
    if (mainActivity.status.value == false) {
        LaunchedEffect(key1 = Unit) {
            delay(3000)
            navHostControllerLambda().navigate(Screen.ProductCart.routeWithData(email.toString())) {
                popUpTo(Screen.PaymentScreen.route) { inclusive = true }
                popUpTo(Screen.CheckoutPage.route) { inclusive = true }
                popUpTo(Screen.ProductCart.route) { inclusive = true }
            }
            mainActivity.status.value = null
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_yellow_wave),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_assignment_late_24),
                    contentDescription = "failure"
                )
                Text(
                    text = "Payment Failed !",
                    fontFamily = titleFontFamily,
                    fontSize = 24.sp,
                    color = Color.DarkGray
                )
            }
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "redirecting you to product cart",
                    fontFamily = descriptionFontFamily,
                    color = Color.DarkGray
                )
            }
        }
    }
}