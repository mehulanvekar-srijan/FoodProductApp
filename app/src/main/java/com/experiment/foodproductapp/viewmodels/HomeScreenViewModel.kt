package com.experiment.foodproductapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class HomeScreenViewModel : ViewModel() {

     val productsList =  listOf(
        "https://www.bigbasket.com/media/uploads/p/xxl/40213061_2-coolberg-non-alcoholic-beer-malt.jpg",
        "https://www.bigbasket.com/media/uploads/p/xxl/40122150_2-coolberg-beer-mint-non-alcoholic.jpg",
        "https://www.bigbasket.com/media/uploads/p/xxl/40213060_2-coolberg-non-alcoholic-beer-cranberry.jpg",
        "https://www.bigbasket.com/media/uploads/p/xxl/40174620_2-budweiser-00-non-alcoholic-beer.jpg",
    )



}