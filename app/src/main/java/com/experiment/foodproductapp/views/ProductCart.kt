package com.experiment.foodproductapp.views

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.ProductCartViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun ProductCart(
    navHostControllerLambda: () -> NavHostController,
    productCartViewModel: ProductCartViewModel = viewModel(),
) {

    val context = LocalContext.current
    ChangeBarColors(statusColor = Color.White, navigationBarColor = DarkYellow)

    LaunchedEffect(key1 = Unit){
        productCartViewModel.fetchCartList(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(6F)
                .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            item {

                TopAppBar(
                    title = {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = "Cart ",
                                fontFamily = titleFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = DarkYellow,
                            )
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = "",
                                tint = DarkYellow,
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navHostControllerLambda().navigateUp()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "",
                                tint = DarkYellow,
                            )
                        }
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                )


            }

            items(
                items = productCartViewModel.cartList,
                key = { it.hashCode() }
            ){ item ->

                val dismissState = rememberDismissState(
                    initialValue = DismissValue.Default,
                    confirmStateChange = {
                        if(it == DismissValue.DismissedToStart){
                            productCartViewModel.onDismiss(context,item)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.2F) },
                    dismissContent = { CardView(item) },
                    background = {

                        val color = animateColorAsState(
                            targetValue = when(dismissState.targetValue) {
                                DismissValue.DismissedToStart -> Color.Red
                                DismissValue.Default -> LightDarkGray
                                else -> Color.Transparent
                            },
                            animationSpec = tween(100)
                        )

                        Box( //Red background
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(15.dp))
                                .background(color.value)
                                .padding(end = 25.dp),
                            contentAlignment = Alignment.CenterEnd,
                        ){
                            Icon( //Dustbin Icon
                                imageVector = Icons.Default.Delete,
                                contentDescription = "",
                                tint = Color.White,
                            )
                        }
                    },
                )
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Column( //Checkout Button
            modifier = Modifier
                .shadow(70.dp)
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .weight(1F)
                .fillMaxSize()
                .background(DarkYellow)
            ,
        ){

            //Price row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.40F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Price ",
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 25.dp)
                        .weight(1F),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )

                Text(
                    text = "Rs : ${productCartViewModel.computePrice()}",
                    color = Color.White,
                    modifier = Modifier
                        .padding(end = 25.dp)
                        .weight(1F),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }

            //Checkout button
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Button(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 6.dp, bottom = 9.dp)
                        .fillMaxSize(),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                    ),
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = "CHECKOUT",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardView(item: Product){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        elevation = 20.dp,
        shape = RoundedCornerShape(15.dp),
        onClick = {  },
    ) {
        Row {
            //Product Image
            Box {
                Image(
                    painter = rememberImagePainter(item.url),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.CenterStart,
                    modifier = Modifier.padding(8.dp),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
            ){
                Text( // Title
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5,
                    overflow = TextOverflow.Ellipsis,
                    text = item.title,
                    fontFamily = titleFontFamily,
                )
                Text( // Price
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    text = "MRP:Rs ${item.price}",
                    fontFamily = descriptionFontFamily,
                )

                //Count button
                Row(modifier = Modifier.fillMaxWidth()) {
                    //Add
                    Box(
                        modifier = Modifier
                            .weight(2F)
                            .background(Color.Transparent),
                        contentAlignment = Alignment.Center,
                    ){
                        Surface(
                            elevation = 3.dp,
                            color = Color.Transparent
                        ){
                            IconButton(
                                onClick = {},
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                Orange,
                                                DarkYellow
                                            )
                                        )
                                    )
                                    .size(width = 25.dp, height = 25.dp)
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
                    Text(text = "def 0")

                    //Minus
                    Box(
                        modifier = Modifier
                            .weight(2F)
                            .background(Color.Transparent),
                        contentAlignment = Alignment.Center,
                    ){
                        Surface(
                            elevation = 3.dp,
                            color = Color.Transparent
                        ){
                            IconButton(
                                onClick = {},
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                Orange,
                                                DarkYellow
                                            )
                                        )
                                    )
                                    .size(width = 25.dp, height = 25.dp)
                            ){
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "",
                                    tint = Color.White,
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PrevPC() {
    val navHostController = rememberNavController()

    FoodProductAppTheme {
        ProductCart(navHostControllerLambda = { navHostController })
    }
}
