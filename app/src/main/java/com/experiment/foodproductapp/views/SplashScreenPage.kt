package com.experiment.foodproductapp.views

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.ui.theme.ChangeBarColors
import com.experiment.foodproductapp.ui.theme.DarkYellow
import com.experiment.foodproductapp.ui.theme.Orange
import com.experiment.foodproductapp.viewmodels.SplashScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel


@Composable
fun SplashScreenPage(
    navHostControllerLambda: () -> NavHostController,
    splashScreenViewModel: SplashScreenViewModel = koinViewModel(),
    animationDuration: Int = splashScreenViewModel.splashDuration.toInt() - 1000
) {
    val startAnimation = remember { mutableStateOf(false) }

    ChangeBarColors(statusColor = Orange, navigationBarColor = DarkYellow)

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
    }

    LaunchedEffect(key1 = Unit) {
        startAnimation.value = true
        splashScreenViewModel.execute()
        splashScreenViewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navHostControllerLambda().navigate(
                        Screen.HomeScreen.routeWithData(
                            splashScreenViewModel.email.value
                        )
                    )
                    {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
                is ValidationEvent.Failure -> {
                    navHostControllerLambda().navigate(Screen.SignInScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
//        if (loggedInEmail == null) {
//            withContext(Dispatchers.Main) {
//                navHostControllerLambda().navigate(Screen.SignInScreen.route) {
//                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
//                }
//            }
//        } else {
//            withContext(Dispatchers.Main) {
//                navHostControllerLambda().navigate(Screen.HomeScreen.routeWithData(loggedInEmail))
//                {
//                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
//                }
//            }
            }
        }
    }
}

@Composable
fun CollisionAnimation(
    animatedAlpha: State<Float>,
    animatedShape: State<Float>,
    animatedPadding: State<Dp>,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_beer_left_glass),
        contentDescription = "ic_beer_left_glass",
        modifier = Modifier
            .padding(start = animatedPadding.value),
        contentScale = ContentScale.Fit,
        alignment = Alignment.CenterStart,
    )

    Image(
        painter = painterResource(id = R.drawable.ic_beer_right_glass),
        contentDescription = "ic_beer_right_glass",
        modifier = Modifier
            .padding(end = animatedPadding.value),
        contentScale = ContentScale.Fit,
        alignment = Alignment.CenterEnd,
    )

    Image(
        painter = painterResource(id = R.drawable.ic_beer_spark),
        contentDescription = "ic_beer_spark",
        modifier = Modifier.fillMaxSize(animatedShape.value),
        contentScale = ContentScale.Fit,
        alpha = animatedAlpha.value,
    )
}


//@Composable
//fun DrawLogo(
//    animatedAlpha: State<Float>,
//    animatedShape: State<Float>,
//    animatedAngle: State<Float>,
//) {
//    Image(
//        painter = painterResource(id = R.drawable.ic_stars),
//        contentDescription = "ic_stars",
//        modifier = Modifier
//            .padding(15.dp)
//            .fillMaxSize(),
//        contentScale = ContentScale.Fit,
//        alignment = Alignment.Center,
//        alpha = animatedAlpha.value,
//    )
//    Image(
//        painter = painterResource(id = R.drawable.ic_beer_glass),
//        contentDescription = "ic_beer_glass",
//        modifier = Modifier
//            .padding(30.dp)
//            .fillMaxSize(animatedShape.value)
//            .rotate(animatedAngle.value),
//        contentScale = ContentScale.Fit,
//        alignment = Alignment.Center,
//        alpha = animatedAlpha.value,
//    )
//}

//@Composable
//fun DrawLogo1(
//    animatedAlpha: State<Float>,
//    animatedShape: State<Float>,
//    animatedAngle: State<Float>,
//) {
//    Image(
//        painter = painterResource(id = R.drawable.ic_beer_drops),
//        contentDescription = "ic_beer_drops",
//        modifier = Modifier
//            .padding(5.dp)
//            .fillMaxSize(animatedShape.value),
//        contentScale = ContentScale.Fit,
//        alignment = Alignment.Center,
//        alpha = animatedAlpha.value,
//    )
//    Image(
//        painter = painterResource(id = R.drawable.ic_beer_glass),
//        contentDescription = "ic_beer_glass",
//        modifier = Modifier
//            .padding(30.dp)
//            .fillMaxSize(animatedShape.value)
//            .rotate(animatedAngle.value),
//        contentScale = ContentScale.Fit,
//        alignment = Alignment.Center,
//        alpha = animatedAlpha.value,
//    )
//}
