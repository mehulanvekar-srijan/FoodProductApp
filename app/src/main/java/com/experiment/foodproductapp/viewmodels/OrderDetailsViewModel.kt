package com.experiment.foodproductapp.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Index
import com.experiment.foodproductapp.database.OrderDetails
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderDetailsViewModel: ViewModel() {

    //private var _order = mutableStateListOf<OrderDetails>()
    var finalList = mutableListOf<List<OrderDetails>>()
    //val order = _order

    val email = "romi@romi.com"

    //val context = LocalContext.current

    // get no of orders count from data base

    var orderCount = 1

    // create list of OrderDetails for each order Id

    fun fetchOrderFromDb(context: Context, email: String, orderCount: Int): MutableList<OrderDetails> {
        var order = mutableStateListOf<OrderDetails>()
        viewModelScope.launch(Dispatchers.IO) {
            val list = DatabaseRepository(context).readAllOrderDetails(email, orderCount)
            list.forEach{ order.add(it) }
        }
        return order
    }


    // create list of of above list
    val orderlist= mutableListOf<OrderDetails>()

    fun fetchOrderList(context: Context) {

        do {
            var _order = fetchOrderFromDb(context = context, email, orderCount) //count = 1
            finalList.add(_order)
            orderCount++
        } while (_order != null)
    }

}
