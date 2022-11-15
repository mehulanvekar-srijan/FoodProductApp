//package com.experiment.foodproductapp.views
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.experiment.foodproductapp.R
//import com.experiment.foodproductapp.ui.theme.*
//import com.experiment.foodproductapp.viewmodels.OrderDetailsViewModel
//import java.math.RoundingMode
//import java.text.DecimalFormat
//
//@Preview
//@Composable
//fun Preview4() {
//    val navHostController = rememberNavController()
//    val navHostControllerLambda: () -> NavHostController = {
//
//        navHostController
//    }
//    OrderDescriptionPage(navHostControllerLambda = navHostControllerLambda)
//}
//
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun OrderDescriptionPage(
//    navHostControllerLambda: () -> NavHostController,
//    orderDetailsViewModel: OrderDetailsViewModel = viewModel()
//) {
//
//    ChangeBarColors(navigationBarColor = DarkYellow)
//    val df = DecimalFormat("#.##")
//    df.roundingMode = RoundingMode.DOWN
//
//    Box(
//        modifier = Modifier.fillMaxSize()
//        //background(DarkYellow)
//    ) {
//        BackgroundImageForDescription()
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize(),
//            //horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(10.dp),
//        ) {
//            item {
//                TopAppBar(
//                    title = { Text(text = "Order Details", color = Color.White) },
//                    backgroundColor = Color.Transparent,
//                    elevation = 0.dp,
//                    navigationIcon = {
//                        IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
//                            Icon(
//                                imageVector = Icons.Default.ArrowBack,
//                                contentDescription = "",
//                                tint = Color.White
//                            )
//                        }
//                    },
//                )
//            }
//
//            item {
//                Card(
//
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                        .padding(start = 10.dp, end = 10.dp),
//                    elevation = 5.dp,
//                    shape = RoundedCornerShape(5),
//                ) {
//                    Column(modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                    ) {
//                        Spacer(modifier = Modifier.height(20.dp))
//
//                        Column(modifier = Modifier.padding(15.dp)) {
//                            val item = orderDetailsViewModel.orderDetails
//                            Text(
//                                //text = "# 1",
//                                text = "Order No: #" + item[0].orderId,
//                                //  modifier = Modifier.padding(top = 20.dp, bottom = 15.dp),
//                                fontFamily = titleFontFamily,
//                                fontSize = 24.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.DarkGray,
//                                modifier = Modifier.padding(bottom = 10.dp)
//                            )
//                            Text(
//                                //text = "4 items",
//                                text = "Total Items: " + item.size.toString(),
//                                //modifier = Modifier.padding(bottom = 5.dp),
//                                fontFamily = titleFontFamily,
//                                fontSize = 24.sp,
//                                color = Color.DarkGray,
//                                fontWeight = FontWeight.Bold,
//                            )
//                        }
//
//                        Divider(
//                            color = Color.DarkGray, modifier = Modifier
//                                .fillMaxWidth()
//                                .width(2.dp)
//                        )
//
//                        var sum = 0
//
//                        Row(modifier = Modifier.fillMaxSize()) {
//                            Column(
//                                //modifier = Modifier.padding(15.dp),
//                                verticalArrangement = Arrangement.spacedBy(10.dp),
//                            ) {
//                                Spacer(modifier = Modifier.height(35.dp))
//
//                                for (element in orderDetailsViewModel.orderDetails) {
//
//
//                                    val item = element
//
//
//                                    Row(
//                                        modifier = Modifier.fillMaxWidth(),
//                                    ) {
//
//
//                                            Text(
//                                                //text = "Coolberg Non Alcoholic Beer - Malt" + " (" + 4 + ")",
//                                                text = item.title + " x" + item.count ,
//                                                fontSize = 18.sp,
//                                                fontFamily = descriptionFontFamily,
//                                                fontWeight = FontWeight.Thin,
//                                                modifier = Modifier
//                                                    .padding(start = 15.dp)
//                                                    .fillMaxWidth(.65f)
//
//                                            )
//
//
//                                            Text(
//                                                //text = "Rs. " + 79,
//                                                text = "Rs. " + item.price,
//                                                fontSize = 18.sp,
//                                                fontFamily = descriptionFontFamily,
//                                                fontWeight = FontWeight.Thin,
//                                                modifier = Modifier
//                                                    .padding(start = 15.dp)
//                                                    .fillMaxWidth()
//                                            )
//                                        }
//                                    sum += item.price * item.count
//                                }
//                            }
//                        }
//
//                        Divider(
//                            color = Color.DarkGray, modifier = Modifier
//                                .fillMaxWidth()
//                                .width(2.dp)
//                        )
//                        Spacer(modifier = Modifier.height(50.dp))
//
//                        Row(modifier = Modifier.fillMaxWidth()) {
//                            Text(
//                                text = "Item Total:",
//                                fontFamily = descriptionFontFamily,
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                modifier = Modifier
//                                    .padding(start = 15.dp)
//                                    .fillMaxWidth(.65f)
//                            )
//
//                            Text(
//                                text = "Rs. ${df.format(sum * .82)}",
//                                fontFamily = descriptionFontFamily,
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                modifier = Modifier
//                                    .padding(start = 15.dp)
//                                    .fillMaxWidth()
//
//                            )
//                        }
//
//                        Row(modifier = Modifier.fillMaxWidth()) {
//                            Text(
//                                text = "Tax:",
//                                fontFamily = descriptionFontFamily,
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                modifier = Modifier
//                                    .padding(start = 15.dp)
//                                    .fillMaxWidth(.65f)
//                            )
//
//                            Text(
//                                text = "Rs. ${df.format(sum * .18)}",
//                                fontFamily = descriptionFontFamily,
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                modifier = Modifier
//                                    .padding(start = 15.dp)
//                                    .fillMaxWidth()
//                            )
//                        }
//
//                        Row(modifier = Modifier.fillMaxWidth()) {
//                            Text(
//                                text = "Amount Paid:",
//                                fontFamily = descriptionFontFamily,
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                modifier = Modifier
//                                    .padding(start = 15.dp)
//                                    .fillMaxWidth(.65f)
//                            )
//
//                            Text(
//                                text = "Rs. $sum",
//                                fontFamily = descriptionFontFamily,
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                modifier = Modifier
//                                    .padding(start = 15.dp)
//                                    .fillMaxWidth()
//                            )
//                        }
//                        Spacer(modifier = Modifier.height(20.dp))
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun BackgroundImageForDescription() {
//    Image(
//        painter = painterResource(id = R.drawable.background_yellow_wave),
//        contentDescription = "Background Image",
//        contentScale = ContentScale.Crop,
//        modifier = Modifier.fillMaxSize()
//    )
//}


package com.experiment.foodproductapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.OrderDetailsViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

//@Preview
//@Composable
//fun Preview4() {
//    val navHostController = rememberNavController()
//    val navHostControllerLambda: () -> NavHostController = {
//
//        navHostController
//    }
//    OrderDescriptionPage(navHostControllerLambda = navHostControllerLambda)
//}
//
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun OrderDescriptionPage(
//    navHostControllerLambda: () -> NavHostController,
//  //  orderDetailsViewModel: OrderDetailsViewModel = viewModel()
//) {
//
//    ChangeBarColors(navigationBarColor = Color.Transparent)
//    val df = DecimalFormat("#.##")
//    df.roundingMode = RoundingMode.DOWN
//    BoxWithConstraints(
//        modifier = Modifier
//            .background(Color.White)
//            .fillMaxSize()
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            TopAppBar(
//                title = { Text(text = "Order Details", color = Color.White) },
//                backgroundColor = DarkYellow,
//                elevation = 0.dp,
//                navigationIcon = {
//                    IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "",
//                            tint = Color.White
//                        )
//                    }
//                },
//            )
//
//            Image(
//                painter = rememberImagePainter("https://img.freepik.com/free-photo/glass-bottles-beer-with-glass-ice-dark-background_1150-8899.jpg?w=1800&t=st=1668510784~exp=1668511384~hmac=42d4cfbe3cb90558df3640a8ab42e896db228c61c752648f20bb6e0ceb87b586"),
//                contentDescription = "Background Image",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(.4f)
//            )
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth(.8f)
//                    .fillMaxHeight(.8f)
//                    .background(Color.Blue)
//                    .offset( y = -(60.dp))
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                        .background(Color.Green)
//                ) {
//
//                    Text(text = "hello world",
//                        modifier = Modifier
//                            .background(Color.Red)
//                            //.fillMaxWidth(.8f)
//                    )
//
//                }
//
//            }
//            Row( modifier = Modifier
//                .fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth(.2f)
//                        //.fillMaxHeight()
//                        .fillMaxHeight()
//                        .background(Color.Red)
//                ) {
//                    Text(text = "hello world")
//                }
//            }
//        }
////        Column(
////            modifier = Modifier
////                .fillMaxWidth()
////                //.fillMaxHeight()
////                .fillMaxHeight(.4f)
////                .background(Color.Blue)
////                .padding(top = 70.dp)
////        ) {
////            Text(text = "hello world")
////
////        }
////        Column(
////            modifier = Modifier
////                .fillMaxWidth()
////                //.fillMaxHeight()
////                .fillMaxHeight(.4f)
////                .background(Color.Red)
////                .padding(top = 70.dp)
////        ) {
////            Text(text = "hello world")
////
////        }
//    }
//
//
//}
//
//
//
//
//@Composable
//fun BackgroundImageForDescription() {
//    Image(
//        painter = rememberImagePainter("https://img.freepik.com/free-photo/glass-bottles-beer-with-glass-ice-dark-background_1150-8899.jpg?w=1800&t=st=1668510784~exp=1668511384~hmac=42d4cfbe3cb90558df3640a8ab42e896db228c61c752648f20bb6e0ceb87b586"),
//        contentDescription = "Background Image",
//        contentScale = ContentScale.Crop,
//        modifier = Modifier.fillMaxSize()
//            //.fillMaxHeight(.4f)
//            .height(300.dp)
//    )
//}

@Preview
@Composable
fun Preview4() {
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {

        navHostController
    }
    OrderDescriptionPage(navHostControllerLambda = navHostControllerLambda)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderDescriptionPage(
    navHostControllerLambda: () -> NavHostController,
    orderDetailsViewModel: OrderDetailsViewModel = viewModel()
) {

    ChangeBarColors(statusColor = Color.White, navigationBarColor = Color.White)
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN


    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                title = { Text(text = "Order Details", color = Color.White) },
                backgroundColor = Color.White,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = DarkYellow
                        )
                    }
                },
            )

            Image(
                painter = rememberImagePainter("https://img.freepik.com/free-photo/glass-bottles-beer-with-glass-ice-dark-background_1150-8899.jpg?w=1800&t=st=1668510784~exp=1668511384~hmac=42d4cfbe3cb90558df3640a8ab42e896db228c61c752648f20bb6e0ceb87b586"),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.4f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .fillMaxHeight(.35f)
            ) {

            }

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.85f)
                    .border(1.dp, Color.Black),

                ) {

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.2f)
                        .fillMaxHeight()
                        .background(DarkYellow),
                    //.border(1.dp, Color.Black),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier

                            .size(width = 50.dp, height = 35.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }

            }
        }

    }
}

