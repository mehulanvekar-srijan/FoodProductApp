package com.experiment.foodproductapp.views

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.ui.theme.Orange
import com.experiment.foodproductapp.ui.theme.descriptionFontFamily
import com.experiment.foodproductapp.ui.theme.titleFontFamily
import com.experiment.foodproductapp.viewmodels.ProductCartViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductCart(
    productCartViewModel: ProductCartViewModel = viewModel(),
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        productCartViewModel.fetchCartList(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Orange),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        LazyColumn(
            modifier = Modifier
                .background(Orange)
                .weight(5F)
        ) {
            items(items = productCartViewModel.cartList.value){ item ->

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if(it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd){
                            Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show()
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart,DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.2F) },
                    dismissContent = { CardView(item) },
                    background = {
                        val color = animateColorAsState(
                            targetValue = when(dismissState.targetValue) {
                                DismissValue.DismissedToStart -> Color.Red
                                else -> Color.Transparent
                            })
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color.value)
                                .padding(end = 25.dp),
                            contentAlignment = Alignment.CenterEnd,
                        ){
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "",
                                tint = Color.White,
                            )
                        }
                    },
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
                .weight(1F),
            contentAlignment = Alignment.Center
        ){
            Button(onClick = { /*TODO*/ }) {
                Text(text = "CHECKOUT")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardView(item: Product){
    Card(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth()
            .height(110.dp),
        elevation = 10.dp,
        onClick = {  },
    ) {
        Row {
            val liked = rememberSaveable { mutableStateOf(false) }

            //Product Image
            Box {
                Image(
                    painter = rememberImagePainter(item.url),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.CenterStart,
                    modifier = Modifier.padding(8.dp),
                )
                IconButton(onClick = { liked.value = !liked.value }) {
                    if (liked.value){
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "",
                            tint = Color.Red
                        )
                    }
                    else{
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "",
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
            ){
                Text( // Title
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5,
                    overflow = TextOverflow.Ellipsis,
                    text = item.title,
                    fontFamily = titleFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Text( // Price
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    text = "MRP:Rs ${item.price}",
                    fontFamily = descriptionFontFamily,
                )
            }
        }
    }
}