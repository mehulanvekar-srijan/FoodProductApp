package com.experiment.foodproductapp.viewmodels

import android.content.Context
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

class HomeScreenViewModel : ViewModel() {

    private var _userEmail = mutableStateOf("")

    //Create empty state list object of homeItems
    private val _homeItems: MutableState<List<HomeItems>> = mutableStateOf(listOf())
    val homeItems: State<List<HomeItems>> = _homeItems

    //creating empty object
    val productForDetailPage = mutableStateOf(HomeItems())

    fun initHomeItems(context: Context){
        viewModelScope.launch(Dispatchers.IO){
            _homeItems.value = DatabaseRepository(context).readAllItems()
        }
    }

    fun addProduct(newItem: HomeItems) {
        productForDetailPage.value = newItem
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


    fun addProductToCart(homeItem: HomeItems, context: Context) {
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
            )

            //Inset into Product Table
            try { DatabaseRepository(context).addProduct(product) }
            catch (e: android.database.sqlite.SQLiteConstraintException) { }
        }
    }

    private fun removeProductFromDatabase(context: Context,productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(context).removeProduct(id = productId, email = _userEmail.value)
        }
    }

    //Get count from db and set state
    fun getProductCount(context: Context, id: Int, state: MutableState<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.value = DatabaseRepository(context).getCount(id = id, email = _userEmail.value)
            } catch (e: android.database.sqlite.SQLiteConstraintException) { }
        }
    }

    //Get current count from db, increment value, set state
    fun incrementProductCount(context: Context, id: Int, state: MutableState<Int>) {
        if (state.value == 0) {
            addProductToCart(productForDetailPage.value, context)
            state.value += 1
        }
        else {
            viewModelScope.launch(Dispatchers.IO) {

                var currentCount =
                    DatabaseRepository(context).getCount(id = id, email = _userEmail.value)
                currentCount += 1

                DatabaseRepository(context).setCount(
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
    fun decrementProductCount(context: Context, id: Int, state: MutableState<Int>) {

        viewModelScope.launch(Dispatchers.IO) {

            var currentCount =
                DatabaseRepository(context).getCount(id = id, email = _userEmail.value)
            if (currentCount != 0) {
                currentCount -= 1
            }

            DatabaseRepository(context).setCount(
                id = id,
                count = currentCount,
                email = _userEmail.value
            ) //set count in db

            if (currentCount == 0) {
                // remove product
                removeProductFromDatabase(context, productForDetailPage.value.id)
            }
            state.value = currentCount //set count of UI state
            DatabaseRepository(context).setCount(
                id = id,
                count = currentCount,
                email = _userEmail.value
            ) //set count in db
            if (currentCount == 0) {
                // remove product
                removeProductFromDatabase(context, productForDetailPage.value.id)
            }
            getProductCount(
                context = context,
                id = id,
                state = state
            )          //set count of UI state
        }
    }
}

