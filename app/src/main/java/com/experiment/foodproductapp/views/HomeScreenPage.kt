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
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.domain.homeScreenStartDestinationRoute
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel
import com.experiment.foodproductapp.viewmodels.MainViewModel
import com.experiment.foodproductapp.viewmodels.NavigationUIMessages
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
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
    homeScreenViewModel: HomeScreenViewModel = koinViewModel(),
    mainViewModel: MainViewModel = koinViewModel()
) {
    navHostControllerLambda().backQueue.forEach {
        if(it.destination.route != null) Log.d("testBS", "HomeScreenPage: route=${it.destination.route}")
    }

    LaunchedEffect(key1 = Unit) {
        homeScreenViewModel.setEmail(email)
        homeScreenViewModel.initHomeItems()
        homeScreenViewModel.initCartItemsCount()
    }

    LaunchedEffect(key1 = Unit) {
        mainViewModel.uiMessages.collect {
            Log.d("testActors", "5. uiMessages.collect it=$it")
            when(it) {
                is NavigationUIMessages.NavigateTo -> {
                    navHostControllerLambda().navigate(Screen.ProductDetailsScreen.route) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = false }
                    }
                }
                NavigationUIMessages.SkipNavigation -> {
                    Log.d("testActors", "5.2. SkipNavigation : finally navigating it=$it")
                }
                else -> {}
            }
        }
    }

    ChangeBarColors(navigationBarColor = Color.White)

    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()
    val brandLogoSize = remember { mutableStateOf(Int.MAX_VALUE) }

    //When using derivedStateOf{} the screen we recompose only when the condition changes
    val visibleOffsetCondition by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset >= (brandLogoSize.value / 2) }
    }
    val visibleItemCondition by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

    val animatedAppBarBackgroundColor = animateColorAsState(
        targetValue = if ( visibleOffsetCondition || visibleItemCondition ) Orange
        else Transparent,
        animationSpec = tween(1),
    )
    val animatedAppBarContentColor = animateColorAsState(
        targetValue = if ( visibleOffsetCondition || visibleItemCondition ) Color.White
        else Transparent,
        animationSpec = tween(1),
    )

    val animatedAppBarBrandIconColor = animateColorAsState(
        targetValue = if ( visibleOffsetCondition || visibleItemCondition ) Color.Unspecified
        else Transparent,
        animationSpec = tween(1),
    )

    val animatedAppBarElevation = animateDpAsState(
        targetValue = if ( visibleOffsetCondition || visibleItemCondition ) 3.dp else 0.dp,
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

                            //homeScreenViewModel.addProduct(item.id)
//                            coroutineScope.launch {
//                                AppStream.send(NavigateObj(route = Screen.ProductDetailsScreen.route))
//                                mainViewModel.getNavigationState()
//                            }

//                            navHostControllerLambda().navigate("HomeToProductDetails/${homeScreenViewModel.userEmail.value}/${item.id}") {
//                                popUpTo(Screen.HomeScreen.route) { inclusive = false }
//                            }

                            homeScreenStartDestinationRoute.value = "HomeToProductDetails/{email}/{id}"
                            navHostControllerLambda().navigate("HomeToProductDetails/${homeScreenViewModel.userEmail.value}/${item.id}") {
                                //popUpTo(Screen.HomeScreen.route) { inclusive = false }
                            }

                            //previous navigation
//                            navHostControllerLambda().navigate(Screen.ProductDetailsScreen.route) {
//                                popUpTo(Screen.HomeScreen.route) { inclusive = false }
//                            }
                        },
                    ) {
                        Row{

                            //Product Image
                            Box(
                                modifier = Modifier
                                    .weight(2F)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center,
                            ){

                                val painter = rememberImagePainter(data = item.url)

                                val shimmerState = remember { mutableStateOf(false) }
                                shimmerState.value = painter.state is ImagePainter.State.Loading || painter.state is ImagePainter.State.Error

                                Image(
                                    painter = painter,
                                    contentDescription = "ic_product",
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .placeholder(
                                            visible = shimmerState.value,
                                            color = Transparent,
                                            highlight = PlaceholderHighlight.shimmer(LightDarkGray),
                                        ),
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(3F),
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
                            .background(Transparent)
                            .padding(start = 17.dp, top = 70.dp)
                    ) {
                        Surface(
                            elevation = 3.dp,
                            color = Transparent
                        ) {
                            IconButton(
                                onClick = {
                                    homeScreenViewModel.addProductToCart(item)
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
            homeScreenViewModel = homeScreenViewModel,
            animatedAppBarBackgroundColor = animatedAppBarBackgroundColor,
            animatedAppBarContentColor = animatedAppBarContentColor,
            animatedAppBarBrandIconColor = animatedAppBarBrandIconColor,
            animatedAppBarElevation = animatedAppBarElevation,
            onUserProfileClick = {
                //navHostControllerLambda().navigate(Screen.UserDetails.routeWithData(homeScreenViewModel.userEmail.value))
                homeScreenStartDestinationRoute.value = "UserDetailsNavigation"
                navHostControllerLambda().navigate("UserDetailsScreenX/${homeScreenViewModel.userEmail.value}")
                //navHostControllerLambda().navigate("UserDetailsScreenX/meh@ul.com")
            },
            onLikedProductsClick = {
                //navHostControllerLambda().navigate(Screen.FavouriteProductsScreen.routeWithData(homeScreenViewModel.userEmail.value))
                homeScreenStartDestinationRoute.value = "HomeToLikedScreen/{email}"
                navHostControllerLambda().navigate("HomeToLikedScreen/${homeScreenViewModel.userEmail.value}")
            },
            onProductCartClick = {
                //navHostControllerLambda().navigate(Screen.ProductCart.routeWithData(homeScreenViewModel.userEmail.value))
                homeScreenStartDestinationRoute.value = "CartScreenNavigation"
                navHostControllerLambda().navigate("HomeToCartScreen/${homeScreenViewModel.userEmail.value}")
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
    homeScreenViewModel: HomeScreenViewModel,
    animatedAppBarBackgroundColor: State<Color>,
    animatedAppBarContentColor: State<Color>,
    animatedAppBarBrandIconColor: State<Color>,
    animatedAppBarElevation: State<Dp>,
    onUserProfileClick: () -> Unit = {},
    onProductCartClick: () -> Unit = {},
    onLikedProductsClick: () -> Unit = {},
) {
    val count by remember {
        derivedStateOf{
            homeScreenViewModel.cartItemCount.value
        }
    }
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

            IconButton(onClick = onLikedProductsClick) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "ic_Favorite_bt",
                    tint = Color.White
                )
            }

            val offset = 12
            if(count > 0){
                BadgedBox(
                    badge = {
                        Badge(
                            modifier = Modifier
                                .offset(x = -offset.dp, y = offset.dp)
                        ){
                            Text(text = "${homeScreenViewModel.cartItemCount.value}")
                        }
                    },
                ){
                    IconButton(onClick = onProductCartClick) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "ic_shopping_cart",
                            tint = Color.White
                        )
                    }
                }
            }
            else{
                IconButton(onClick = onProductCartClick) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "ic_shopping_cart",
                        tint = Color.White
                    )
                }
            }
        }
    )
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



