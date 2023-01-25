package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.dao.LikedItemsDao
import com.experiment.foodproductapp.database.entity.HomeItems
import com.experiment.foodproductapp.database.entity.LikedItems
import com.experiment.foodproductapp.database.entity.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailsPageViewModel(
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private var _userEmail = mutableStateOf("")
    val userEmail = _userEmail

    private val _cartItemCount: MutableState<Int> = mutableStateOf(0)
    val cartItemCount: State<Int> = _cartItemCount

    //creating empty object
    private val _productForDetailPage = mutableStateOf(HomeItems())
    val productForDetailPage = _productForDetailPage

    private val _quantity =  mutableStateOf(0)
    val quantity = _quantity


    fun addProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _productForDetailPage.value  = databaseRepository.readOrderId(productId)
            _quantity.value = databaseRepository.getCount(id = _productForDetailPage.value.id, email = _userEmail.value)
        }
    }

    fun setEmail(email: String?) {
        if (email != null) _userEmail.value = email
    }

    fun addProductToCart(homeItem: HomeItems) {
        viewModelScope.launch(Dispatchers.IO) {

            //Convert HomeItem to Product Object
            val product = Product(
                email = _userEmail.value,
                id = homeItem.id,
                url = homeItem.url,
                title = homeItem.title,
                description = homeItem.description,
                price = homeItem.price,
                count = homeItem.count,
                alcohol = homeItem.alcohol
            )

            //Inset into Product Table
            try {
                databaseRepository.addProduct(product)
                initCartItemsCount()
            } catch (_: android.database.sqlite.SQLiteConstraintException) {
            }
        }
    }

    private fun removeProductFromDatabase(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.removeProduct(id = productId, email = _userEmail.value)
            initCartItemsCount()
        }
    }

    //Get count from db and set state
    fun getProductCount() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _quantity.value = databaseRepository.getCount(id = _productForDetailPage.value.id, email = _userEmail.value)
            } catch (_: android.database.sqlite.SQLiteConstraintException) { }
        }
    }

    //Get current count from db, increment value, set state
    fun incrementProductCount() {
        if (_quantity.value == 0) {
            addProductToCart(productForDetailPage.value)
            _quantity.value += 1
        } else {
            viewModelScope.launch(Dispatchers.IO) {

                var currentCount =
                    databaseRepository.getCount(id = _productForDetailPage.value.id, email = _userEmail.value)
                currentCount += 1

                databaseRepository.setCount(
                    id = _productForDetailPage.value.id,
                    count = currentCount,
                    email = _userEmail.value
                ) //set count in db
            }
            //after updating count or adding product update count state in UI
            _quantity.value += 1
        }
    }

    //Get current count from db, decrement value, set state
    fun decrementProductCount() {

        viewModelScope.launch(Dispatchers.IO) {

            var currentCount =
                databaseRepository.getCount(id = _productForDetailPage.value.id, email = _userEmail.value)
            if (currentCount != 0) {
                currentCount -= 1
            }

            if (currentCount == 0) {
                // remove product
                removeProductFromDatabase(productForDetailPage.value.id)
            }

            databaseRepository.setCount(
                id = _productForDetailPage.value.id,
                count = currentCount,
                email = _userEmail.value
            ) //set count in db

            getProductCount()          //set count of UI state
        }
    }

    fun initCartItemsCount() {
        viewModelScope.launch(Dispatchers.IO){
            val cartList = databaseRepository.readAllProducts(_userEmail.value)
            _cartItemCount.value = cartList.size

            Log.d("testBadge", "initCartItemsCount: ${cartItemCount.value}")
        }
    }


    //Liked Feature methods
    suspend fun fetchFavouriteProductsByEmail(
        likedItemsDao: LikedItemsDao = databaseRepository.getLikedItemsDao(),
        email: String,
    ): List<LikedItems> {

        val productListDeferred = viewModelScope.async(Dispatchers.IO){
            databaseRepository.readItemsByEmail(likedItemsDao, email)
        }

        return productListDeferred.await()
    }

    fun insertFavouriteProduct(
        likedItemsDao: LikedItemsDao = databaseRepository.getLikedItemsDao(),
        likedItems: LikedItems,
    ) {
        viewModelScope.launch(Dispatchers.IO){
            databaseRepository.insertLikedItem(likedItemsDao,likedItems)
        }
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

