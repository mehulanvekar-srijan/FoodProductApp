package com.experiment.foodproductapp.views


import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource


import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.ui.theme.*

import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun Preview() {
    val navHostController = rememberNavController()
    ProductDetailsPage({ navHostController }, viewModel())
}


@OptIn(ExperimentalCoilApi::class, ExperimentalAnimationApi::class)
@Composable
fun ProductDetailsPage(
    navHostControllerLambda: () -> NavHostController, homeScreenViewModel: HomeScreenViewModel
) {

    val scrollState = rememberScrollState()
    val likedState = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        homeScreenViewModel.getProductCount()
        homeScreenViewModel.initCartItemsCount()
    }

    ChangeBarColors(statusColor = Color.White, navigationBarColor = DarkYellow)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkYellow),
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.87f)
                    .clip(RoundedCornerShape(bottomStartPercent = 45))
                    .background(Color.White)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.88f)
                        .verticalScroll(scrollState),
                ) {

                    Image(
                        painter = rememberImagePainter(homeScreenViewModel.productForDetailPage.value.url),
                        contentDescription = "ic_product_image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        Log.d(
                                            "testTaps",
                                            "ProductDetailsPage: onDoubleTap"
                                        )
                                        coroutineScope.launch {
                                            likedState.value = !likedState.value
                                            delay(1000)
                                            likedState.value = !likedState.value
                                        }
                                    },
                                )
                            }
                            .height(350.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                            .graphicsLayer {
                                alpha =
                                    1.05f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.0f)
                                translationY = 0.3f * scrollState.value
                            },
                    )

                    Text(
                        text = homeScreenViewModel.productForDetailPage.value.title,
                        color = Color.DarkGray,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 26.sp,
                            letterSpacing = 1.sp,
                            fontFamily = titleFontFamily
                        ),

                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 40.dp, top = 20.dp)
                    )

                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    Text(
                        text = homeScreenViewModel.productForDetailPage.value.description,
                        color = LightDarkGray,
                        style = TextStyle(
                            fontSize = 17.sp, fontFamily = descriptionFontFamily
                        ),
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 20.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(top = 20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Add
                    IconButton(
                        onClick = {
                            homeScreenViewModel.incrementProductCount()
                        }, modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Orange, DarkYellow
                                    )
                                )
                            )
                            .size(width = 35.dp, height = 35.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "ic_add_count_bt",
                            tint = Color.White,
                        )
                    }

                    Text(
                        text = homeScreenViewModel.quantity.value.toString(),
                        style = TextStyle(
                            fontSize = 25.sp,
                        ),
                        color = DarkYellow,
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                    )

                    //Minus
                    IconButton(
                        onClick = {
                            homeScreenViewModel.decrementProductCount()
                        }, modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Orange, DarkYellow
                                    )
                                )
                            )
                            .size(width = 35.dp, height = 35.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "ic_minus_count_bt",
                            tint = Color.White,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(top = 20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_percentage_percent),
                        contentDescription = "ic_percentage",
                        modifier = Modifier.fillMaxHeight(.5f)
                    )
                    Text(
                        text = "" + homeScreenViewModel.productForDetailPage.value.alcohol + stringResource(
                            id = R.string.five_alcohol_string
                        ),
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal, fontSize = 20.sp, letterSpacing = 1.sp
                        ),
                        fontFamily = descriptionFontFamily,
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_rupee_sv),
                        contentDescription = "ic_rupees",
                        modifier = Modifier.fillMaxHeight(.5f)
                    )

                    Text(
                        text = stringResource(id = R.string.rs_dot_string) + " " + homeScreenViewModel.productForDetailPage.value.price,
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal, fontSize = 20.sp, letterSpacing = 1.sp
                        ),
                        fontFamily = descriptionFontFamily,
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = likedState.value,
            enter= fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier.fillMaxHeight(0.6F)
        ){

            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize().padding(80.dp),
                tint = Color.Red,
            )

        }

        AppBar(
            homeScreenViewModel = homeScreenViewModel,
            navHostControllerLambda = navHostControllerLambda,
            onProductCartClick = {
                navHostControllerLambda().navigate(
                    Screen.ProductCart.routeWithData(
                        homeScreenViewModel.userEmail.value
                    )
                )
            },
        )
    }
}


@Composable
fun AppBar(
    homeScreenViewModel: HomeScreenViewModel,
    navHostControllerLambda: () -> NavHostController,
    onProductCartClick: () -> Unit = {},
) {
    TopAppBar(title = { }, backgroundColor = Color.Transparent, elevation = 0.dp, navigationIcon = {
        IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "ic_arrow_back_bt",
                tint = DarkYellow
            )
        }

    }, actions = {
        IconButton(onClick = { homeScreenViewModel.changeState()}) {
            if (homeScreenViewModel.favoriteState.value) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "ic_Favorite_bt",
                    tint = Orange
                )
            }
            else{
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "ic_Favorite_bt",
                    tint = Color.Red
                )
            }
        }
        val offset = 12
        if (homeScreenViewModel.cartItemCount.value > 0) {
            BadgedBox(
                badge = {
                    Badge(
                        modifier = Modifier.offset(x = -offset.dp, y = offset.dp)
                    ) {
                        Text(text = "${homeScreenViewModel.cartItemCount.value}")
                    }
                },
            ) {
                IconButton(onClick = onProductCartClick) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "ic_product_cart_bt",
                        tint = DarkYellow
                    )
                }
            }
        } else {
            IconButton(onClick = onProductCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "ic_shopping_cart",
                    tint = DarkYellow
                )
            }
        }
    })
}
