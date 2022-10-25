package com.experiment.foodproductapp.views

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.ui.theme.ChangeBarColors
import com.experiment.foodproductapp.ui.theme.Orange
import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel
import kotlin.math.min

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreenPage(
    email : String?,
    navHostControllerLambda: () -> NavHostController,
    homeScreenViewModel: HomeScreenViewModel = viewModel(),
) {

    LaunchedEffect(key1 = Unit){ homeScreenViewModel.setEmail(email) }

    ChangeBarColors(navigationBarColor = Color.White)
    val listState = rememberLazyListState()
    val brandLogoSize = remember { mutableStateOf(Int.MAX_VALUE) }

    val animatedAppBarBackgroundColor = animateColorAsState(
        targetValue = if((listState.firstVisibleItemScrollOffset >= (brandLogoSize.value/2)) || (listState.firstVisibleItemIndex > 0)) Orange
                        else Color.Transparent,
        animationSpec = tween(1),
    )
    val animatedAppBarContentColor = animateColorAsState(
        targetValue = if((listState.firstVisibleItemScrollOffset >= (brandLogoSize.value/2)) || (listState.firstVisibleItemIndex > 0)) Color.White
                        else Color.Transparent,
        animationSpec = tween(1),
    )

    val animatedAppBarElevation = animateDpAsState(
        targetValue = if((listState.firstVisibleItemScrollOffset >= (brandLogoSize.value/2)) || (listState.firstVisibleItemIndex > 0)) 3.dp
                        else 0.dp,
        animationSpec = tween(1),
    )

    Box(modifier = Modifier.fillMaxSize()){

        BackgroundImage()

        //Main Column
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            state = listState,
        ){

            item {
                BrandLogo(listState = listState,brandLogoSize = brandLogoSize)
            }

            items(
                items = homeScreenViewModel.productsList,
            ) { item ->

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                    elevation = 5.dp,
                    shape = RoundedCornerShape(
                        bottomStart = 40.dp,
                        topStart = 3.dp,
                        topEnd = 3.dp,
                        bottomEnd = 3.dp,
                    ),
                    onClick = {  },
                ) {
                    Row {
                        Image(
                            painter = rememberImagePainter(item),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.CenterStart,
                            modifier = Modifier.padding(8.dp),
                        )
                        Column {
                            Text(
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.30F)
                                    .padding(3.dp),
                                color = Color.Black,
                                overflow = TextOverflow.Ellipsis,
                                text = "Beer",
                                maxLines = 1,
                            )
                        }
                    }
                }
            }

            item{
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }

        AppBar(
            animatedAppBarBackgroundColor = animatedAppBarBackgroundColor,
            animatedAppBarContentColor = animatedAppBarContentColor,
            animatedAppBarElevation = animatedAppBarElevation,
            onUserProfileClick = {
                navHostControllerLambda().navigate(
                    Screen.UserDetails.routeWithDate(email ?: "")
                )
            }
        )
//        AnimatedVisibility(
//            visible = (listState.firstVisibleItemScrollOffset >= (brandLogoSize.value/2)) || (listState.firstVisibleItemIndex > 0),
//            enter = fadeIn(),
//            exit = fadeOut(),
//        ) {
//            //Top Bar
//            AppBar(animatedColor)
//            SideEffect { Log.d("testRecomp", "AnimatedVisibility: ") }
//        }
    }

    SideEffect { Log.d("testRecomp", "HomeScreenPage: ") }
}

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.background_home_view),
        contentDescription = "Background Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

    SideEffect { Log.d("testRecomp", "BackgroundImage: ") }
}

@Composable
fun AppBar(
    animatedAppBarBackgroundColor: State<Color>,
    animatedAppBarContentColor: State<Color>,
    animatedAppBarElevation: State<Dp>,
    onUserProfileClick: ()->Unit = {},
) {
    TopAppBar(
        title = { Text(text = "Beer App", color = animatedAppBarContentColor.value) },
        backgroundColor = animatedAppBarBackgroundColor.value,
        elevation = animatedAppBarElevation.value,
        actions = {
            IconButton(onClick = onUserProfileClick) {
                Icon(imageVector = Icons.Default.ManageAccounts, contentDescription = "", tint = Color.White)
            }
        }
    )
    SideEffect { Log.d("testRecomp", "AppBar: ") }
}

@Composable
fun BrandLogo(
    listState: LazyListState,
    brandLogoSize: MutableState<Int>
) {
    Image(
        modifier = Modifier
            .fillMaxHeight(0.10F)
            .fillMaxWidth()
            .padding(end = 25.dp)
            .onGloballyPositioned {
                brandLogoSize.value = it.size.height
            }
            .graphicsLayer {
                alpha = min(1f, 1 - (listState.firstVisibleItemScrollOffset / 600f))
                translationY = -listState.firstVisibleItemScrollOffset * 0.1f
            },
        alignment = Alignment.Center,
        painter = painterResource(id = R.drawable.ic_beer_cheers),
        contentDescription = "brand logo",
    )
    SideEffect { Log.d("testRecomp", "BrandLogo: ") }
}