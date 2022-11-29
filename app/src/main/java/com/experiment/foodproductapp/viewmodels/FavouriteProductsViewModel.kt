package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.experiment.foodproductapp.database.entity.LikedItems
import com.experiment.foodproductapp.repository.DatabaseRepository

class FavouriteProductsViewModel(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    init {
        Log.d("testDI", "FavouriteProductsViewModel: ${databaseRepository.hashCode()}")
    }

}