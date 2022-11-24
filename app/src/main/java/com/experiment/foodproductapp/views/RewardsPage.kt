package com.experiment.foodproductapp.views

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.ui.theme.*
import com.experiment.foodproductapp.viewmodels.RewardsPageViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Reward(
    email: String?,
    navHostControllerLambda: () -> NavHostController,
    rewardsPageViewModel: RewardsPageViewModel = koinViewModel()
) {
    ChangeBarColors(navigationBarColor = Color.White)

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        rewardsPageViewModel.getRewardPoints(email.toString())
        rewardsPageViewModel.text.value = rewardsPageViewModel.getRandomString(6)
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
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height * 14)
                            .padding(10.dp),
                        contentAlignment = CenterEnd
                    ) {
                        IconButton(modifier = Modifier.size(50.dp), onClick = {
                            rewardsPageViewModel.navigateToRewardsDetails(
                                navHostControllerLambda()
                            )
                        }) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                imageVector = Icons.Outlined.WorkspacePremium,
                                contentDescription = "ic_help_bt",
                                tint = Color.White
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .height(height * 15), elevation = 30.dp, shape = RoundedCornerShape(20.dp)
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
                                text = stringResource(id = R.string.equals_rs_string) + " " + rewardsPageViewModel.calculateEquals(),
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
                        .height(height * 18), elevation = 30.dp, shape = RoundedCornerShape(20.dp)
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
                                text = stringResource(id = rewardsPageViewModel.getRedeemStringId()),
                                modifier = Modifier.padding(5.dp),
                                fontFamily = titleFontFamily,
                                fontSize = 20.sp
                            )

                        }
                    }

                }

                Spacer(Modifier.height(height * 2))

                Card(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .height(height * 15)
                        .clickable(onClick = {
                            rewardsPageViewModel.navigateToRewardsDetails(
                                navHostControllerLambda()
                            )
                        }), elevation = 30.dp, shape = RoundedCornerShape(20.dp)
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
                                ), animationSpec = tween(1000, easing = FastOutSlowInEasing)
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
                            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp),
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

                        Button(
                            modifier = Modifier
                                .height(55.dp)
                                .fillMaxWidth(0.8f)
                                .padding(top = 10.dp, bottom = 10.dp)
                                .align(CenterHorizontally),
                            onClick = {
                                Log.d("Refer", "Reward: ${rewardsPageViewModel.text.value}")
                                val intent = Intent(Intent.ACTION_SEND).putExtra(
                                    Intent.EXTRA_TEXT, rewardsPageViewModel.text.value
                                ).setType("text/plain")
                                context.startActivity(Intent.createChooser(intent, "Share Using"))
                            },
                            shape = RoundedCornerShape(15.dp),
                            // Uses ButtonDefaults.ContentPadding by default
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            // Inner content including an icon and a text label
                            Icon(
                                Icons.Filled.Share,
                                contentDescription = "ic_share_bt",
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(stringResource(id = R.string.refer_your_friends_string))
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
        modifier = Modifier.fillMaxSize(),
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