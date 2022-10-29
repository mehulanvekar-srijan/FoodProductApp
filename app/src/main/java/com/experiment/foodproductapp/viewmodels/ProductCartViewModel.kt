package com.experiment.foodproductapp.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductCartViewModel : ViewModel() {

    private var _cartList = mutableStateListOf<Product>()
    val cartList = _cartList

//    private val _totalPrice = mutableStateOf(0)
//    val totalPrice = _totalPrice

    fun onDismiss(context: Context,item: Product){
        viewModelScope.launch(Dispatchers.IO){
            removeFromProductList(item)
            removeFromDatabase(context,item)
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
        }
    }

    fun computePrice(): Int{
        var sum = 0
        _cartList.forEach{ sum += it.price }
        return sum
    }
}