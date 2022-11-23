package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.entity.OrderDetails
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderDetailsViewModel: ViewModel() {

    var finalList = mutableStateListOf<MutableList<OrderDetails>>()
    val email = mutableStateOf("")

    // to access the order on description page
    var orderDetails = mutableListOf<OrderDetails>()
    //var orderDetailsId = mutableStateOf<Int>(0)

    val finalAmount = mutableStateOf<Double>(-1.0)


    //assign the details of the order clicked on
    fun addOrderId(context: Context,newOrderId: Int) {
        // fetch the order from db
        viewModelScope.launch(Dispatchers.IO) {

            val list = DatabaseRepository(context).readAllOrderDetails(email.value, newOrderId)

            list.forEach {
                orderDetails.add(it)
            }

        }
    }


    fun navigateToProductOrderDescriptionPage(navHostController: NavHostController) {
        navHostController.navigate(Screen.OrderDescriptionPage.route) {
            popUpTo(Screen.OrderDetails.route) { inclusive = false }
        }
    }


    fun calculateSum(item: MutableList<OrderDetails>):Int {
        var sum=0
        var index=0
        do {
            sum += item[index].price * item[index].count
            index++
        } while (index < item.size)
        return sum
    }

    fun calculateRedeemedAmount(context: Context) {

        viewModelScope.launch(Dispatchers.IO) {
            finalAmount.value =
                DatabaseRepository(context).getFinalPrice(email.value, orderDetails[0].orderId)
        }
    }


    fun navigateToHomeScreenPage(navHostController: NavHostController) {
        navHostController.navigate(Screen.HomeScreen.routeWithData(email.value)) {
            popUpTo(Screen.HomeScreen.route) { inclusive = true }
        }
    }

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
    }
}
