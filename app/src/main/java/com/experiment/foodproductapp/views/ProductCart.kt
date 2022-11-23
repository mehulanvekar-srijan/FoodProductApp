package com.experiment.foodproductapp.views

import android.content.Context
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
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.database.entity.Product
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.ProductCartViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductCart(
    email: String?,
    navHostControllerLambda: () -> NavHostController,
    productCartViewModel: ProductCartViewModel = koinViewModel(),
) {

    val context = LocalContext.current
    ChangeBarColors(statusColor = Color.White, navigationBarColor = DarkYellow)

    LaunchedEffect(key1 = Unit) {
        if (email != null) {
            productCartViewModel.email.value = email
            productCartViewModel.initAvailablePointsAndRedeemedAmount(email)
        }
        productCartViewModel.fetchCartList()
    }

    if(productCartViewModel.openDialog.value){
        ShowDialogBox(context,productCartViewModel)
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
                .weight(7F)
                .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            item {
                TopAppBar(
                    title = {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = stringResource(id = R.string.cart_string) + " ",
                                fontFamily = titleFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = DarkYellow,
                            )
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = "ic_cart_logo",
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
                                contentDescription = "ic_arrow_back_bt",
                                tint = DarkYellow,
                            )
                        }
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    actions = {
                        IconButton(onClick = {
                            productCartViewModel.navigateToRewards(
                                navHostController = navHostControllerLambda(),
                                email = email,
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = "ic_rewards_page_bt",
                                tint = DarkYellow
                            )
                        }
                    }
                )
            }

            items(
                items = productCartViewModel.cartList,
                key = { it.id } // Caused n Solved multiple issues. Learn it.
            ) { item ->

                val dismissState = rememberDismissState(
                    initialValue = DismissValue.Default,
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {

                            //productCartViewModel.setDialogState(true)
                            productCartViewModel.setNewlyDeletedItem(item)

                        }
                        false
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.2F) },
                    dismissContent = {
                        CardView(item, productCartViewModel)
                    },
                    background = {

                        val color = animateColorAsState(
                            targetValue = when (dismissState.targetValue) {
                                DismissValue.DismissedToStart -> Color.Red
                                DismissValue.Default -> LightDarkGray
                                else -> Color.Transparent
                            },
                            animationSpec = tween(100)
                        )

                        Box(
                            //Red background
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(15.dp))
                                .background(color.value)
                                .padding(end = 25.dp),
                            contentAlignment = Alignment.CenterEnd,
                        ) {
                            Icon(
                                //Dustbin Icon
                                imageVector = Icons.Default.Delete,
                                contentDescription = "ic_delete",
                                tint = Color.White,
                            )
                        }
                    },
                )
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Box(
            modifier = Modifier
                .shadow(70.dp)
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .weight(2F)
                .fillMaxSize()
                .background(DarkYellow)
        ) {
            CheckoutArea(
                productCartViewModel = productCartViewModel,
                navigate = {
                    if (productCartViewModel.sum.value != 0) {
                        productCartViewModel.navigateToCheckout(navHostControllerLambda())
                    }
//                    navHostControllerLambda().navigate(Screen.PaymentScreen.route)
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardView(
    item: Product,
    productCartViewModel: ProductCartViewModel
) {
    val quantity = remember { mutableStateOf(0) }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        productCartViewModel.getProductCount(item.id, quantity)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp),
        onClick = { },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            //Product Image
            Box(
                modifier = Modifier.weight(2F)
            ) {
                LoadImage(item)
            }

            //Title / Description
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .weight(4F),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    // Title
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5,
                    overflow = TextOverflow.Ellipsis,
                    text = item.title,
                    //text = "Coolberg Non Alcoholic Beer - Mint",
                    fontFamily = titleFontFamily,
                    fontSize = 20.sp,
                )
                Text(
                    // Price
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.mrp_rs_string) + " " + item.price,
                    fontFamily = descriptionFontFamily,
                )
            }

            //Count button
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2F)
                    .padding(end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Add
                Box(
                    modifier = Modifier
                        .weight(2F)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center,
                ) {
                    Surface(
                        elevation = 3.dp,
                        color = Color.Transparent
                    ) {
                        IconButton(
                            onClick = {
                                productCartViewModel.incrementProductCount(
                                    item.id,
                                    quantity
                                )
                            },
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
                //Text(text = "1", textAlign = TextAlign.Center)
                Text(text = quantity.value.toString(), textAlign = TextAlign.Center)

                //Minus
                Box(
                    modifier = Modifier
                        .weight(2F)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center,
                ) {
                    Surface(
                        elevation = 3.dp,
                        color = Color.Transparent
                    ) {
                        //If Quantity is > 1 show Yellow button else show Grey button
                        if (quantity.value > 1) {
                            IconButton(
                                onClick = {
                                    productCartViewModel.decrementProductCount(
                                        item.id,
                                        quantity
                                    )
                                },
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
                                    .size(width = 25.dp, height = 25.dp),
                                enabled = quantity.value > 1,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Remove,
                                    contentDescription = "ic_subtract_count_bt",
                                    tint = Color.White,
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    productCartViewModel.decrementProductCount(
                                        item.id,
                                        quantity
                                    )
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(LightDarkGray)
                                    .size(width = 25.dp, height = 25.dp),
                                enabled = quantity.value > 1,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Remove,
                                    contentDescription = "ic_subtract_count_bt",
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun LoadImage(item: Product) {
    Image(
        painter = rememberImagePainter(item.url),
        contentDescription = "ic_cart_item",
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        modifier = Modifier.padding(8.dp),
    )
}

@Composable
fun CheckoutArea(
    productCartViewModel: ProductCartViewModel,
    navigate: () -> Unit = {},
) {
    Column {

        //Sum row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.price_string) + " ",
                color = Color.White,
                modifier = Modifier
                    .padding(start = 25.dp),
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
            )

            Text(
                text = stringResource(id = R.string.rs_string) + " " + productCartViewModel.sum.value,
                color = Color.White,
                modifier = Modifier
                    .padding(end = 48.dp)
                    .weight(1F),
                textAlign = TextAlign.End,
                fontSize = 18.sp,
            )
        }

        //Redeem Amount
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.redeem_amount_string) + " ",
                color = if (productCartViewModel.checkedState.value) {
                    Color.White
                } else {
                    LightDarkGray
                },
                modifier = Modifier
                    .padding(start = 25.dp),
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                Text(
                    text = if (productCartViewModel.checkedState.value) {
                        stringResource(id = R.string.rs_string) + " " + (productCartViewModel.sum.value - productCartViewModel.finalSum.value)
                    } else {
                        stringResource(id = R.string.rs_0_string)
                    },
                    color = if (productCartViewModel.checkedState.value) {
                        Color.White
                    } else {
                        LightDarkGray
                    },
                    modifier = Modifier
                        .weight(1F),
                    textAlign = TextAlign.End,
                    fontSize = 20.sp,
                )
                Checkbox(
                    enabled = productCartViewModel.sum.value >= 100 && productCartViewModel.availablePoints.value > 10,
                    modifier = Modifier.padding(0.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White,
                        uncheckedColor = LightDarkGray,
                        checkmarkColor = Color.Black
                    ),
                    // below line we are setting
                    // the state of checkbox.
                    checked = productCartViewModel.checkedState.value,
                    // below line is use to add on check
                    // change to our checkbox.
                    onCheckedChange = {
                        productCartViewModel.checkedState.value = it
                        if (productCartViewModel.sum.value >= 100) {
                            productCartViewModel.updateFinalSum()
                        }
                    },
                )
            }
        }

        //Final Amount
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(bottom = 2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.final_amount_string) + " ",
                color = Color.White,
                modifier = Modifier
                    .padding(start = 25.dp)
                    .weight(1F),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )

            Text(
                text = stringResource(id = R.string.rs_string) + " " + productCartViewModel.finalSum.value,
                color = Color.White,
                modifier = Modifier
                    .padding(end = 45.dp)
                    .weight(1F),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        //Checkout button
        Box(
            modifier = Modifier
                .weight(1F),
        ) {
            Button(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, top = 2.dp, bottom = 1.dp)
                    .fillMaxSize(),
                onClick = navigate,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                ),
                shape = RoundedCornerShape(50),
            ) {
                Text(
                    text = stringResource(id = R.string.checkout_string),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.padding(2.dp))
    }
}


@Composable
fun ShowDialogBox(
    context: Context,
    productCartViewModel: ProductCartViewModel
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Warning..!!") },
        text = { Text(text = "The item will be removed from the cart") },
        confirmButton = {
            Button(
                onClick = {
                    productCartViewModel.onDismiss(productCartViewModel.newlyDeletedItem.value)
                    productCartViewModel.setDialogState(false)
                }) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    productCartViewModel.setDialogState(false)
                }) {
                Text("No")
            }
        }
    )

}

@Preview(showBackground = true, backgroundColor = 1)
@Composable
fun PrevPC() {

    val navHostController = rememberNavController()

    FoodProductAppTheme {
        ProductCart(navHostControllerLambda = { navHostController }, email = "meh@ul.com")
    }


//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(5.dp)
//            .background(Color.White)
//    ){
//        for (i in 1..4){
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(110.dp),
//                elevation = 20.dp,
//                shape = RoundedCornerShape(15.dp),
//                onClick = {  },
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    //Product Image
//                    Box(
//                        modifier = Modifier.weight(2F)
//                    ){
//                        Image(
//                            //painter = rememberImagePainter(item.url),
//                            painter = painterResource(id = R.drawable.beer0),
//                            contentDescription = "",
//                            contentScale = ContentScale.Fit,
//                            alignment = Alignment.CenterStart,
//                            modifier = Modifier.padding(8.dp),
//                        )
//                    }
//
//                    //Title / Description
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(5.dp)
//                            .weight(4F),
//                        verticalArrangement = Arrangement.SpaceEvenly,
//                    ){
//                        Text( // Title
//                            textAlign = TextAlign.Start,
//                            style = MaterialTheme.typography.h5,
//                            overflow = TextOverflow.Ellipsis,
//                            //text = item.title,
//                            text = "Coolberg Non Alcoholic Beer - Mint",
//                            fontFamily = titleFontFamily,
//                            fontSize = 20.sp,
//                        )
//                        Text( // Price
//                            textAlign = TextAlign.Center,
//                            color = Color.Black,
//                            overflow = TextOverflow.Ellipsis,
//                            text = "MRP:Rs 79",
//                            fontFamily = descriptionFontFamily,
//                        )
//                    }
//
//                    //Count button
//                    Row(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .weight(2F)
//                            .padding(end = 5.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        //Add
//                        Box(
//                            modifier = Modifier
//                                .weight(2F)
//                                .background(Color.Transparent),
//                            contentAlignment = Alignment.Center,
//                        ){
//                            Surface(
//                                elevation = 3.dp,
//                                color = Color.Transparent
//                            ){
//                                IconButton(
//                                    onClick = {
//                                        //productCartViewModel.incrementProductCount(context,item.id,quantity)
//                                    },
//                                    modifier = Modifier
//                                        .clip(RoundedCornerShape(15))
//                                        .background(
//                                            Brush.verticalGradient(
//                                                listOf(
//                                                    Orange,
//                                                    DarkYellow
//                                                )
//                                            )
//                                        )
//                                        .size(width = 25.dp, height = 25.dp)
//                                ){
//                                    Icon(
//                                        imageVector = Icons.Default.Add,
//                                        contentDescription = "",
//                                        tint = Color.White,
//                                    )
//                                }
//                            }
//                        }
//
//                        //Count Value
//                        Text(text = "1", textAlign = TextAlign.Center)
//                        //Text(text = quantity.value.toString(),textAlign = TextAlign.Center)
//
//                        //Minus
//                        Box(
//                            modifier = Modifier
//                                .weight(2F)
//                                .background(Color.Transparent),
//                            contentAlignment = Alignment.Center,
//                        ){
//                            Surface(
//                                elevation = 3.dp,
//                                color = Color.Transparent
//                            ){
//                                IconButton(
//                                    onClick = {
//                                        //productCartViewModel.decrementProductCount(context,item.id,quantity)
//                                    },
//                                    modifier = Modifier
//                                        .clip(RoundedCornerShape(15))
//                                        .background(
//                                            Brush.verticalGradient(
//                                                listOf(
//                                                    Orange,
//                                                    DarkYellow
//                                                )
//                                            )
//                                        )
//                                        .size(width = 25.dp, height = 25.dp)
//                                        .clickable {
//
//                                        },
//
//                                    enabled = true,
//                                ){
//                                    Icon(
//                                        imageVector = Icons.Filled.Remove,
//                                        contentDescription = "",
//                                        tint = Color.White,
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            Spacer(modifier = Modifier.height(10.dp))
//        }
//    }
}
