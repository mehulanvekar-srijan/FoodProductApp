package com.experiment.foodproductapp.views



import android.graphics.Paint
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
import androidx.compose.ui.graphics.Brush
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
//val  productDetails =  Product(
//    id = 0,
//    url = "https://www.bigbasket.com/media/uploads/p/xxl/40213061_2-coolberg-non-alcoholic-beer-malt.jpg",
//    title = "Coolberg Non Alcoholic Beer - Malt",
//    description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
//    price = 79,
//    count = 0,
////alcohol = 5
//)


@Composable
fun ProductDetailsPage(navHostControllerLambda: () -> NavHostController, homeScreenViewModel: HomeScreenViewModel) {

    val productDetails = homeScreenViewModel.productForDetailPage
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val quantity = remember {mutableStateOf(0)}

    LaunchedEffect(key1 = Unit) {
        homeScreenViewModel.getProductCount(context,productDetails.id,quantity)
    }

    ChangeBarColors(statusColor = Color.White, navigationBarColor = DarkYellow)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkYellow),
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
                        .fillMaxWidth()
                        .fillMaxHeight(.88f)
                        .verticalScroll(scrollState),
                ) {


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(Color.White)
                                .graphicsLayer {
                                    alpha =
                                        1.05f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.0f)
                                    translationY = 0.3f * scrollState.value
                                },

                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = rememberImagePainter(productDetails.url),
                                //painter = painterResource(id = R.drawable.beer),
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth,

                                modifier = Modifier
                                    .padding(top = 60.dp)
                                    .height(350.dp)
                                //.padding(10.dp, top = 60.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = productDetails.title,
                            color = Color.DarkGray,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 26.sp,
                                letterSpacing = 1.sp,
                                fontFamily = titleFontFamily
                            ),
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(start = 40.dp, top = 20.dp)

                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        //item {
                        Text(
                            text = productDetails.description,
                            color = LightDarkGray,
                            style = TextStyle(
                                fontSize = 17.sp,
                                //letterSpacing = 1.sp,
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

                Spacer(modifier = Modifier.padding(top = 20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(end = 20.dp, start = 40.dp),
                    verticalArrangement = Arrangement.Center

                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        //Add
                        Box(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .padding(end = 10.dp),
                            contentAlignment = Alignment.TopEnd
                        ){
                            Surface(
                                color = Color.Transparent
                            ){
                                IconButton(
                                    onClick = {
                                        homeScreenViewModel.incrementProductCount(
                                            context,
                                            productDetails.id,
                                            quantity
                                        )
                                    },
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(
                                                    Orange,
                                                    DarkYellow
                                                )
                                            )
                                        )
                                        .size(width = 35.dp, height = 35.dp)
                                ){
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "",
                                        tint = Color.White,
                                    )
                                }
                            }
                        }

                        //Count Value
                        Box(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .clip(RoundedCornerShape(50)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "" + quantity.value,
                                style = TextStyle(
                                    fontSize = 25.sp,
                                ),
                                modifier = Modifier
                                    .background(DarkYellow)
                                    .padding(start = 20.dp, end = 20.dp)
                            )

                        }

                        //Minus
                        Box(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .padding(start = 10.dp),
                            contentAlignment = Alignment.TopEnd,
                        ){
                            Surface(
                                color = Color.Transparent
                            ){
                                IconButton(
                                    onClick = {
                                        homeScreenViewModel.decrementProductCount(
                                            context,
                                            productDetails.id,
                                            quantity
                                        )
                                    },
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(
                                                    Orange,
                                                    DarkYellow
                                                )
                                            )
                                        )
                                        .size(width = 35.dp, height = 35.dp)
                                ){
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "",
                                        tint = Color.White,
                                    )
                                }
                            }
                        }
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
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_percentage_p),
                            contentDescription = "",
                            modifier = Modifier.fillMaxHeight(.7f)
                        )
                        Text(
                            text = "5" + "% v/v",
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 25.sp,
                                letterSpacing = 1.sp
                            ),
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
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_rupee_2),
                            contentDescription = "",
                            modifier = Modifier.fillMaxHeight(.7f)
                        )

                        Text(
                            text = "Rs. " + productDetails.price,
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 25.sp,
                                letterSpacing = 1.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
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
            if (quantity.value == 0) {
                homeScreenViewModel.addProductToCart(productDetails, context)
                quantity.value++
            }
        }

    )
}

@Composable
fun AppBar(
    navHostControllerLambda: () -> NavHostController,
    onProductCartClick: ()-> Unit = {},
    onProductAddClick:  ()-> Unit = {}
) {

    TopAppBar(
        title = { Text(text = "Beer App", color = DarkYellow) },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = DarkYellow)
            }

        },
        actions = {
            IconButton(onClick =  onProductAddClick ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = DarkYellow
                )

            }

            IconButton(onClick = onProductCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "",
                    tint = DarkYellow
                )
            }
        }
    )
}
