package com.experiment.foodproductapp.views

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.RewardsPageViewModel
import kotlinx.coroutines.launch

@Composable
@Preview
fun show1() {
    val string = "Sahil"
    val navHostController = rememberNavController()
    val navHostControllerLambda: () -> NavHostController = {
        navHostController
    }
    Reward(email = string, navHostControllerLambda)
}

@Composable
fun Reward(
    email: String?,
    navHostControllerLambda: () -> NavHostController,
    rewardsPageViewModel: RewardsPageViewModel = viewModel()
) {
    ChangeBarColors(navigationBarColor = Color.White)

    val text = stringResource(id = R.string.refer_copy_string)

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        rewardsPageViewModel.getRewardPoints(context, email.toString())
    }

    Box(modifier = Modifier.fillMaxSize()) {
//        Background Image
        BackgroundImage2()

//        bottom box
        BottomBox()

//      Top Bar
        TopAppBar(
            title = { Text(text = "", color = Color.White) },
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
                    .padding(start = 25.dp, end = 25.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height * 14)
                        .padding(end = 10.dp, top = 16.dp, bottom = 10.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.welcome_to_string),
                        fontFamily = titleFontFamily,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Text(
                        text = stringResource(id = R.string.rewards_string),
                        fontFamily = titleFontFamily,
                        fontSize = 30.sp,
                        color = Color.White
                    )

                }
                Card(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .height(height * 15),
                    elevation = 30.dp,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.StarRate,
                            contentDescription = "ic_points",
                            tint = Color.Black,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.points_string),
                            modifier = Modifier.padding(5.dp),
                            fontFamily = titleFontFamily,
                            fontSize = 24.sp
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = rewardsPageViewModel.rewardPointsState.value.toString(),
                                fontFamily = titleFontFamily,
                                fontSize = 40.sp,
                                textAlign = TextAlign.End
                            )
                            Text(
                                text = stringResource(id = R.string.equals_rs_string) + (rewardsPageViewModel.rewardPointsState.value / 10).toString(),
                                fontFamily = descriptionFontFamily,
                                textAlign = TextAlign.End
                            )
                        }
                    }

                }
                Spacer(Modifier.height(height * 2))

                Card(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .height(height * 18),
                    elevation = 30.dp,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.25f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Redeem,
                                contentDescription = "ic_redeem",
                                Modifier.size(40.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.redeem_string),
                                modifier = Modifier.padding(5.dp),
                                fontFamily = titleFontFamily,
                                fontSize = 22.sp
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 5.dp, end = 20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End,
                        ) {
                            Text(
                                text = stringResource(id = R.string.redeem_message_string),
                                modifier = Modifier.padding(5.dp),
                                fontFamily = titleFontFamily,
                                fontSize = 20.sp
                            )

                        }
//                        ) {
//                            TextField(
//                                value = rewardsPageViewModel.redeemedpoints.value,
//                                colors = TextFieldDefaults.textFieldColors(
//                                    textColor = Color.Black,
//                                    backgroundColor = LightGray1,
//                                    placeholderColor = Color.White,
//                                    cursorColor = Color.Black,
//                                    errorIndicatorColor = Color.Transparent,
//                                    focusedLabelColor = Color.Black,
//                                    errorCursorColor = Color.Black,
//                                    errorLabelColor = Color.Red,
//                                    focusedIndicatorColor = Color.Transparent,
//                                    unfocusedIndicatorColor = Color.Transparent,
//                                    unfocusedLabelColor = Orange,
//                                ),
//                                shape = RoundedCornerShape(20.dp),
//                                keyboardOptions = KeyboardOptions(
//                                    keyboardType = KeyboardType.Phone
//                                ),
//                                onValueChange = {
//                                    rewardsPageViewModel.updateUserPoints(it)
//                                })
//
//                            Spacer(modifier = Modifier.height(5.dp))
//
//                            Button(
//                                onClick = { rewardsPageViewModel.validateRewards(context,email.toString()).show()
//                                },
//                                modifier = Modifier
//                                    .fillMaxWidth(.4f)
//                                    .height(22.dp),
//                                contentPadding = PaddingValues(0.dp),
//                                shape = RoundedCornerShape(50),
//                                colors = ButtonDefaults.buttonColors(
//                                    backgroundColor = DarkYellow,
//                                    contentColor = Color.White
//                                ),
//                                elevation = ButtonDefaults.elevation(
//                                    defaultElevation = 5.dp
//                                )
//                            ) {
//                                Text(
//                                    modifier = Modifier.fillMaxSize(),
//                                    text = "Redeem",
//                                    color = Color.White,
//                                    fontSize = 14.sp,
//                                    textAlign = TextAlign.Center
//                                )
//                            }
//                        }
                    }

                }

                Spacer(Modifier.height(height * 2))

                Card(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .height(height * 15),
                    elevation = 30.dp,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MilitaryTech,
                            contentDescription = "ic_league_level",
                            Modifier.size(50.dp)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp),
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = rewardsPageViewModel.checkLevel().toString(),
                                    fontFamily = titleFontFamily,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier.padding(start = 5.dp, top = 3.dp),
                                    text = stringResource(id = R.string.bracket_level_string) + " " + rewardsPageViewModel.getLevel(
                                        rewardsPageViewModel.checkLevel().toString()
                                    ) + ")",
                                    fontFamily = descriptionFontFamily,
                                    fontWeight = Thin,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            if (rewardsPageViewModel.checkLevel()
                                    .toString() == stringResource(id = R.string.gold_string)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.you_are_already_in_the_top_string),
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = Medium,
                                    fontFamily = FontFamily.SansSerif
                                )
                            } else {
                                Text(
                                    text = rewardsPageViewModel.getDifference(
                                        rewardsPageViewModel.checkLevel().toString()
                                    ) + " " + stringResource(id = R.string.points_to_next_level_string),
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = Medium,
                                    fontFamily = FontFamily.SansSerif
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Log.d(
                                "Progress", rewardsPageViewModel.calculateProgress(
                                    rewardsPageViewModel.rewardPointsState.value
                                ).toString()
                            )

                            val animatedProgress = animateFloatAsState(
                                targetValue = rewardsPageViewModel.calculateProgress(
                                    rewardsPageViewModel.rewardPointsState.value
                                ),
                                animationSpec = tween(1000, easing = FastOutSlowInEasing)
                            )
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth(0.70f),
                                progress = animatedProgress.value
                            )
                        }

                    }

                }

                Spacer(Modifier.height(height * 2))

                Card(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .height(height * 25),
                    elevation = 30.dp,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 15.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.refer_your_friends_string),
                            fontFamily = titleFontFamily,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Text(
                                text = " " + stringResource(id = R.string.you_get_string),
                                fontFamily = descriptionFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = " " + stringResource(id = R.string.two_hundred_reward_points_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(10.dp),
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "ic_bullets",
                                tint = Orange
                            )
                            Text(
                                text = " " + stringResource(id = R.string.they_get_string),
                                fontFamily = descriptionFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = " " + stringResource(id = R.string.hundred_reward_points_string),
                                fontFamily = titleFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )

                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {

                            TextField(
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth(0.76f),
                                value = text,
                                maxLines = 1,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.DarkGray,
                                    backgroundColor = LightGray1,
                                    placeholderColor = Color.White,
                                    cursorColor = Color.Black,
                                    errorIndicatorColor = Color.Transparent,
                                    focusedLabelColor = Color.Black,
                                    errorCursorColor = Color.Black,
                                    errorLabelColor = Color.Red,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    unfocusedLabelColor = Orange,
                                ),
                                shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp),
                                onValueChange = {
                                })

                            Button(
                                modifier = Modifier
                                    .height(55.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    Toast.makeText(
                                        context,
                                        R.string.text_copied_to_clipboard_string,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp),
                                // Uses ButtonDefaults.ContentPadding by default
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                // Inner content including an icon and a text label
                                Icon(
                                    Icons.Filled.ContentCopy,
                                    contentDescription = "ic_copy_bt",
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(stringResource(id = R.string.copy_string))
                            }

                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = stringResource(id = R.string.you_have_referred_zero_friends_string),
                            fontFamily = titleFontFamily,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun BottomBox() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}

@Composable
fun BackgroundImage2() {
    Image(
        painter = painterResource(id = R.drawable.background_yellow_wave),
        contentDescription = "ic_background_image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}


//@Composable
//fun Reward(
//    navHostControllerLambda: () -> NavHostController,
//    rewardsPageViewModel: RewardsPageViewModel = viewModel()
//) {
//    ChangeBarColors(navigationBarColor = DarkYellow)
//    val context = LocalContext.current
//    val lazyListState = rememberLazyListState()
//    var itemsListState = listOf<Rewards>()
//
////    LaunchedEffect(key1 = Unit){
////        rewardsPageViewModel.getRewards(context)
////    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        //Background Image
//        BackgroundImage2()
//
//        //top bar
//        TopAppBar(
//            title = { Text(text = "Rewards", color = Color.White) },
//            backgroundColor = Color.Transparent,
//            elevation = 0.dp,
//            navigationIcon = {
//                IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = "",
//                        tint = Color.White
//                    )
//                }
//            },
//        )
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//
//            LazyRow(
//                state = lazyListState,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//            ) {
//                items(items = rewardsPageViewModel.rewards) { item ->
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(300.dp)
//                    ) {
//                        Card(
//                            modifier = Modifier
//                                .fillParentMaxWidth()
//                                .height(400.dp)
//                                .padding(start = 15.dp, end = 15.dp),
//                            elevation = 10.dp,
//                            shape = RoundedCornerShape(10),
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth(),
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ) {
//                                Text(
//                                    modifier = Modifier.padding(top = 30.dp),
//                                    text = item.code,
//                                    fontFamily = titleFontFamily,
//                                    fontSize = 24.sp
//                                )
//                                Text(
//                                    modifier = Modifier.padding(top = 30.dp),
//                                    text = item.description,
//                                    fontFamily = descriptionFontFamily,
//                                    fontSize = 24.sp
//                                )
//                                Text(
//                                    modifier = Modifier.padding(top = 60.dp),
//                                    text = item.title,
//                                    fontFamily = descriptionFontFamily,
//                                    fontSize = 24.sp
//                                )
//                            }
//                        }
//                    }
////                    Spacer(modifier = Modifier.width(20.dp))
////                    if (item == rewardsPageViewModel.rewards.last()) {
////                        val currentList = itemsListState
////
////                        val secondPart = currentList.subList(0, lazyListState.firstVisibleItemIndex)
////                        val firstPart = currentList.subList(lazyListState.firstVisibleItemIndex, currentList.size)
////
////                        rememberCoroutineScope().launch {
////                            val SCROLL_DX_INT = 100
////                            lazyListState.scrollToItem(0, maxOf(0, lazyListState.firstVisibleItemScrollOffset - SCROLL_DX_INT))
////                        }
////
////                        itemsListState = firstPart + secondPart
////                    }
//                }
//            }
//        }
//    }
////    LaunchedEffect(Unit) {
////        autoScroll(lazyListState)
////    }
//}