package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.dao.LikedItemsDao
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


    suspend fun fetchEmail(
        dao: UserDao = databaseRepository.getUserDao()
    ): String {

        val d = viewModelScope.async(Dispatchers.IO){
            databaseRepository.getLoggedInUser(dao)
        }

        return d.await().toString()
    }

    suspend fun insertFavouriteProduct(
        likedItemsDao: LikedItemsDao = databaseRepository.getLikedItemsDao(),
        likedItems: LikedItems,
    ){
        viewModelScope.launch(Dispatchers.IO){
            databaseRepository.insertLikedItem(likedItemsDao,likedItems)
        }
    }

    suspend fun fetchFavouriteProducts(
        likedItemsDao: LikedItemsDao = databaseRepository.getLikedItemsDao()
    ): List<LikedItems> {

        val productListDeferred = viewModelScope.async(Dispatchers.IO){
            databaseRepository.readAllLikedItems(likedItemsDao)
        }
        return productListDeferred.await()

    }

}