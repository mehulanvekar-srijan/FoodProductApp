package com.experiment.foodproductapp.views

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch
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

    val context = LocalContext.current

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

    val animatedAppBarBrandIconColor = animateColorAsState(
        targetValue = if((listState.firstVisibleItemScrollOffset >= (brandLogoSize.value/2)) || (listState.firstVisibleItemIndex > 0)) Color.Unspecified
        else Color.Transparent,
        animationSpec = tween(1),
    )

    val animatedAppBarElevation = animateDpAsState(
        targetValue = if((listState.firstVisibleItemScrollOffset >= (brandLogoSize.value/2)) || (listState.firstVisibleItemIndex > 0)) 3.dp
        else 0.dp,
        animationSpec = tween(1),
    )

    Box(modifier = Modifier.fillMaxSize()){

        //Background Image
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

            //Brand Logo
            item {
                BrandLogo(listState = listState,brandLogoSize = brandLogoSize)
            }

            //Products
            items(items = homeScreenViewModel.productsList) { item ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                ){
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        elevation = 5.dp,
                        shape = RoundedCornerShape(
                            bottomStart = 40.dp,
                            topStart = 3.dp,
                            topEnd = 3.dp,
                            bottomEnd = 3.dp,
                        ),
                        onClick = {
                        },
                    ) {
                        Row {
                            val liked = rememberSaveable { mutableStateOf(false) }

                            //Product Image
                            Box {
                                Image(
                                    painter = rememberImagePainter(item.url),
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit,
                                    alignment = Alignment.CenterStart,
                                    modifier = Modifier.padding(8.dp),
                                )
//                                IconButton(onClick = { liked.value = !liked.value }) {
//                                    if (liked.value){
//                                        Icon(
//                                            imageVector = Icons.Outlined.Favorite,
//                                            contentDescription = "",
//                                            tint = Color.Red
//                                        )
//                                    }
//                                    else{
//                                        Icon(
//                                            imageVector = Icons.Outlined.FavoriteBorder,
//                                            contentDescription = "",
//                                        )
//                                    }
//                                }
                            }

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceEvenly,
                            ){
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

                                Text( // Price
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis,
                                    text = "MRP:Rs ${item.price}",
                                    fontFamily = descriptionFontFamily,
                                    color = LightDarkGray,
                                )

                            }
                        }
                    }

                    Box( //left Middle Add Icon
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 10.dp)
                            .offset(x = -12.dp,y = 70.dp),
                    ) {
                        Surface(
                            elevation = 3.dp,
                            color = Color.Transparent
                        ){
                            IconButton(
                                onClick = {
                                    homeScreenViewModel.addProductToCart(item,context)
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                LightPink, DarkPink
                                            )
                                        )
                                    )
                                    .size(width = 30.dp, height = 30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "",
                                    tint = Color.White,
                                )
                            }
                        }
                    }

                }

            }

            //Space
            item{ Spacer(modifier = Modifier.padding(5.dp)) }
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
            }
        )
    }

//    val count = remember{ mutableStateOf(0) }
//    SideEffect { Log.d("testRecomp", "HomeScreenPage : ${count.value++}") }
}

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.background_home_view),
        contentDescription = "Background Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

//    val count = remember{ mutableStateOf(0) }
//    SideEffect { Log.d("testRecomp", "BackgroundImage : ${count.value++}") }
}

@Composable
fun AppBar(
    animatedAppBarBackgroundColor: State<Color>,
    animatedAppBarContentColor: State<Color>,
    animatedAppBarBrandIconColor: State<Color>,
    animatedAppBarElevation: State<Dp>,
    onUserProfileClick: ()->Unit = {},
    onProductCartClick: ()->Unit = {},
) {
    TopAppBar(
        title = { Text(text = "Beer App", color = animatedAppBarContentColor.value) },
        backgroundColor = animatedAppBarBackgroundColor.value,
        elevation = animatedAppBarElevation.value,
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_beer_cheers),
                contentDescription = "",
                tint = animatedAppBarBrandIconColor.value,
                modifier = Modifier.padding(start = 5.dp)
            )
        },
        actions = {
            IconButton(onClick = onUserProfileClick) {
                Icon(imageVector = Icons.Default.ManageAccounts, contentDescription = "", tint = Color.White)
            }

            IconButton(onClick = onProductCartClick) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "", tint = Color.White)
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
//    val count = remember{ mutableStateOf(0) }
//    SideEffect { Log.d("testRecomp", "BrandLogo: ${count.value++}") }
}


@Preview(showBackground = true)
@Composable
fun Prev() {
    val navHostController = rememberNavController()

    FoodProductAppTheme {
        HomeScreenPage(email = "meh@ul.com", navHostControllerLambda = { navHostController })
    }
}
