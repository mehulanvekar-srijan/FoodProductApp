package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.dao.UserDao
import com.experiment.foodproductapp.database.entity.LikedItems
import com.experiment.foodproductapp.database.entity.User
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavouriteProductsViewModel(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    init {
        Log.d("testDI", "FavouriteProductsViewModel: ${databaseRepository.hashCode()}")
    }



    //Test
    suspend fun fetchEmail(dao: UserDao): String {

        val d = viewModelScope.async(Dispatchers.IO){
            databaseRepository.getUser(dao)
        }

        return d.await().toString()
    }

}