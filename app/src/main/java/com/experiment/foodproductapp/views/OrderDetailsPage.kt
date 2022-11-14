package com.experiment.foodproductapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.HelpCenter
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.LiveHelp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.OrderDetailsViewModel

@Preview
@Composable
fun Preview2() {
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {

        navHostController
    }
    OrderDetails("romi@romi.com", navHostControllerLambda = navHostControllerLambda)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderDetails(
    email: String?,
    navHostControllerLambda: () -> NavHostController,
    orderDetailsViewModel: OrderDetailsViewModel = viewModel(),
) {
    val context = LocalContext.current

    ChangeBarColors(navigationBarColor = Color.White)

    val interactionSource = MutableInteractionSource()

    LaunchedEffect(key1 = Unit) {
        orderDetailsViewModel.email.value = email.toString()
        orderDetailsViewModel.fetchOrderList(context)
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        val height = (maxHeight / 100f) * 20
        //Background Image
        BackgroundImage1()

        //top bar
        TopAppBar(
            title = { Text(text = "Order Details", color = Color.White) },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
        )

        //Main Column
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(items = orderDetailsViewModel.finalList) { item ->

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
                        onClick = {},
                    ) {
                        Row {
                            var sum = 0
                            Column(
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .fillMaxWidth(.7F)
                                    .fillMaxHeight()
                            ) {
                                var index = 0
                                Text(
                                    text = "# " + item[index].orderId,
                                    modifier = Modifier.padding(top = 20.dp, bottom = 15.dp),
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = titleFontFamily,
                                    fontSize = 19.sp,
                                )
                                Text(
                                    text = item.size.toString() + " items",
                                    modifier = Modifier.padding(bottom = 5.dp),
                                    fontFamily = titleFontFamily,
                                    fontSize = 19.sp,
                                )
                                Column(modifier = Modifier.height(35.dp)) {
                                    val count = 2

                                    do {
                                        if (index < count) {
                                            Text(
                                                text = item[index].title + " x" + item[index].count,
                                                maxLines = 1,
                                                fontSize = 14.sp,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                        sum += item[index].price * item[index].count
                                        index++
                                    } while (index < item.size)
                                }
                                Box(
                                    modifier = Modifier
                                        .clickable(
                                            interactionSource = interactionSource,
                                            onClick = {},
                                            indication = null
                                        )
                                        .padding(top = 10.dp),

                                    ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.HelpOutline,
                                            modifier = Modifier.size(19.dp),
                                            tint = DarkRed,
                                            contentDescription = "Help",
                                        )

                                        Text(
                                            color = DarkRed,
                                            text = "Support",
                                            fontFamily = titleFontFamily,
                                            fontSize = 15.sp,
                                        )
                                    }
                                }

                            }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "Rs : $sum",
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

@Composable
fun BackgroundImage1() {
    Image(
        painter = painterResource(id = R.drawable.background_home_view),
        contentDescription = "Background Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}