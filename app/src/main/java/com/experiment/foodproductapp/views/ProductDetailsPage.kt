package com.experiment.foodproductapp.views



import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.HomeScreenViewModel

//@Preview
//@Composable
//fun Preview() {
//    val navHostController = rememberNavController()
//    ProductDetailsPage({navHostController },viewModel())
//}


@Composable
fun ProductDetailsPage(navHostControllerLambda: () -> NavHostController, homeScreenViewModel: HomeScreenViewModel) {

    val productDetails = homeScreenViewModel.productForDetailPage
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    ChangeBarColors(statusColor = Color.White, navigationBarColor = DarkYellow)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkYellow),
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.85f)
                    .clip(RoundedCornerShape(bottomStartPercent = 45))
                    .background(Color.White)
                    //.verticalScroll(scrollState),

                ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.88f)
                        .verticalScroll(scrollState),
                ) {


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(Color.White)
                                .graphicsLayer {
                                    alpha =
                                        1.05f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.0f)
                                    translationY = 0.3f * scrollState.value
                                },

                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = rememberImagePainter(productDetails!!.url),
                                //painter = painterResource(id = R.drawable.beer),
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth,

                                modifier = Modifier
                                    .padding(top = 60.dp)
                                    .height(350.dp)
                                //.padding(10.dp, top = 60.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = productDetails!!.title,
                            color = Color.DarkGray,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 26.sp,
                                letterSpacing = 1.sp,
                                fontFamily = titleFontFamily
                            ),
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(start = 40.dp, top = 20.dp)

                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        //item {
                        Text(
                            text = productDetails!!.description,
                            color = LightDarkGray,
                            style = TextStyle(
                                fontSize = 17.sp,
                                //letterSpacing = 1.sp,
                                fontFamily = descriptionFontFamily
                            ),
                            //maxLines = 5,
                            overflow = TextOverflow.Clip,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 40.dp, end = 20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(top = 10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_percentage_p),
                            contentDescription = "",
                            modifier = Modifier.fillMaxHeight(.7f)
                        )
                        Text(
                            text = "5" + "% v/v",
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 25.sp,
                                letterSpacing = 1.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_rupee_2),
                            contentDescription = "",
                            modifier = Modifier.fillMaxHeight(.7f)
                        )
                        Text(
                            text = "Rs. " + productDetails!!.price,
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 25.sp,
                                letterSpacing = 1.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                }
            }
        }
    }

    AppBar(
        navHostControllerLambda = navHostControllerLambda,
        onProductCartClick = {
            homeScreenViewModel.addProductToCart(productDetails!!,context)
            homeScreenViewModel.navigateToProductCart(navHostControllerLambda())
        }
    )
}

@Composable
fun AppBar(
    navHostControllerLambda: () -> NavHostController,
    onProductCartClick: ()-> Unit = {},
    onProductAddClick:  ()-> Unit = {}
) {
    val liked = rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = "Beer App", color = DarkYellow) },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = DarkYellow)
            }

        },
        actions = {
            IconButton(onClick =  onProductAddClick ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = DarkYellow
                )

            }

            IconButton(onClick = onProductCartClick) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "", tint = DarkYellow)

            }
        }
    )
}