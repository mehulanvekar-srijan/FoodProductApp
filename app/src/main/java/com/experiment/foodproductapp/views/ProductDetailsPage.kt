package com.experiment.foodproductapp.views



import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*

import androidx.compose.runtime.saveable.rememberSaveable


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource


import androidx.compose.ui.text.TextStyle

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

import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.ui.theme.*

import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel

//@Preview
//@Composable
//fun Preview() {
//    val navHostController = rememberNavController()
//    ProductDetailsPage({navHostController },viewModel())
//}
//
//
//@Composable
//fun ProductDetailsPage(navHostControllerLambda: () -> NavHostController, homeScreenViewModel: HomeScreenViewModel) {
//
//    val productDetails = homeScreenViewModel.productForDetailPage
//    val context = LocalContext.current
//    val scrollState = rememberScrollState()
//
//    ChangeBarColors(statusColor = Color.White, navigationBarColor = Orange)
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Orange),
//    ) {
//
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(.87f)
//                    .clip(RoundedCornerShape(bottomStartPercent = 45))
//                    .background(Color.White)
//                    //.verticalScroll(scrollState),
//
//                ) {
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(.88f)
//                        .verticalScroll(scrollState),
//                ) {
//
//
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight()
//                                .background(Color.White)
//                                .graphicsLayer {
//                                    alpha =
//                                        1f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.0f)
//                                    translationY = 0.3f * scrollState.value
//                                },
//
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Image(
//                                //painter = rememberImagePainter(productDetails!!.url),
//                                painter = painterResource(id = R.drawable.beer),
//                                contentDescription = "",
//                                contentScale = ContentScale.FillWidth,
//                                modifier = Modifier
//                                    .padding(top = 60.dp)
//                                //.padding(10.dp, top = 60.dp)
//                            )
//                        }
//                    }
//
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    ) {
//                        Text(
//                            text = productDetails!!.title,
//                            color = Color.Black,
//                            style = TextStyle(
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 30.sp,
//                                letterSpacing = 1.sp
//                            ),
//                            textAlign = TextAlign.Start,
//                            modifier = Modifier
//                                .padding(start = 40.dp)
//
//                        )
//                    }
//                    Spacer(modifier = Modifier.padding(top = 20.dp))
//
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            //.fillMaxHeight(.55f)
//                            //.height(120.dp)
//                    ) {
//                        //item {
//                        Text(
//                            text = productDetails!!.description,
//                            color = Color.Black,
//                            style = TextStyle(
//                                fontSize = 20.sp,
//                                letterSpacing = 1.sp
//                            ),
//                            //maxLines = 5,
//                            overflow = TextOverflow.Clip,
//                            textAlign = TextAlign.Justify,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 40.dp, end = 20.dp)
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.padding(top = 10.dp))
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceAround,
//                ) {
//                    Column(
//                        modifier = Modifier
//                        //.border(border = BorderStroke(3.dp, Color.White))
//                        //.clip(RoundedCornerShape(bottomStartPercent = 40, topEndPercent = 40))
//                        //.clip(RoundedCornerShape(bottomStartPercent = 30, topEndPercent = 30, topStartPercent = 30, bottomEndPercent = 30))
//                        // .background(Color.White)
//                    ) {
//                        Text(
//                            text = "5" + "%",
//                            color = Color.White,
//                            style = TextStyle(
//                                // fontWeight = FontWeight.,
//                                fontSize = 40.sp,
//                                letterSpacing = 1.sp
//                            ),
//                            // fontSize = 30.dp,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .align(Alignment.CenterHorizontally)
//                        )
//                        Text(
//                            text = "Alcohol",
//                            color = Color.White,
//                            style = TextStyle(
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 20.sp,
//                                letterSpacing = 1.sp
//                            ),
//                            modifier = Modifier
//                                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
//                            //textAlign = TextAlign.Center,
//                        )
//                    }
//
//
//                    Column(
//                        modifier = Modifier
//                        //.border(border = BorderStroke(3.dp, Color.White))
//                        //.clip(RoundedCornerShape(bottomStartPercent = 40, topEndPercent = 40))
//                        //.clip(RoundedCornerShape(bottomStartPercent = 30, topEndPercent = 30, topStartPercent = 30, bottomEndPercent = 30))
//                        // .background(Color.White)
//                    ) {
//                        Text(
//                            text = "$" + productDetails!!.price,
//                            color = Color.White,
//                            style = TextStyle(
//                                //fontWeight = FontWeight.Bold,
//                                fontSize = 40.sp,
//                                letterSpacing = 1.sp
//                            ),
//                            //textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .padding(start = 10.dp, end = 10.dp)
//                        )
//
//                        Text(
//                            text = "Price",
//                            color = Color.White,
//                            style = TextStyle(
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 20.sp,
//                                letterSpacing = 1.sp
//                            ),
//                            // fontSize = 30.dp,
//                            modifier = Modifier
//                                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
//                                .align(Alignment.CenterHorizontally)
//                        )
//                    }
//                }
//            }
//        }
//
//        }
//
//    AppBar(
//        navHostControllerLambda = navHostControllerLambda,
//        onProductCartClick = {
//            homeScreenViewModel.addProductToCart(productDetails!!,context)
//            homeScreenViewModel.navigateToProductCart(navHostControllerLambda())
//        }
//    )
//}



//@Composable
//fun AppBar(
//    navHostControllerLambda: () -> NavHostController,
//    onProductCartClick: ()-> Unit = {},
//) {
//    val liked = rememberSaveable { mutableStateOf(false) }
//
//    TopAppBar(
//        title = { Text(text = "Beer App", color = Color.Black) },
//        backgroundColor = Color.Transparent,
//        elevation = 0.dp,
//        navigationIcon = {
//            IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
//                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.Black)
//            }
//
//        },
//        actions = {
////            IconButton(onClick = {liked.value = !liked.value}) {
////                if (liked.value){
////                    Icon(
////                        imageVector = Icons.Outlined.Favorite,
////                        contentDescription = "",
////                        tint = Color.Red
////                    )
////                }
////                else{
////                    Icon(
////                        imageVector = Icons.Outlined.FavoriteBorder,
////                        contentDescription = "",
////                    )
////                }
////            }
//
//            IconButton(onClick = onProductCartClick) {
//                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "", tint = Color.Black)
//
//            }
//        }
//    )
//}
//

//@Composable
//fun ProductDetailsPage(navHostControllerLambda: () -> NavHostController, homeScreenViewModel: HomeScreenViewModel) {
//
//    val productDetails = homeScreenViewModel.productForDetailPage
//    val context = LocalContext.current
//    //val scrollState = rememberScrollState()
//
//    ChangeBarColors(statusColor = Color.White, navigationBarColor = Orange)
//
//    val lazyListState = rememberLazyListState()
//    val visibility by remember {
//        derivedStateOf {
//            when {
//                lazyListState.layoutInfo.visibleItemsInfo.isNotEmpty() && lazyListState.firstVisibleItemIndex == 0 -> {
//                    val imageSize = lazyListState.layoutInfo.visibleItemsInfo[0].size
//                    val scrollOffset = lazyListState.firstVisibleItemScrollOffset
//
//                    scrollOffset / imageSize.toFloat()
//                }
//                else                                                                                               -> 1f
//            }
//        }
//    }
//    val firstItemTranslationY by remember {
//        derivedStateOf {
//            when {
//                lazyListState.layoutInfo.visibleItemsInfo.isNotEmpty() && lazyListState.firstVisibleItemIndex == 0 -> lazyListState.firstVisibleItemScrollOffset * .6f
//                else                                                                                               -> 0f
//            }
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Orange),
//    ) {
//
//        Image(
//            painter = rememberImagePainter(productDetails!!.url),
//            //painter = painterResource(id = R.drawable.beer),
//            contentDescription = "",
//            contentScale = ContentScale.FillWidth,
//            modifier = Modifier
//                .padding(top = 60.dp)
//            //.padding(10.dp, top = 60.dp)
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(.87f)
//                    .clip(RoundedCornerShape(bottomStartPercent = 45))
//                    .background(Color.White)
//                //.verticalScroll(scrollState),
//
//            ) {
//                LazyColumn(state = lazyListState) {
//                    item {
//
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight(.88f)
//                        ) {
//
//
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .fillMaxHeight()
//                                        .background(Color.White)
//                                        .graphicsLayer {
//                                        alpha = 1f - visibility
//                                        translationY = firstItemTranslationY
//                                    },
//
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Image(
//                                        painter = rememberImagePainter(productDetails!!.url),
//                                        //painter = painterResource(id = R.drawable.beer),
//                                        contentDescription = "",
//                                        contentScale = ContentScale.FillWidth,
//                                        modifier = Modifier
//                                            .padding(top = 60.dp)
//                                        //.padding(10.dp, top = 60.dp)
//                                    )
//                                }
//                            }
//
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                            ) {
//                                Text(
//                                    text = productDetails!!.title,
//                                    color = Color.Black,
//                                    style = TextStyle(
//                                        fontWeight = FontWeight.Bold,
//                                        fontSize = 30.sp,
//                                        letterSpacing = 1.sp
//                                    ),
//                                    textAlign = TextAlign.Start,
//                                    modifier = Modifier
//                                        .padding(start = 40.dp)
//
//                                )
//                            }
//                            Spacer(modifier = Modifier.padding(top = 20.dp))
//
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                //.fillMaxHeight(.55f)
//                                //.height(120.dp)
//                            ) {
//                                //item {
//                                Text(
//                                    text = productDetails!!.description,
//                                    color = Color.Black,
//                                    style = TextStyle(
//                                        fontSize = 20.sp,
//                                        letterSpacing = 1.sp
//                                    ),
//                                    //maxLines = 5,
//                                    overflow = TextOverflow.Clip,
//                                    textAlign = TextAlign.Justify,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(start = 40.dp, end = 20.dp)
//                                )
//                            }
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.padding(top = 10.dp))
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceAround,
//                    ) {
//                        Column(
//                            modifier = Modifier
//                            //.border(border = BorderStroke(3.dp, Color.White))
//                            //.clip(RoundedCornerShape(bottomStartPercent = 40, topEndPercent = 40))
//                            //.clip(RoundedCornerShape(bottomStartPercent = 30, topEndPercent = 30, topStartPercent = 30, bottomEndPercent = 30))
//                            // .background(Color.White)
//                        ) {
//                            Text(
//                                text = "5" + "%",
//                                color = Color.White,
//                                style = TextStyle(
//                                    // fontWeight = FontWeight.,
//                                    fontSize = 40.sp,
//                                    letterSpacing = 1.sp
//                                ),
//                                // fontSize = 30.dp,
//                                textAlign = TextAlign.Center,
//                                modifier = Modifier
//                                    .align(Alignment.CenterHorizontally)
//                            )
//                            Text(
//                                text = "Alcohol",
//                                color = Color.White,
//                                style = TextStyle(
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 20.sp,
//                                    letterSpacing = 1.sp
//                                ),
//                                modifier = Modifier
//                                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
//                                //textAlign = TextAlign.Center,
//                            )
//                        }
//
//
//                        Column(
//                            modifier = Modifier
//                            //.border(border = BorderStroke(3.dp, Color.White))
//                            //.clip(RoundedCornerShape(bottomStartPercent = 40, topEndPercent = 40))
//                            //.clip(RoundedCornerShape(bottomStartPercent = 30, topEndPercent = 30, topStartPercent = 30, bottomEndPercent = 30))
//                            // .background(Color.White)
//                        ) {
//                            Text(
//                                text = "$" + productDetails!!.price,
//                                color = Color.White,
//                                style = TextStyle(
//                                    //fontWeight = FontWeight.Bold,
//                                    fontSize = 40.sp,
//                                    letterSpacing = 1.sp
//                                ),
//                                //textAlign = TextAlign.Center,
//                                modifier = Modifier
//                                    .padding(start = 10.dp, end = 10.dp)
//                            )
//
//                            Text(
//                                text = "Price",
//                                color = Color.White,
//                                style = TextStyle(
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 20.sp,
//                                    letterSpacing = 1.sp
//                                ),
//                                // fontSize = 30.dp,
//                                modifier = Modifier
//                                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
//                                    .align(Alignment.CenterHorizontally)
//                            )
//                        }
//                    }
//                }
//            }
//
//        }
//    }
//
//    AppBar(
//        navHostControllerLambda = navHostControllerLambda,
//        onProductCartClick = {
//            homeScreenViewModel.addProductToCart(productDetails!!,context)
//            homeScreenViewModel.navigateToProductCart(navHostControllerLambda())
//        }
//    )
//}
//
//
//



@Composable
fun AppBar(
    navHostControllerLambda: () -> NavHostController,
    onProductCartClick: ()-> Unit = {},
    onProductAddClick:  ()-> Unit = {}
) {
    val liked = rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = "Beer App", color = Color.Black) },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.Black)
            }

        },
        actions = {
            IconButton(onClick =  onProductAddClick ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = Color.Red
                    )

            }

            IconButton(onClick = onProductCartClick) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "", tint = Color.Black)

            }
        }
    )
}


val productDetails = Product(
    id = 1,
    url = "https://www.bigbasket.com/media/uploads/p/xxl/40122150_2-coolberg-beer-mint-non-alcoholic.jpg",
    title = "Coolberg Non Alcoholic Beer - Mint",
    description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
    price = 79,
)


//
@Composable
fun ProductDetailsPage(navHostControllerLambda: () -> NavHostController, homeScreenViewModel: HomeScreenViewModel) {

    val productDetails = homeScreenViewModel.productForDetailPage
    val context = LocalContext.current

    ChangeBarColors(statusColor = Color.White, navigationBarColor = Orange)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Orange),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

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
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = rememberImagePainter(productDetails!!.url),
                        //painter = painterResource(id = R.drawable.beer),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .padding(top = 60.dp)
                        //.padding(10.dp, top = 60.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = productDetails!!.title,
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            letterSpacing = 1.sp,
                            fontFamily = titleFontFamily
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 40.dp)

                    )
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.55f)
                    //.height(120.dp)
                ) {
                    item {
                        Text(
                            text = productDetails!!.description,
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 20.sp,
                                letterSpacing = 1.sp,
                                fontFamily = descriptionFontFamily
                            ),
                            //maxLines = 5,
                            overflow = TextOverflow.Clip,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 40.dp, end = 20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(top = 10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Column(
                        modifier = Modifier
                        //.border(border = BorderStroke(3.dp, Color.White))
                        //.clip(RoundedCornerShape(bottomStartPercent = 40, topEndPercent = 40))
                        //.clip(RoundedCornerShape(bottomStartPercent = 30, topEndPercent = 30, topStartPercent = 30, bottomEndPercent = 30))
                        // .background(Color.White)
                    ) {
                        Text(
                            text = "5" + "%",
                            color = Color.White,
                            style = TextStyle(
                                // fontWeight = FontWeight.,
                                fontSize = 40.sp,
                                letterSpacing = 1.sp
                            ),
                            // fontSize = 30.dp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "Alcohol",
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                letterSpacing = 1.sp
                            ),
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                            //textAlign = TextAlign.Center,
                        )
                    }


                    Column(
                        modifier = Modifier
                        //.border(border = BorderStroke(3.dp, Color.White))
                        //.clip(RoundedCornerShape(bottomStartPercent = 40, topEndPercent = 40))
                        //.clip(RoundedCornerShape(bottomStartPercent = 30, topEndPercent = 30, topStartPercent = 30, bottomEndPercent = 30))
                        // .background(Color.White)
                    ) {
                        Text(
                            text = "$" + productDetails!!.price,
                            color = Color.White,
                            style = TextStyle(
                                //fontWeight = FontWeight.Bold,
                                fontSize = 40.sp,
                                letterSpacing = 1.sp
                            ),
                            //textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                        )

                        Text(
                            text = "Price",
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                letterSpacing = 1.sp
                            ),
                            // fontSize = 30.dp,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
    AppBar(
        navHostControllerLambda = navHostControllerLambda,
        onProductCartClick = {

            homeScreenViewModel.navigateToProductCart(navHostControllerLambda())
        },
        onProductAddClick = {
            homeScreenViewModel.addProductToCart(productDetails!!,context)
        }
    )
}



