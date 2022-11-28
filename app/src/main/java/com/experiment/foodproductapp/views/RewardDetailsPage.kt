package com.experiment.foodproductapp.views

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.CardFace
import com.experiment.foodproductapp.ui.theme.ChangeBarColors
import com.experiment.foodproductapp.ui.theme.DarkYellow
import com.experiment.foodproductapp.ui.theme.Orange
import com.experiment.foodproductapp.ui.theme.titleFontFamily
import com.experiment.foodproductapp.viewmodels.RewardsDetailsPageViewModel

@Preview
@Composable
fun preview4() {
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {
        navHostController
    }
    RewardDetails(100, navHostControllerLambda)
}

@Composable
fun RewardDetails(
    points: Int?,
    navHostControllerLambda: () -> NavHostController,
    rewardsDetailsPageViewModel: RewardsDetailsPageViewModel = viewModel()
) {
    ChangeBarColors(navigationBarColor = DarkYellow)

    LaunchedEffect(key1 = Unit) {
        if (points != null) {
            rewardsDetailsPageViewModel.rewardPoints.value = points
        }
        rewardsDetailsPageViewModel.setBorder()
    }

    Background()

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.reward_category_string), color = Color.White) },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "ic_arrow_back_bt",
                    tint = Color.White
                )
            }
        },
    )


    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = maxHeight / 100f
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.tap_on_card_to_know_more_string),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(10.dp))
            FlipCard(
                cardFace = rewardsDetailsPageViewModel.bronzeCardFace.value,
                onClick = {
                    rewardsDetailsPageViewModel.bronzeCardFace.value =
                        rewardsDetailsPageViewModel.bronzeCardFace.value.next
                    rewardsDetailsPageViewModel.onBronzeClick(rewardsDetailsPageViewModel.bronzeCardFace.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height * 26),
                front = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(rewardsDetailsPageViewModel.bronzePadding.value)
                            .border(
                                width = rewardsDetailsPageViewModel.bronzeBorder.value,
                                color = Color.Black,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bronze_medal),
                            contentDescription = "Bronze Medal"
                        )
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.level_one_string),
                                textAlign = TextAlign.Center,
                                fontFamily = titleFontFamily,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.bronze_string),
                                textAlign = TextAlign.Center,
                                fontFamily = titleFontFamily,
                                fontSize = 24.sp
                            )
                        }
                    }
                },
                back = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_get_one_rupee_for_20_points_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_can_redeem_points_in_cart_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_can_redeem_max_10_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_can_redeem_points_on_order_above_100_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                },
            )
            Spacer(modifier = Modifier.size(20.dp))
            FlipCard(
                cardFace = rewardsDetailsPageViewModel.silverCardFace.value,
                onClick = {
                    rewardsDetailsPageViewModel.silverCardFace.value =
                        rewardsDetailsPageViewModel.silverCardFace.value.next
                    rewardsDetailsPageViewModel.onSilverClick(rewardsDetailsPageViewModel.silverCardFace.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height * 26),
                front = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(rewardsDetailsPageViewModel.silverPadding.value)
                            .border(
                                width = rewardsDetailsPageViewModel.silverBorder.value,
                                color = Color.Black,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.silver_medal),
                            contentDescription = "Silver Medal"
                        )
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.level_two_string),
                                textAlign = TextAlign.Center,
                                fontFamily = titleFontFamily,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.silver_string),
                                textAlign = TextAlign.Center,
                                fontFamily = titleFontFamily,
                                fontSize = 24.sp
                            )
                        }
                    }
                },
                back = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_get_one_rupee_for_10_points_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_can_redeem_points_in_cart_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_can_redeem_max_10_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_can_redeem_points_on_order_above_100_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                },
            )
            Spacer(modifier = Modifier.size(20.dp))
            FlipCard(
                cardFace = rewardsDetailsPageViewModel.goldCardFace.value,
                onClick = {
                    rewardsDetailsPageViewModel.goldCardFace.value =
                        rewardsDetailsPageViewModel.goldCardFace.value.next
                    rewardsDetailsPageViewModel.onGoldClick(rewardsDetailsPageViewModel.goldCardFace.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height * 26),
                front = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(rewardsDetailsPageViewModel.goldPadding.value)
                            .border(
                                width = rewardsDetailsPageViewModel.goldBorder.value,
                                color = Color.Black,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gold_medal),
                            contentDescription = "Gold Medal"
                        )
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.level_three_string),
                                textAlign = TextAlign.Center,
                                fontFamily = titleFontFamily,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.gold_string),
                                textAlign = TextAlign.Center,
                                fontFamily = titleFontFamily,
                                fontSize = 24.sp
                            )
                        }
                    }
                },
                back = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_get_one_rupee_for_5_points_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_can_redeem_points_in_cart_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_can_redeem_max_10_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(id = R.string.you_can_redeem_points_on_order_above_100_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                },
            )
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}


@Composable
fun Background() {
    Image(
        painter = painterResource(id = R.drawable.background_yellow_wave),
        contentDescription = "ic_background_image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FlipCard(
    cardFace: CardFace,
    onClick: (CardFace) -> Unit,
    modifier: Modifier = Modifier,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing,
        )
    )
    Card(
        onClick = { onClick(cardFace) },
        shape = RoundedCornerShape(20.dp),
        elevation = 20.dp,
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            },
    ) {
        if (rotation.value <= 90f) {
            Box(
                Modifier.fillMaxSize()
            ) {
                front()
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f
                    },
            ) {
                back()
            }
        }
    }
}