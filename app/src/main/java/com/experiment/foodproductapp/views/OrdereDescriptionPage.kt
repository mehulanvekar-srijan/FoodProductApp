package com.experiment.foodproductapp.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


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
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.OrderDetailsViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter





@Preview
@Composable
fun Preview4() {
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {

        navHostController
    }
    OrderDescriptionPage(navHostControllerLambda = navHostControllerLambda)
}


@Composable
fun OrderDescriptionPage(
    navHostControllerLambda: () -> NavHostController,
    orderDetailsViewModel: OrderDetailsViewModel = viewModel()
) {

    ChangeBarColors(statusColor = Color.White, navigationBarColor = Color.White)
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN
    var sum = 0


    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(0f to Color.Gray, 1000f to Color.White)
                )
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
                    .fillMaxHeight(.45f)
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
                    .shadow(20.dp)
                    .background(Color.White)
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.85f),

                //.border(1.dp, Color.Black),

            ) {
                Column(modifier = Modifier.padding(15.dp)) {
                    val item = orderDetailsViewModel.orderDetails
                    Text(
                        text = "Order No: #" + item[0].orderId,
                        fontFamily = titleFontFamily,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Total Items: " + item.size.toString(),
                        fontFamily = titleFontFamily,
                        fontSize = 22.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Divider(
                    color = Color.LightGray, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp)
                        .width(2.dp)

                )
                val lazyListState = rememberLazyListState()
                //Spacer(modifier = Modifier.height(35.dp))
                LazyColumn(
                    modifier = Modifier.simpleVerticalScrollbar(lazyListState),
                    state = lazyListState
                ) {
                    items(items = orderDetailsViewModel.orderDetails) { item ->
                        Spacer(modifier = Modifier.height(35.dp))
                        //for (element in orderDetailsViewModel.orderDetails) {
                        //   val item = element

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                            //.padding(top = 25.dp),
                        ) {


                            Text(
                                text = item.title + " x" + item.count,
                                fontSize = 18.sp,
                                fontFamily = descriptionFontFamily,
                                fontWeight = FontWeight.Thin,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth(.65f)

                            )


                            Text(
                                text = "Rs. " + item.price * item.count,
                                fontSize = 18.sp,
                                fontFamily = descriptionFontFamily,
                                fontWeight = FontWeight.Thin,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth()
                            )
                            sum += item.price * item.count

                        }

                    }

                    item {
                        Spacer(modifier = Modifier.height(3.dp))

                        Divider(
                            color = Color.LightGray, modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp, top = 5.dp)
                                .width(2.dp)
                        )
                        Spacer(modifier = Modifier.height(35.dp))
                    }
                    // Log.d("CheckSum", "OrderDescriptionPage outside lazy: Sum is: $sum")

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            //Log.d("CheckSum", "OrderDescriptionPage: Sum is: $sum")

                            Text(
                                text = "Item Total:",
                                fontFamily = descriptionFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Thin,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth(.6f)
                            )

                            Text(
                                text = "Rs. ${df.format(sum * .82)}",
                                fontFamily = descriptionFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Thin,
                                modifier = Modifier
                                    .padding(start = 15.dp, end = 15.dp)
                                    .fillMaxWidth()

                            )
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            Text(
                                text = "Tax:",
                                fontFamily = descriptionFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth(.6f)
                            )

                            Text(
                                text = "Rs. ${df.format(sum * .18)}",
                                fontFamily = descriptionFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            Text(
                                text = "Amount Paid:",
                                fontFamily = descriptionFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth(.6f)
                            )

                            Text(
                                text = "Rs. ${sum}",
                                fontFamily = descriptionFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }


                    //Log.d("CheckSum", "OrderDescriptionPage end of lazy: Sum is: $sum")

                }
                Log.d("CheckSum", "OrderDescriptionPage outside lazy: Sum is: $sum")
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
                        onClick = {
                                  orderDetailsViewModel.navigateToHomeScreenPage(navHostController = navHostControllerLambda())
                        },
                        modifier = Modifier

                            .size(width = 40.dp, height = 35.dp)
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


@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 4.dp
): Modifier {

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = Color.LightGray,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                //alpha = alpha
            )
        }
    }
}
