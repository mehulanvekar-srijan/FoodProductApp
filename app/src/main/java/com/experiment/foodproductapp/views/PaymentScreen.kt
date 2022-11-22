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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.MainActivity
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.utility.payment
import com.experiment.foodproductapp.viewmodels.PaymentScreenViewModel

@Composable
fun PaymentScreen(
    navHostControllerLambda: () -> NavHostController,
    email: String?,
    phoneNumber: String?,
    sum: Int?,
    points: Int?,
    paymentScreenViewModel: PaymentScreenViewModel = viewModel(),
    activityLambda: () -> Activity,
) {
    Log.d("total1", sum.toString())
    val total = sum.toString()
    val mainActivity = activityLambda() as MainActivity
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        if (sum != null) {
            payment(mainActivity, email.toString(), phoneNumber.toString(), total)
        }
    }


    if (mainActivity.status.value == true) { //Payment success
        LaunchedEffect(key1 = Unit) {
            paymentScreenViewModel.navigateOnSuccess(
                navHostController = navHostControllerLambda(),
                context = context,
                email = email,
                sum = sum,
                points = points,
                activity = mainActivity,
            )
        }
        ChangeBarColors(statusColor = Orange, navigationBarColor = DarkYellow)
        Box(
            modifier = Modifier
                .fillMaxSize(),
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
                    painter = painterResource(id = R.drawable.ic_baseline_assignment_turned_in_24),
                    contentDescription = "ic_success"
                )
                Text(
                    text = stringResource(id = R.string.order_placed_string),
                    fontFamily = titleFontFamily,
                    fontSize = 24.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = stringResource(id = R.string.your_order_was_placed_successfully_string),
                    fontFamily = descriptionFontFamily,
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
                    text = stringResource(id = R.string.redirecting_you_to_home_page_string),
                    fontFamily = descriptionFontFamily,
                    color = Color.DarkGray
                )
            }
        }
    }
    if (mainActivity.status.value == false) { //Payment failure
        LaunchedEffect(key1 = Unit) {
            paymentScreenViewModel.navigateOnFailure(
                navHostController = navHostControllerLambda(),
                context = context,
                email = email,
                activity = mainActivity,
            )
        }
        ChangeBarColors(statusColor = Orange, navigationBarColor = DarkYellow)
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