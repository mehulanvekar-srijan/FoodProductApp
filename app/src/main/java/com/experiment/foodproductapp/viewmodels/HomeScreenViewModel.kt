package com.experiment.foodproductapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen

class HomeScreenViewModel : ViewModel() {

    private var userEmail  = mutableStateOf("")

    fun setEmail(email: String?) {
        if(email != null) userEmail.value = email
    }

     val productsList =  listOf(
        "https://www.bigbasket.com/media/uploads/p/xxl/40213061_2-coolberg-non-alcoholic-beer-malt.jpg",
        "https://www.bigbasket.com/media/uploads/p/xxl/40122150_2-coolberg-beer-mint-non-alcoholic.jpg",
        "https://www.bigbasket.com/media/uploads/p/xxl/40213060_2-coolberg-non-alcoholic-beer-cranberry.jpg",
        "https://www.bigbasket.com/media/uploads/p/xxl/40174620_2-budweiser-00-non-alcoholic-beer.jpg",
    )


    fun navigateToUserDetails(navHostController: NavHostController){
        navHostController.navigate(Screen.UserDetails.routeWithDate(userEmail.value)) {
            popUpTo(Screen.SignInScreen.route) { inclusive = true }
        }
    }

}