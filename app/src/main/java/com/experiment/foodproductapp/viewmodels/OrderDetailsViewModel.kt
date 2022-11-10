package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Index
import com.experiment.foodproductapp.database.OrderDetails
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderDetailsViewModel: ViewModel() {

    var finalList = mutableStateListOf<MutableList<OrderDetails>>()
    val email = mutableStateOf("")

    fun fetchOrderList(context: Context) {
        var orderCount = 1
        viewModelScope.launch(Dispatchers.IO) {
            do {
                val order = mutableListOf<OrderDetails>()
                val list = DatabaseRepository(context).readAllOrderDetails(email.value, orderCount)

                Log.d("orderDetails", " list value fetchOrderList: $list")


                list.forEach {
                    order.add(it)
                }

                for (element in order) {
                    Log.d(
                        "orderDetails",
                        " list value fetchOrderList: ${element.title} and count of orderis ${order.count()}"
                    )

                }
                if (list.isNotEmpty()) {
                    //finalList.add(order.subList(0,order.count()))
                    finalList.add(order)
                    Log.d("orderDetails", "fetchOrderList: ")
                }

                orderCount++

            } while (list.isNotEmpty())
        }

        Log.d("orderDetails", "count of total orders: ${finalList.count()}")



        for (element in finalList) {
            val ord = element
            Log.d("orderDetails", "no of prod in each item: ${ord.count()}")
            for (element in ord) {
                Log.d("orderDetails", "title : ${element.title}   number: ${element.orderId}")
            }
        }
    }
}
