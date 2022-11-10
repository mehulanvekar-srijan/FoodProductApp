package com.experiment.foodproductapp.views

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.experiment.foodproductapp.viewmodels.OrderDetailsViewModel
import com.experiment.foodproductapp.viewmodels.UserDetailsViewModel
import kotlinx.coroutines.launch

@Preview
@Composable
fun preview2() {
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {

        navHostController
    }
    OrderDetails("sahil@test.com", navHostControllerLambda = navHostControllerLambda)
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderDetails(
    email : String?,
    navHostControllerLambda: () -> NavHostController,
    orderDetailsViewModel: OrderDetailsViewModel= viewModel(),
){
    ChangeBarColors(navigationBarColor = Color.White)
    val listState = rememberLazyListState()


    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        val height = (maxHeight / 100f) * 40
        //Background Image
        BackgroundImage1()

        //Main Column
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top=20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            state = listState,
        ) {
            items(items = orderDetailsViewModel.orderlist ) { item ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height)
                            .padding(start = 15.dp, end = 15.dp),
                        elevation = 5.dp,
                        shape = RoundedCornerShape(5),
                        onClick = {
                            },
                    ) {
                        Row {

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
                                    color = Color.DarkGray
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

//    val count = remember{ mutableStateOf(0) }
//    SideEffect { Log.d("testRecomp", "BackgroundImage : ${count.value++}") }
}