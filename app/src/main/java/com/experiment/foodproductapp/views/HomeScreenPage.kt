package com.experiment.foodproductapp.views

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch
import kotlin.math.min

@Preview
@Composable
fun preview3() {
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {

        navHostController
    }
    HomeScreenPage("sahil@test.com", navHostControllerLambda = navHostControllerLambda)
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun HomeScreenPage(
    email: String?,
    navHostControllerLambda: () -> NavHostController,
    homeScreenViewModel: HomeScreenViewModel = viewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        homeScreenViewModel.setEmail(email)
        homeScreenViewModel.initHomeItems(context = context)
    }

    ChangeBarColors(navigationBarColor = Color.White)

    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()
    val brandLogoSize = remember { mutableStateOf(Int.MAX_VALUE) }

    val animatedAppBarBackgroundColor = animateColorAsState(
        targetValue = if ((listState.firstVisibleItemScrollOffset >= (brandLogoSize.value / 2)) || (listState.firstVisibleItemIndex > 0)) Orange
        else Color.Transparent,
        animationSpec = tween(1),
    )
    val animatedAppBarContentColor = animateColorAsState(
        targetValue = if ((listState.firstVisibleItemScrollOffset >= (brandLogoSize.value / 2)) || (listState.firstVisibleItemIndex > 0)) Color.White
        else Color.Transparent,
        animationSpec = tween(1),
    )

    val animatedAppBarBrandIconColor = animateColorAsState(
        targetValue = if ((listState.firstVisibleItemScrollOffset >= (brandLogoSize.value / 2)) || (listState.firstVisibleItemIndex > 0)) Color.Unspecified
        else Color.Transparent,
        animationSpec = tween(1),
    )

    val animatedAppBarElevation = animateDpAsState(
        targetValue = if ((listState.firstVisibleItemScrollOffset >= (brandLogoSize.value / 2)) || (listState.firstVisibleItemIndex > 0)) 3.dp
        else 0.dp,
        animationSpec = tween(1),
    )

    Box(modifier = Modifier.fillMaxSize()) {

        //Background Image
        BackgroundImage()

        //Main Column
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            state = listState,
        ) {

            //Brand Logo
            item {
                BrandLogo(listState = listState, brandLogoSize = brandLogoSize)
            }

            //Products
            items(items = homeScreenViewModel.homeItems.value) { item ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp)
                            .height(180.dp),
                        elevation = 5.dp,
                        shape = RoundedCornerShape(
                            bottomStart = 40.dp,
                            topStart = 3.dp,
                            topEnd = 3.dp,
                            bottomEnd = 3.dp,
                        ),
                        onClick = {
                            homeScreenViewModel.addProduct(item)
                            homeScreenViewModel.navigateToProductDetailsPage(navHostController = navHostControllerLambda())
                        },
                    ) {
                        Row {

                            //Product Image
                            Box {
                                Image(
                                    painter = rememberImagePainter(item.url),
                                    contentDescription = "ic_product",
                                    contentScale = ContentScale.Fit,
                                    alignment = Alignment.CenterStart,
                                    modifier = Modifier.padding(8.dp),
                                )
                            }

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Text( // Title
                                    textAlign = TextAlign.Start,
                                    overflow = TextOverflow.Ellipsis,
                                    text = item.title,
                                    fontFamily = titleFontFamily,
                                    fontSize = 24.sp,
                                    color = DarkGray
                                )
                                Text( // Description
                                    textAlign = TextAlign.Start,
                                    overflow = TextOverflow.Ellipsis,
                                    text = item.description,
                                    maxLines = 2,
                                    fontFamily = descriptionFontFamily,
                                    color = LightDarkGray
                                )

                                Text(
                                    // Price
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis,
                                    text = stringResource(id = R.string.mrp_rs_string) + " " + item.price,
                                    fontFamily = descriptionFontFamily,
                                    color = LightDarkGray,
                                )

                            }
                        }
                    }

                    val iconColor = remember { Animatable(DarkPink) }

                    Box( //left Middle Add Icon
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 17.dp, top = 70.dp)
                    ) {
                        Surface(
                            elevation = 3.dp,
                            color = Color.Transparent
                        ) {
                            IconButton(
                                onClick = {
                                    homeScreenViewModel.addProductToCart(item, context)
                                    coroutineScope.launch {
                                        iconColor.animateTo(Orange, tween(50))
                                        iconColor.animateTo(DarkPink, tween(200))
                                    }
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(iconColor.value)
                                    .size(width = 30.dp, height = 30.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "ic_add_to_cart_bt",
                                    tint = Color.White,
                                )
                            }
                        }
                    }
                }

            }

            //Space
            item { Spacer(modifier = Modifier.padding(5.dp)) }
        }

        //Top App Bar
        AppBar(
            animatedAppBarBackgroundColor = animatedAppBarBackgroundColor,
            animatedAppBarContentColor = animatedAppBarContentColor,
            animatedAppBarBrandIconColor = animatedAppBarBrandIconColor,
            animatedAppBarElevation = animatedAppBarElevation,
            onUserProfileClick = {
                homeScreenViewModel.navigateToUserDetails(navHostControllerLambda())
            },
            onProductCartClick = {
                homeScreenViewModel.navigateToProductCart(navHostControllerLambda())
            },
            onOrderDetailsClick = {
                homeScreenViewModel.navigateToOrderDetailsPage(navHostControllerLambda())
            }
        )
    }
}

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.background_home_view),
        contentDescription = "ic_background_image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun AppBar(
    animatedAppBarBackgroundColor: State<Color>,
    animatedAppBarContentColor: State<Color>,
    animatedAppBarBrandIconColor: State<Color>,
    animatedAppBarElevation: State<Dp>,
    onUserProfileClick: () -> Unit = {},
    onProductCartClick: () -> Unit = {},
    onOrderDetailsClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = animatedAppBarContentColor.value
            )
        },
        backgroundColor = animatedAppBarBackgroundColor.value,
        elevation = animatedAppBarElevation.value,
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_beer_cheers),
                contentDescription = "ic_beer_cheers",
                tint = animatedAppBarBrandIconColor.value,
                modifier = Modifier.padding(start = 5.dp)
            )
        },
        actions = {
            IconButton(onClick = onUserProfileClick) {
                Icon(
                    imageVector = Icons.Default.ManageAccounts,
                    contentDescription = "ic_edit_profile_bt",
                    tint = Color.White
                )
            }

            IconButton(onClick = onOrderDetailsClick) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "ic_order_history_bt",
                    tint = Color.White
                )
            }

            IconButton(onClick = onProductCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "ic_shopping_cart",
                    tint = Color.White
                )
//                Text(
//                    text = "20",
//                    modifier = Modifier.offset(x = 10.dp, y = -10.dp),
//                    fontSize = 10.sp,
//                )
            }
        }
    )

//    val count = remember{ mutableStateOf(0) }
//    SideEffect { Log.d("testRecomp", "AppBar: ${count.value++}") }
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
            .onGloballyPositioned {
                brandLogoSize.value = it.size.height
            }
            .graphicsLayer {
                alpha = min(1f, 1 - (listState.firstVisibleItemScrollOffset / 600f))
                translationY = -listState.firstVisibleItemScrollOffset * 0.1f
            },
        alignment = Alignment.Center,
        painter = painterResource(id = R.drawable.ic_beer_cheers),
        contentDescription = "ic_brand_logo",
    )
}



