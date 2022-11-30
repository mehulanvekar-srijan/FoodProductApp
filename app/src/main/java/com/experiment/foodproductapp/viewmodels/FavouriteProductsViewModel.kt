package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.dao.HomeItemsDao
import com.experiment.foodproductapp.database.dao.LikedItemsDao
import com.experiment.foodproductapp.database.dao.UserDao
import com.experiment.foodproductapp.database.entity.HomeItems
import com.experiment.foodproductapp.database.entity.LikedItems
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavouriteProductsViewModel(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    init {
        Log.d("testDI", "FavouriteProductsViewModel: ${databaseRepository.hashCode()}")
    }


    suspend fun fetchEmail(
        userDao: UserDao = databaseRepository.getUserDao()
    ): String {

        val d = viewModelScope.async(Dispatchers.IO){
            databaseRepository.getLoggedInUser(userDao)
        }

        return d.await().toString()
    }

    fun insertFavouriteProduct(
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

    suspend fun fetchFavouriteProductsByEmail(
        likedItemsDao: LikedItemsDao = databaseRepository.getLikedItemsDao(),
        email: String,
    ): List<LikedItems> {

        val productListDeferred = viewModelScope.async(Dispatchers.IO){
            databaseRepository.readItemsByEmail(likedItemsDao, email)
        }

        return productListDeferred.await()
    }


    suspend fun fetchHomeItemById(
        homeItemsDao: HomeItemsDao = databaseRepository.getHomeItemsDao(),
        id: Int,
    ) : HomeItems {

        val deferredHomeItem = viewModelScope.async(Dispatchers.IO){
            databaseRepository.readItemById(homeItemsDao,id)
        }

        return deferredHomeItem.await()
    }

    fun removeFromFavourites(
        likedItemsDao: LikedItemsDao = databaseRepository.getLikedItemsDao(),
        id: Int,
        email: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            databaseRepository.deleteItem(likedItemsDao = likedItemsDao, id = id, email = email)
        }
    }
}