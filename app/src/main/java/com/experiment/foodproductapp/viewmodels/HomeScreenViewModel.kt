package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.entity.HomeItems
import com.experiment.foodproductapp.database.entity.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    init {
        Log.d("testDI", "HomeScreenViewModel: ${databaseRepository.hashCode()}")
    }

    private var _userEmail = mutableStateOf("")

    //Create empty state list object of homeItems
    private val _homeItems: MutableState<List<HomeItems>> = mutableStateOf(listOf())
    val homeItems: State<List<HomeItems>> = _homeItems

    private val _cartItemCount: MutableState<Int> = mutableStateOf(0)
    val cartItemCount: State<Int> = _cartItemCount

    //creating empty object
    private val _productForDetailPage = mutableStateOf(HomeItems())
    val productForDetailPage = _productForDetailPage

    fun initHomeItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeItems.value = databaseRepository.readAllItems()
        }
    }

    fun addProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
           _productForDetailPage.value  = databaseRepository.readOrderId(productId)
        }
    }

    fun setEmail(email: String?) {
        if (email != null) _userEmail.value = email
    }

    //Navigation
    fun navigateToUserDetails(navHostController: NavHostController) {
        navHostController.navigate(Screen.UserDetails.routeWithData(_userEmail.value))
    }

    fun navigateToProductCart(navHostController: NavHostController) {
        navHostController.navigate(Screen.ProductCart.routeWithData(_userEmail.value))
    }

    fun navigateToOrderDetailsPage(navHostController: NavHostController) {
        navHostController.navigate(Screen.OrderDetails.routeWithData(_userEmail.value))
    }

    fun navigateToProductDetailsPage(navHostController: NavHostController) {
        navHostController.navigate(Screen.ProductDetailsScreen.route) {
            popUpTo(Screen.HomeScreen.route) { inclusive = false }
        }
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
        }
    }

    //Get count from db and set state
    fun getProductCount(id: Int, state: MutableState<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.value = databaseRepository.getCount(id = id, email = _userEmail.value)
            } catch (_: android.database.sqlite.SQLiteConstraintException) {
            }
        }
    }

    //Get current count from db, increment value, set state
    fun incrementProductCount(id: Int, state: MutableState<Int>) {
        if (state.value == 0) {
            addProductToCart(productForDetailPage.value)
            state.value += 1
        } else {
            viewModelScope.launch(Dispatchers.IO) {

                var currentCount =
                    databaseRepository.getCount(id = id, email = _userEmail.value)
                currentCount += 1

                databaseRepository.setCount(
                    id = id,
                    count = currentCount,
                    email = _userEmail.value
                ) //set count in db
            }
            //after updating count or adding product update count state in UI
            state.value += 1
        }

    }

    //Get current count from db, decrement value, set state
    fun decrementProductCount(id: Int, state: MutableState<Int>) {

        viewModelScope.launch(Dispatchers.IO) {

            var currentCount =
                databaseRepository.getCount(id = id, email = _userEmail.value)
            if (currentCount != 0) {
                currentCount -= 1
            }

            databaseRepository.setCount(
                id = id,
                count = currentCount,
                email = _userEmail.value
            ) //set count in db

            if (currentCount == 0) {
                // remove product
                removeProductFromDatabase(productForDetailPage.value.id)
            }
            state.value = currentCount //set count of UI state
            databaseRepository.setCount(
                id = id,
                count = currentCount,
                email = _userEmail.value
            ) //set count in db
            if (currentCount == 0) {
                // remove product
                removeProductFromDatabase(productForDetailPage.value.id)
            }
            getProductCount(
                id = id,
                state = state
            )          //set count of UI state
        }
    }

    fun initCartItemsCount(){
        viewModelScope.launch(Dispatchers.IO){
            val cartList = databaseRepository.readAllProducts(_userEmail.value)
            _cartItemCount.value = cartList.size

            Log.d("testBadge", "initCartItemsCount: ${cartItemCount.value}")
        }
    }
}

