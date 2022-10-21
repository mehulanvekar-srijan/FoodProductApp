package com.experiment.foodproductapp.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.experiment.foodproductapp.viewmodels.UserDetailsViewModel

@Composable
fun UserDetails(
    email : String?,
    userDetailsViewModel: UserDetailsViewModel = viewModel(),
) {
    if (email != null){

        val context = LocalContext.current

        LaunchedEffect(key1 = Unit){
            userDetailsViewModel.execute(context,email)
        }

        Text(text = userDetailsViewModel.user.value.toString())
    }
}