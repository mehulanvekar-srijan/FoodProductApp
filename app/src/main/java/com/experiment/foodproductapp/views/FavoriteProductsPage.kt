package com.experiment.foodproductapp.views

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.database.entity.HomeItems
import com.experiment.foodproductapp.database.entity.LikedItems
import com.experiment.foodproductapp.database.entity.Product
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.FavouriteProductsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun FavouriteProductsPage(
    email: String?,
    navHostControllerLambda: () -> NavHostController,
    favouriteProductsViewModel: FavouriteProductsViewModel = koinViewModel(),
) {

    ChangeBarColors(statusColor = Color.White, navigationBarColor = DarkYellow)

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(key1 = Unit, block = {
        favouriteProductsViewModel.initEmail()
        favouriteProductsViewModel.initLikedItemsList()
    })

//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        Text(text = email.toString())
//        favouriteProductsViewModel.likedItemsList.forEach {
//            Text(text = it.title)
//        }
//    }



//    LaunchedEffect(key1 = Unit) {
//
//        //clearing list during recomposition
//        //productCartViewModel.cartList.clear()
//
//        if (email != null) {
//            //productCartViewModel.email.value = email
//            //productCartViewModel.initAvailablePointsAndRedeemedAmount(email)
//        }
//
//        //productCartViewModel.fetchCartList()
//    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favourite",
                        fontFamily = titleFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = DarkYellow,
                    )
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
            )
        }
    ) {

        //Main Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {

            //Item List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(3F)
                    .padding(start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {

                items(
                    items = favouriteProductsViewModel.likedItemsList,
                    key = { key -> key.id } // Caused n Solved multiple issues. Learn it.
                ) { item ->

                    val dismissState = rememberDismissState(
                        initialValue = DismissValue.Default,
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {

                                //productCartViewModel.setNewlyDeletedItem(item)
                                favouriteProductsViewModel.onDismiss(item)

                                Log.d("testSwap", "FavouriteProductsPage: swiped")

                                coroutineScope.launch {

                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = if(item.title.length >= 15) "Item '${item.title.substring(0..15)}...' was removed"
                                        else "Item '${item.title}' was removed",
                                        actionLabel = "UNDO"
                                    )

                                    when(result){
                                        SnackbarResult.ActionPerformed -> {
                                            favouriteProductsViewModel.onRestore(item = item)
                                        }
                                        SnackbarResult.Dismissed -> {}
                                    }

                                }

                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        dismissThresholds = { FractionalThreshold(0.2F) },
                        dismissContent = {
                            FavouriteCardView(item)
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
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(600)
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }

        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouriteCardView(item: HomeItems) {
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
                FavouriteLoadImage(item)
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
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FavouriteLoadImage(item: HomeItems) {
    Image(
        painter = rememberImagePainter(item.url),
        contentDescription = "ic_cart_item",
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        modifier = Modifier.padding(8.dp),
    )
}
