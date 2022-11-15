package com.experiment.foodproductapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.database.Rewards
import com.experiment.foodproductapp.ui.theme.ChangeBarColors
import com.experiment.foodproductapp.ui.theme.DarkYellow
import com.experiment.foodproductapp.ui.theme.descriptionFontFamily
import com.experiment.foodproductapp.ui.theme.titleFontFamily
import com.experiment.foodproductapp.viewmodels.RewardsPageViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DELAY_BETWEEN_SCROLL_MS = 8L
private const val SCROLL_DX = 1f

@Composable
fun Reward(
    navHostControllerLambda: () -> NavHostController,
    rewardsPageViewModel: RewardsPageViewModel = viewModel()
) {
    ChangeBarColors(navigationBarColor = DarkYellow)
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    var itemsListState = listOf<Rewards>()

//    LaunchedEffect(key1 = Unit){
//        rewardsPageViewModel.getRewards(context)
//    }

    Box(modifier = Modifier.fillMaxSize()) {
        //Background Image
        BackgroundImage2()

        //top bar
        TopAppBar(
            title = { Text(text = "Rewards", color = Color.White) },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            navigationIcon = {
                IconButton(onClick = { navHostControllerLambda().navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            },
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                items(items = rewardsPageViewModel.rewards) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(400.dp)
                                .padding(start = 15.dp, end = 15.dp),
                            elevation = 10.dp,
                            shape = RoundedCornerShape(10),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 30.dp),
                                    text = item.code,
                                    fontFamily = titleFontFamily,
                                    fontSize = 24.sp
                                )
                                Text(
                                    modifier = Modifier.padding(top = 30.dp),
                                    text = item.description,
                                    fontFamily = descriptionFontFamily,
                                    fontSize = 24.sp
                                )
                                Text(
                                    modifier = Modifier.padding(top = 60.dp),
                                    text = item.title,
                                    fontFamily = descriptionFontFamily,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    if (item == rewardsPageViewModel.rewards.last()) {
                        val currentList = itemsListState

                        val secondPart = currentList.subList(0, lazyListState.firstVisibleItemIndex)
                        val firstPart = currentList.subList(lazyListState.firstVisibleItemIndex, currentList.size)

                        rememberCoroutineScope().launch {
                            val SCROLL_DX_INT = 100
                            lazyListState.scrollToItem(0, maxOf(0, lazyListState.firstVisibleItemScrollOffset - SCROLL_DX_INT))
                        }

                        itemsListState = firstPart + secondPart
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        autoScroll(lazyListState)
    }
}

private tailrec suspend fun autoScroll(lazyListState: LazyListState) {
    lazyListState.scroll(MutatePriority.PreventUserInput) {
        scrollBy(SCROLL_DX)
    }
    delay(DELAY_BETWEEN_SCROLL_MS)

    autoScroll(lazyListState)
}

@Composable
fun BackgroundImage2() {
    Image(
        painter = painterResource(id = R.drawable.background_yellow_wave),
        contentDescription = "Background Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}