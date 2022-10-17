package com.experiment.foodproductapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.experiment.foodproductapp.viewmodels.SplashScreenViewModel

@Preview(showBackground = true)
@Composable
fun SplashScreenPage(
    splashScreenViewModel: SplashScreenViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "",
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize(0.9f),
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
        )
    }

    LaunchedEffect(key1 = Unit) {
        splashScreenViewModel.execute()
    }
}

