package com.experiment.foodproductapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.experiment.foodproductapp.viewmodels.OrderDetailsViewModel

@Preview
@Composable
fun Preview2() {
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {

        navHostController
    }
    OrderDetails("sahil@test.com", navHostControllerLambda = navHostControllerLambda)
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun OrderDetails(
    email: String?,
    navHostControllerLambda: () -> NavHostController,
    orderDetailsViewModel: OrderDetailsViewModel = viewModel(),
) {

    ChangeBarColors(navigationBarColor = DarkYellow)

    LaunchedEffect(key1 = Unit) {
        orderDetailsViewModel.setEmail(email)
        //orderDetailsViewModel.email.value = email.toString()
        orderDetailsViewModel.finalList.clear()
//        orderDetailsViewModel.priceList.clear()
        orderDetailsViewModel.fetchOrderList()
    }

    if (orderDetailsViewModel.finalList.isNotEmpty()) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

            val height = (maxHeight / 100f) * 15
            //Background Image
            BackgroundImage1()

            //top bar
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.order_details_string),
                        color = Color.White
                    )
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "ic_arrow_back_bt",
                            tint = Color.White
                        )
                    }
                },
            )

            //Main Column
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(items = orderDetailsViewModel.finalList.reversed()) { listOfOrders ->

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height)
                                .padding(start = 15.dp, end = 15.dp),
                            elevation = 5.dp,
                            shape = RoundedCornerShape(10),
                            onClick = {
                                orderDetailsViewModel.addOrderId(listOfOrders[0].orderId)
                                navHostControllerLambda().navigate(Screen.OrderDescriptionPage.route) {
                                    popUpTo(Screen.OrderDetails.route) { inclusive = false }
                                }
                            },
                        ) {
                            Row {
                                Column(
                                    modifier = Modifier
                                        .padding(start = 30.dp)
                                        .fillMaxWidth(.5F)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.order_no_string) + " " + listOfOrders[0].orderId,
                                        modifier = Modifier.padding(bottom = 10.dp),
                                        overflow = TextOverflow.Ellipsis,
                                        fontFamily = titleFontFamily,
                                        fontSize = 19.sp,
                                    )
                                    Text(
                                        text = stringResource(id = R.string.total_items_string) + " " + listOfOrders.size.toString(),
                                        fontFamily = titleFontFamily,
                                        fontSize = 19.sp,
                                    )
                                }
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Image(
                                        painter = rememberImagePainter(listOfOrders[0].url),
                                        contentDescription = "ic_product_at_0_image",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(.7f)
                                    )

                                    Text(
                                        //text = stringResource(id = R.string.rs_string) + " " + orderDetailsViewModel.priceList[listOfOrders[0].orderId - 1],
                                        text = stringResource(id = R.string.rs_string) + " " + orderDetailsViewModel.priceMap[listOfOrders[0].orderId],
                                        fontFamily = titleFontFamily,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    else {

        Box(modifier = Modifier.fillMaxSize()) {
            BackgroundImage1()
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.order_details_string),
                        color = Color.White
                    )
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "ic_arrow_back_bt",
                            tint = Color.White
                        )
                    }
                },
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
//                    painter = rememberImagePainter("https://www.denmakers.in/img/no-product-found.png"),
                    painter = rememberImagePainter(R.drawable.no_product_found),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.45f)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.there_are_no_items_in_the_cart_string),
                    fontWeight = FontWeight.Thin,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = descriptionFontFamily,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 5.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {
                OutlinedButton(
                    onClick = {
                        navHostControllerLambda().navigate(Screen.HomeScreen.routeWithData(orderDetailsViewModel.userEmail.value)) {
                            popUpTo(Screen.HomeScreen.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp),

                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = DarkYellow
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.click_here_to_order_string),
                        fontSize = 20.sp, color = Color.Black
                    )
                }
            }

        }
    }
}

@Composable
fun BackgroundImage1() {
    Image(
        painter = painterResource(id = R.drawable.background_yellow_wave),
        contentDescription = "ic_background_image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}