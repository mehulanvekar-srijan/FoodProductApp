package com.experiment.foodproductapp.views


import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource


import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.*

import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.entity.LikedItems
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.DetailsPageViewModel

import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun Preview() {
    val navHostController = rememberNavController()
    ProductDetailsPage("romi@romi.com",1,{ navHostController }, koinViewModel())
}


@OptIn(ExperimentalCoilApi::class, ExperimentalAnimationApi::class)
@Composable
fun ProductDetailsPage(
    email: String?,
    id: Int?,
    navHostControllerLambda: () -> NavHostController,
    detailsPageViewModel: DetailsPageViewModel = koinViewModel()
) {
    val likedState = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {

        detailsPageViewModel.setEmail(email)
        detailsPageViewModel.addProduct(id!!)

        detailsPageViewModel.getProductCount()
        detailsPageViewModel.initCartItemsCount()
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

            //White Background
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.87f)
                    .clip(RoundedCornerShape(bottomStartPercent = 45))
                    .background(Color.White)
            ) {

                //Image Title Description
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.88f)
                        .verticalScroll(scrollState),
                ) {

                    //Product Image
                    Image(
                        painter = rememberImagePainter(detailsPageViewModel.productForDetailPage.value.url),
                        contentDescription = "ic_product_image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onDoubleTap = {

                                        if (likedState.value) { //Already Liked, Then Remove
                                            detailsPageViewModel.removeFromFavourites(
                                                id = detailsPageViewModel.productForDetailPage.value.id,
                                                email = detailsPageViewModel.userEmail.value
                                            )
                                        } else { //Not Yet Liked, Then Add in the Table
                                            detailsPageViewModel.insertFavouriteProduct(
                                                likedItems = LikedItems(
                                                    id = detailsPageViewModel.productForDetailPage.value.id,
                                                    email = detailsPageViewModel.userEmail.value
                                                )
                                            )
                                        }
                                        likedState.value = !likedState.value

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

                    //Title
                    Text(
                        text = detailsPageViewModel.productForDetailPage.value.title,
                        color = Color.DarkGray,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 26.sp,
                            letterSpacing = 1.sp,
                            fontFamily = titleFontFamily
                        ),

                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, top = 20.dp,end = 20.dp)
                    )

                    Spacer(modifier = Modifier.background(Color.Red).padding(top = 10.dp))

                    //Description
                    Text(
                        text = detailsPageViewModel.productForDetailPage.value.description,
                        color = LightDarkGray,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontFamily = descriptionFontFamily
                        ),
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 20.dp, bottom = 5.dp, top = 5.dp),
                    )
                }

                Spacer(modifier = Modifier.padding(top = 20.dp))

                //Add n Minus icons
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
                            detailsPageViewModel.incrementProductCount()
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
                        text = detailsPageViewModel.quantity.value.toString(),
                        style = TextStyle(
                            fontSize = 25.sp,
                        ),
                        color = DarkYellow,
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                    )

                    //Minus
                    IconButton(
                        onClick = {
                            detailsPageViewModel.decrementProductCount()
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

            //Bottom % Icons
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
                        text = "" + detailsPageViewModel.productForDetailPage.value.alcohol + stringResource(
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
                        text = stringResource(id = R.string.rs_dot_string) + " " + detailsPageViewModel.productForDetailPage.value.price,
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal, fontSize = 20.sp, letterSpacing = 1.sp
                        ),
                        fontFamily = descriptionFontFamily,
                    )
                }
            }
        }

//        LikedAnimation(likedState = likedState)

        AppBar(
            likedState = likedState,
            email = detailsPageViewModel.userEmail.value,
            detailsPageViewModel = detailsPageViewModel,
            navHostControllerLambda = navHostControllerLambda,
        ) {
            navHostControllerLambda().navigate(
                Screen.ProductCart.routeWithData(
                    detailsPageViewModel.userEmail.value
                )
            )
        }
    }
}


@Composable
fun AppBar(
    likedState: MutableState<Boolean>,
    email: String,
    detailsPageViewModel: DetailsPageViewModel,
    navHostControllerLambda: () -> NavHostController,
    onProductCartClick: () -> Unit = {},
) {

    //To set initial value of like button
    LaunchedEffect(key1 = Unit) {
        val likedItems = detailsPageViewModel.fetchFavouriteProductsByEmail(email = email)
        likedItems.forEach {
            if (detailsPageViewModel.productForDetailPage.value.id == it.id) {
                likedState.value = true
            }
        }
    }
    TopAppBar(
        title = { },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "ic_arrow_back_bt",
                    tint = DarkYellow
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    if (likedState.value) { //Liked
                        detailsPageViewModel.removeFromFavourites(
                            id = detailsPageViewModel.productForDetailPage.value.id,
                            email = detailsPageViewModel.userEmail.value
                        )
                    } else { //Not Yet Liked
                        detailsPageViewModel.insertFavouriteProduct(
                            likedItems = LikedItems(
                                id = detailsPageViewModel.productForDetailPage.value.id,
                                email = detailsPageViewModel.userEmail.value
                            )
                        )
                    }
                    likedState.value = !likedState.value
                }
            ) {
//                Box {
                if (likedState.value) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "ic_Favorite_bt",
                        tint = Color.Red
                    )
                    LikedAnimation(likedState = likedState)
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "ic_Favorite_bt",
                        tint = Orange
                    )
                }
//                }

            }
            val offset = 12
            if (detailsPageViewModel.cartItemCount.value > 0) {
                BadgedBox(
                    badge = {
                        Badge(
                            modifier = Modifier.offset(x = -offset.dp, y = offset.dp)
                        ) {
                            Text(text = "${detailsPageViewModel.cartItemCount.value}")
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

@Composable
fun LikedAnimation(likedState: State<Boolean>) {

    val compositionResult = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.confetti)
    )

    val progress = animateLottieCompositionAsState(
        composition = compositionResult.value,
        isPlaying = likedState.value,
        iterations = 1,
        speed = 1.0F,
        cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
    )

    LottieAnimation(
        composition = compositionResult.value,
        progress = { progress.value },
        modifier = Modifier.size(height = 200.dp, width = 50.dp)
    )
}
