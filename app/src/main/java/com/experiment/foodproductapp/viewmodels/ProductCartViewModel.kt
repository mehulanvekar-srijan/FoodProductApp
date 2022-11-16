package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.math.abs

class ProductCartViewModel : ViewModel() {

    private var _cartList = mutableStateListOf<Product>()
    val cartList = _cartList

    private val _email = mutableStateOf("")
    val email = _email

    fun onDismiss(context: Context,item: Product) {
        viewModelScope.launch(Dispatchers.IO){
            removeFromProductList(item)
            removeFromDatabase(context,item)
            updateSum()
            updateFinalSum()
        }
    }

    private fun removeFromProductList(item: Product) = _cartList.remove(item)

    private fun removeFromDatabase(context: Context,item: Product){
        viewModelScope.launch(Dispatchers.IO){
            DatabaseRepository(context).removeProduct(id = item.id, email = email.value)
        }
    }

    fun fetchCartList(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val list = DatabaseRepository(context).readAllProducts(_email.value)
            list.forEach{ _cartList.add(it) }
            updateSum()
            updateFinalSum()
        }
    }

    //===== count feature =====
    private val _sum = mutableStateOf(computeSum())
    val sum = _sum

    private fun computeSum(): Int = _cartList.fold(0) { result, value ->
            result + (value.price * value.count)
    }

    private fun updateSum() {
        _sum.value = computeSum()
    }

    //Get count from db and set state
    fun getProductCount(context: Context,id: Int,state: MutableState<Int>){
        viewModelScope.launch(Dispatchers.IO){
            state.value = DatabaseRepository(context).getCount(id = id, email = email.value)
        }
    }

    //Get current count from db, increment value, set state
    fun incrementProductCount(context: Context,id: Int,state: MutableState<Int>) {

        viewModelScope.launch(Dispatchers.IO){

            var currentCount = DatabaseRepository(context).getCount(id = id, email = email.value)

            currentCount += 1

            DatabaseRepository(context).setCount(id = id, count = currentCount, email = email.value) //set count in db
            getProductCount(context = context, id = id, state = state)          //set count of UI state
            _cartList.forEach { if(it.id == id) it.count = currentCount }       //set count of list in RAM
            // or state.value = currentCount

            updateSum()   // update sum vale

            updateFinalSum()   // update final sum value after Redeemed pts
        }

    }

    //Get current count from db, decrement value, set state
    fun decrementProductCount(context: Context,id: Int,state: MutableState<Int>) {

        viewModelScope.launch(Dispatchers.IO){

            var currentCount = DatabaseRepository(context).getCount(id = id, email = email.value)

            currentCount -= 1

            DatabaseRepository(context).setCount(id = id, count = currentCount, email = email.value) //set count in db
            getProductCount(context = context, id = id, state = state)          //set count of UI state
            _cartList.forEach { if(it.id == id) it.count = currentCount }       //set count of list in RAM
            // or state.value = currentCount

            updateSum()     // update sum vale

            updateFinalSum()   // update final sum value after Redeemed pts
        }

    }

    fun navigateToCheckout(navHostController: NavHostController){
        //Before navigating compute remaining redeemAmount
        val value = _redeemAmount.value - _finalSum.value
        if(value < 0) _redeemAmount.value = 0
        else _redeemAmount.value = abs(value)

        //Clear the cart list
        _cartList.clear()

        Log.d("testredeemAmount", "ProductCartViewModel: email=${email.value} , finalSum=${finalSum.value} , redeemAmount=${redeemAmount.value}")

        navHostController.navigate(
            Screen.CheckoutPage.routeWithData(email.value,finalSum.value,redeemAmount.value)
        )
    }


    //===== Redeem Feature =====
    private val _redeemAmount = mutableStateOf(0)
    val redeemAmount = _redeemAmount

    fun initRedeemAmount(context: Context, email: String) {
        viewModelScope.launch(Dispatchers.IO){
            //Fetch Redeem Amount
            _redeemAmount.value = DatabaseRepository(context).getRedeemedAmount(email)
        }
    }

    private val _finalSum = mutableStateOf(0)
    val finalSum = _finalSum

    private fun updateFinalSum() {
        val value = _sum.value - _redeemAmount.value
        if(value < 0) _finalSum.value = 0
        else _finalSum.value = _sum.value - _redeemAmount.value
    }
}