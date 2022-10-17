package com.experiment.foodproductapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenViewModel : ViewModel() {

    val splashDuration: Long = 3000  // Milliseconds

    fun execute() {
        viewModelScope.launch(Dispatchers.IO) {

            delay(splashDuration)

            withContext(Dispatchers.Main) {
                //Navigation Logic

            }
        }
    }

}