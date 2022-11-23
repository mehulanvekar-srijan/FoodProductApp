package com.experiment.foodproductapp.views


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

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
import com.experiment.foodproductapp.ui.theme.*

import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel

@Preview
@Composable
fun Preview() {
    val navHostController = rememberNavController()
    ProductDetailsPage({ navHostController }, viewModel())
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProductDetailsPage(
    navHostControllerLambda: () -> NavHostController,
    homeScreenViewModel: HomeScreenViewModel
) {

    val context = LocalContext.current
    val productDetails = homeScreenViewModel.productForDetailPage
    val scrollState = rememberScrollState()
    val quantity = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        homeScreenViewModel.getProductCount(productDetails.value.id, quantity)
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
                                painter = rememberImagePainter(productDetails.value.url),
                                //painter = painterResource(id = R.drawable.beer),
                                contentDescription = "ic_product_image",
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
                            text = productDetails.value.title,
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
                        if (scrollState.value == 0) {
                            Text(
                                text = productDetails.value.description,
                                color = LightDarkGray,
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    //letterSpacing = 1.sp,
                                    fontFamily = descriptionFontFamily
                                ),
                                maxLines = 4,

                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 40.dp, end = 20.dp)
                            )
                        } else {
                            Text(
                                text = productDetails.value.description,
                                color = LightDarkGray,
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    //letterSpacing = 1.sp,
                                    fontFamily = descriptionFontFamily
                                ),
                                overflow = TextOverflow.Ellipsis,
                                //overflow = TextOverflow.Clip,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 40.dp, end = 20.dp)
                            )
                        }
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
                        ) {
                            Surface(
                                color = Color.Transparent
                            ) {
                                IconButton(
                                    onClick = {
                                        homeScreenViewModel.incrementProductCount(
                                            productDetails.value.id,
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
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "ic_add_count_bt",
                                        tint = Color.White,
                                    )
                                }
                            }
                        }

                        //Count Value
                        Text(
                            text = quantity.value.toString(),
                            style = TextStyle(
                                fontSize = 25.sp,
                            ),
                            color = DarkYellow,
                            modifier = Modifier
                                .padding(start = 5.dp, end = 5.dp)
                        )

                        //}

                        //Minus
                        Box(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .padding(start = 10.dp),
                            contentAlignment = Alignment.TopEnd,
                        ) {
                            Surface(
                                color = Color.Transparent
                            ) {
                                IconButton(
                                    onClick = {
                                        homeScreenViewModel.decrementProductCount(
                                            productDetails.value.id,
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
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "ic_minus_count_bt",
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
                            painter = painterResource(id = R.drawable.ic_percentage_percent),
                            contentDescription = "ic_percentage",
                            modifier = Modifier.fillMaxHeight(.5f)
                        )
                        Text(
                            //text = stringResource(id = R.string.five_alcohol_string),
                            text = "" + productDetails.value.alcohol + stringResource(id = R.string.five_alcohol_string),
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                letterSpacing = 1.sp
                            ),
                            fontFamily = descriptionFontFamily,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
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
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_rupee_sv),
                            contentDescription = "ic_rupees",
                            //colorFilter = ColorFilter.tint(color = Color.White),
                            modifier = Modifier.fillMaxHeight(.5f)

                        )

                        Text(
                            text = stringResource(id = R.string.rs_dot_string) + " " + productDetails.value.price,
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                letterSpacing = 1.sp
                            ),
                            fontFamily = descriptionFontFamily,
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
    )
}

@Composable
fun AppBar(
    navHostControllerLambda: () -> NavHostController,
    onProductCartClick: () -> Unit = {},
) {

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
            IconButton(onClick = onProductCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "ic_product_cart_bt",
                    tint = DarkYellow
                )
            }
        }
    )
}
