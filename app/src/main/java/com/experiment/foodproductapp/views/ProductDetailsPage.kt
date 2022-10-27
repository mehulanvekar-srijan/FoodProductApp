package com.experiment.foodproductapp.views



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*

import androidx.compose.runtime.Composable


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip


import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.experiment.foodproductapp.R

import com.experiment.foodproductapp.database.Product

import com.experiment.foodproductapp.ui.theme.Orange


var product: Product = Product(
id = 0,
url = "https://www.bigbasket.com/media/uploads/p/xxl/40213061_2-coolberg-non-alcoholic-beer-malt.jpg",
title = "Coolberg Non Alcoholic Beer - Malt",
description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
price = 79,
)


@Preview
@Composable
fun Preview() {
//    val navHostController = rememberNavController()
//    val navHostControllerLambda: () -> NavHostController = {
//
//        navHostController
//    }
  ProductDetailsPage()
}

@Composable
fun ProductDetailsPage() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Orange),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                //painter = rememberImagePainter(product.url),
                painter = painterResource(id = R.drawable.beer),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Text(
                    text = product.title,
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        letterSpacing = 1.sp
                    ),
                    textAlign = TextAlign.Center
                )
                // }
                //Spacer(modifier = Modifier.padding(top = 20.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.88f)
                        .padding(20.dp),
                    // .background(Orange),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    item {
                        Text(

                            text = product.description,
                            color = Color.Black,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 23.sp,
                                letterSpacing = 1.sp
                            ),
                            textAlign = TextAlign.Justify
                        )
                    }
                }

                    Text(
                        text = "Rs. " + product.price,
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            letterSpacing = 1.sp
                        ),
                        textAlign = TextAlign.Center,

                    )
                }
        }
    }
}




