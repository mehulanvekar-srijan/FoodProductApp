package com.experiment.foodproductapp.views

import android.app.Activity
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.MainActivity
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.utility.payment
import com.experiment.foodproductapp.viewmodels.PaymentScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PaymentScreen(
    navHostControllerLambda: () -> NavHostController,
    email: String?,
    phoneNumber: String?,
    sum: Int?,
    points: Int?,
    paymentScreenViewModel: PaymentScreenViewModel = koinViewModel(),
    activityLambda: () -> Activity,
) {
    Log.d("total1", sum.toString())
    val total = sum.toString()
    val mainActivity = activityLambda() as MainActivity
    val context = LocalContext.current

    ChangeBarColors(statusColor = Orange, navigationBarColor = DarkYellow)

    LaunchedEffect(key1 = Unit) {
        if (sum != null) {
            payment(mainActivity, email.toString(), phoneNumber.toString(), total)
        }
        paymentScreenViewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navHostControllerLambda().navigate(Screen.HomeScreen.routeWithData(email.toString())) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = true }
                    }
                }
                is ValidationEvent.Failure -> {
                    navHostControllerLambda().navigate(Screen.ProductCart.routeWithData(email.toString())) {
                        popUpTo(Screen.ProductCart.route) { inclusive = true }
                    }
                }
            }
        }
    }

    if (mainActivity.status.value == true) {  //Payment success

        val startAnimation = remember { mutableStateOf(false) }
        val animationDuration: Int = paymentScreenViewModel.splashDuration.toInt() - 1000

        LaunchedEffect(key1 = Unit) {
            paymentScreenViewModel.navigateOnSuccess(
                email = email,
                sum = sum,
                points = points,
                activity = mainActivity,
            )
            startAnimation.value = true
        }

        val animatedAlpha = animateFloatAsState(
            targetValue = if (startAnimation.value) 1F else 0F,
            animationSpec = tween(animationDuration),
        )

        val animatedShape = animateFloatAsState(
            targetValue = if (startAnimation.value) 0.9F else 0.0F,
            animationSpec = tween(animationDuration),
        )

        val animatedPadding = animateDpAsState(
            targetValue = if (startAnimation.value) 15.dp else 0.dp,
            animationSpec = tween(2000),
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_yellow_wave),
                contentDescription = "ic_background_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            CollisionAnimation(
                animatedAlpha = animatedAlpha,
                animatedShape = animatedShape,
                animatedPadding = animatedPadding,
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.75f),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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

            }
            Text(
                text = "redirecting you to Home Page",
                fontFamily = descriptionFontFamily,
                color = Color.DarkGray,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }

    }

    if (mainActivity.status.value == false) { //Payment failure
        LaunchedEffect(key1 = Unit) {
            paymentScreenViewModel.navigateOnFailure(
                email = email,
                activity = mainActivity,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_yellow_wave),
                contentDescription = "ic_background_image",
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
                    contentDescription = "ic_failure",
//                    tint = Color.Unspecified,
                )
                Text(
                    text = stringResource(id = R.string.payment_failed_string),
                    fontFamily = titleFontFamily,
                    fontSize = 24.sp,
                    color = Color.DarkGray
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.redirecting_you_to_product_cart_string),
                    fontFamily = descriptionFontFamily,
                    color = Color.DarkGray
                )
            }
        }
    }
}
