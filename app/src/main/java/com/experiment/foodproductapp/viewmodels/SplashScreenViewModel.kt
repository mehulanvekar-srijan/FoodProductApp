package com.experiment.foodproductapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenViewModel : ViewModel() {

    fun execute() {
        viewModelScope.launch(Dispatchers.IO) {

            delay(3000)

            withContext(Dispatchers.Main) {
                //Navigation Logic

            }
        }
    }

}