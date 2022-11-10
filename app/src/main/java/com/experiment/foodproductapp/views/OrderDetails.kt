package com.experiment.foodproductapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

    LaunchedEffect(key1 = Unit) {
        orderDetailsViewModel.email.value = email.toString()
        orderDetailsViewModel.fetchOrderList(context)
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        val height = (maxHeight / 100f) * 40
        //Background Image
        BackgroundImage1()

        //top bar
        TopAppBar(
            title = { Text(text = "Order Details", color = Color.White) },
            backgroundColor = Color.Transparent,
            elevation = 2.dp,
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
                        shape = RoundedCornerShape(5),
                        onClick = {},
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            var index = 0
                            do {
                                Text(text = item[index].title, fontSize = 18.sp)
                                index++
                            } while (index < item.size)
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