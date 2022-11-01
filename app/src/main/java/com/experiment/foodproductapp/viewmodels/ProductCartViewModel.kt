package com.experiment.foodproductapp.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductCartViewModel : ViewModel() {

    private var _cartList = mutableStateListOf<Product>()
    val cartList = _cartList

    fun onDismiss(context: Context,item: Product){
        viewModelScope.launch(Dispatchers.IO){
            removeFromProductList(item)
            removeFromDatabase(context,item)
            computePrice()
        }
    }

    private fun removeFromProductList(item: Product) = _cartList.remove(item)

    private fun removeFromDatabase(context: Context,item: Product){
        viewModelScope.launch(Dispatchers.IO){
            DatabaseRepository(context).removeProduct(item.id)
        }
    }

    fun fetchCartList(context: Context){
        viewModelScope.launch(Dispatchers.IO){
            val list = DatabaseRepository(context).readAllProducts()
            list.forEach{ _cartList.add(it) }
            computePrice()
        }
    }

    //===== count feature =====
    private val _sum = mutableStateOf(initPrice())
    val sum = _sum

    private fun initPrice(): Int{
        var sum = 0
        _cartList.forEach{ sum += (it.price * it.count) }
        return sum
    }

    private fun computePrice(){
        var sum = 0
        _cartList.forEach{ sum += (it.price * it.count) }
        _sum.value = sum
    }

    //Get count from db and set state
    fun getProductCount(context: Context,id: Int,state: MutableState<Int>){
        viewModelScope.launch(Dispatchers.IO){
            state.value = DatabaseRepository(context).getCount(id)
        }
    }

    //Get current count from db, increment value, set state
    fun incrementProductCount(context: Context,id: Int,state: MutableState<Int>) {

        viewModelScope.launch(Dispatchers.IO){

            var currentCount = DatabaseRepository(context).getCount(id)

            currentCount += 1

            DatabaseRepository(context).setCount(id = id, count = currentCount) //set count in db
            getProductCount(context = context, id = id, state = state)          //set count of UI state
            _cartList.forEach { if(it.id == id) it.count = currentCount }       //set count of list in RAM
            // or state.value = currentCount

            computePrice()   // update sum vale
        }

    }

    //Get current count from db, decrement value, set state
    fun decrementProductCount(context: Context,id: Int,state: MutableState<Int>) {

        viewModelScope.launch(Dispatchers.IO){

            var currentCount = DatabaseRepository(context).getCount(id)

            currentCount -= 1

            DatabaseRepository(context).setCount(id = id, count = currentCount) //set count in db
            getProductCount(context = context, id = id, state = state)          //set count of UI state
            _cartList.forEach { if(it.id == id) it.count = currentCount }       //set count of list in RAM
            // or state.value = currentCount

            computePrice()     // update sum vale
        }

    }
}