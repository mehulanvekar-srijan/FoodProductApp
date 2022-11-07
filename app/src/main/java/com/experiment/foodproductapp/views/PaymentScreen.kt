package com.experiment.foodproductapp.views

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.experiment.foodproductapp.MainActivity
import com.experiment.foodproductapp.utility.payment

@Composable
fun PaymentScreen(
    activityLambda: () -> Activity,
) {

    val mainActivity = activityLambda() as MainActivity

    LaunchedEffect(key1 = Unit){
        payment(mainActivity)
    }

    if (mainActivity.status.value == true){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green),
            contentAlignment = Alignment.Center,
        ){
            Text(text = "Success")
        }
    }
    if (mainActivity.status.value == false){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red),
            contentAlignment = Alignment.Center,
        ){
            Text(text = "Failed")
        }
    }
}