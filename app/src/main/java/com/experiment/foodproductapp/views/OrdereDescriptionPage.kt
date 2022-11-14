package com.experiment.foodproductapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.OrderDetailsViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

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

    ChangeBarColors(navigationBarColor = DarkYellow)
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN

    Box(
        modifier = Modifier.fillMaxSize()
        //background(DarkYellow)
    ) {
        BackgroundImageForDescription()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            //horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                TopAppBar(
                    title = { Text(text = "Order Details", color = Color.White) },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    },
                )
            }

            item {
                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 10.dp, end = 10.dp),
                    elevation = 5.dp,
                    shape = RoundedCornerShape(5),
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        Column(modifier = Modifier.padding(15.dp)) {
                            val item = orderDetailsViewModel.orderDetails
                            Text(
                                //text = "# 1",
                                text = "Order No: #" + item[0].orderId,
                                //  modifier = Modifier.padding(top = 20.dp, bottom = 15.dp),
                                fontFamily = titleFontFamily,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            Text(
                                //text = "4 items",
                                text = "Total Items: " + item.size.toString(),
                                //modifier = Modifier.padding(bottom = 5.dp),
                                fontFamily = titleFontFamily,
                                fontSize = 24.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        Divider(
                            color = Color.DarkGray, modifier = Modifier
                                .fillMaxWidth()
                                .width(2.dp)
                        )

                        var sum = 0

                        Row(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier.padding(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Spacer(modifier = Modifier.height(35.dp))

                                for (element in orderDetailsViewModel.orderDetails) {


                                    val item = element


                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {

                                        Column(
                                            modifier = Modifier
                                                //.padding(start = 15.dp)
                                                .fillMaxWidth(.65F)

                                        ) {
                                            Text(
                                                //text = "Coolberg Non Alcoholic Beer - Malt" + " (" + 4 + ")",
                                                text = item.title + " x" + item.count ,
                                                fontSize = 18.sp,
                                                fontFamily = descriptionFontFamily,
                                                fontWeight = FontWeight.Thin,
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                //text = "Rs. " + 79,
                                                text = "Rs. " + item.price,
                                                fontSize = 18.sp,
                                                textAlign = TextAlign.End,
                                                fontFamily = descriptionFontFamily,
                                                fontWeight = FontWeight.Thin,
                                            )
                                        }
                                    }
                                    sum += item.price * item.count
                                }
                            }
                        }

                        Divider(
                            color = Color.DarkGray, modifier = Modifier
                                .fillMaxWidth()
                                .width(2.dp)
                        )
                        Spacer(modifier = Modifier.height(50.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Item Total:",
                                fontFamily = descriptionFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth(.65f)
                            )

                            Text(
                                text = "Rs. ${df.format(sum * .82)}",
                                fontFamily = descriptionFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth()

                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Tax:",
                                fontFamily = descriptionFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth(.65f)
                            )

                            Text(
                                text = "Rs. ${df.format(sum * .18)}",
                                fontFamily = descriptionFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth()
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Amount Paid:",
                                fontFamily = descriptionFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth(.65f)
                            )

                            Text(
                                text = "Rs. $sum",
                                fontFamily = descriptionFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun BackgroundImageForDescription() {
    Image(
        painter = painterResource(id = R.drawable.background_yellow_wave),
        contentDescription = "Background Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}