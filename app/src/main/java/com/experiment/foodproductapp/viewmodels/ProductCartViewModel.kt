package com.experiment.foodproductapp.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductCartViewModel : ViewModel() {

    private val _cartList = mutableStateOf(listOf<Product>())
    val cartList = _cartList

    fun fetchCartList(context: Context){
        viewModelScope.launch(Dispatchers.IO){
            _cartList.value = DatabaseRepository(context).readAllProducts()

        }
    }
}