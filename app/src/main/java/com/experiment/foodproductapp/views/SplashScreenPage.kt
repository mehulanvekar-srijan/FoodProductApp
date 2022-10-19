package com.experiment.foodproductapp.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.ui.theme.Purple700
import com.experiment.foodproductapp.viewmodels.SplashScreenViewModel

@Composable
fun SplashScreenPage(
    navHostControllerLambda : () -> NavHostController,
    splashScreenViewModel: SplashScreenViewModel = viewModel(),
    animationDuration : Int = splashScreenViewModel.splashDuration.toInt() - 1000
) {
    val startAnimation = remember { mutableStateOf(false) }

    val animatedAlpha = animateFloatAsState(
        targetValue = if(startAnimation.value) 1F else 0F,
        animationSpec = tween(animationDuration),
    )

    val animatedShape = animateFloatAsState(
        targetValue = if(startAnimation.value) 0.9F else 0.0F,
        animationSpec = tween(animationDuration),
    )

    val animatedAngle = animateFloatAsState(
        targetValue = if(startAnimation.value) 0F else 25F,
        animationSpec = tween(animationDuration),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple700),
        contentAlignment = Alignment.Center,
    ) {
        DrawLogo(animatedAlpha,animatedShape,animatedAngle)
    }

    LaunchedEffect(key1 = Unit) {
        startAnimation.value = true
        splashScreenViewModel.execute(navHostControllerLambda())
    }
}

@Composable
fun DrawLogo(
    animatedAlpha: State<Float>,
    animatedShape: State<Float>,
    animatedAngle: State<Float>,
) {

    Image(
        painter = painterResource(id = R.drawable.ic_beer_glass),
        contentDescription = "ic_burger_glass",
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize(animatedShape.value)
            .rotate(animatedAngle.value),
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center,
        alpha = animatedAlpha.value,
    )
    Image(
        painter = painterResource(id = R.drawable.ic_stars),
        contentDescription = "ic_stars",
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize(animatedShape.value),
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center,
        alpha = animatedAlpha.value,
    )
}
