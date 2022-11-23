package com.experiment.foodproductapp.viewmodels

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

class OrderDetailsViewModel(
    private val databaseRepository: DatabaseRepository,
): ViewModel() {

    init {
        Log.d("testDI", "OrderDetailsViewModel: ${databaseRepository.hashCode()}")
    }

    val _finalList = mutableStateListOf<MutableList<OrderDetails>>()
    val finalList = _finalList

    val _priceList = mutableStateListOf<Int>()
    val priceList = _priceList

    private val email = mutableStateOf("")

    // to access the order on description page
    private val _orderDetails = mutableListOf<OrderDetails>()
    val orderDetails = _orderDetails

    private val _finalAmount = mutableStateOf(-1.0)
    val finalAmount = _finalAmount

    fun setEmail(mail: String?) {
        email.value = mail.toString()
    }

    //assign the details of the order clicked on
    fun addOrderId(newOrderId: Int) {
        _orderDetails.clear()
        // fetch the order from db
        viewModelScope.launch(Dispatchers.IO) {
            val list = databaseRepository.readAllOrderDetails(email.value, newOrderId)
            list.forEach {
                _orderDetails.add(it)
            }
        }
    }


    fun navigateToProductOrderDescriptionPage(navHostController: NavHostController) {
        navHostController.navigate(Screen.OrderDescriptionPage.route) {
            popUpTo(Screen.OrderDetails.route) { inclusive = false }
        }
    }


    fun calculateSum(item: List<OrderDetails>): Int {
        var sum = 0
        var index=0
        do {
            sum += item[index].price * item[index].count
            index++
        } while (index < item.size)
        return sum
    }

    fun calculateRedeemedAmount() {
        viewModelScope.launch(Dispatchers.IO) {
            finalAmount.value =
                databaseRepository.getFinalPrice(email.value, orderDetails[0].orderId)
        }
    }


    fun navigateToHomeScreenPage(navHostController: NavHostController) {
        navHostController.navigate(Screen.HomeScreen.routeWithData(email.value)) {
            popUpTo(Screen.HomeScreen.route) { inclusive = true }
        }
    }

    fun fetchOrderList() {
        var orderCount = 1
        viewModelScope.launch(Dispatchers.IO) {
            do {
                val order = mutableListOf<OrderDetails>()
                val list = databaseRepository.readAllOrderDetails(email.value, orderCount)

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
                    _finalList.add(order)
                    var sum = 0.0
                    sum = databaseRepository.getFinalPrice(email.value, order[0].orderId)
                    _priceList.add(sum.toInt())
                    Log.d("orderDetails", "fetchOrderList: ")
                }


                orderCount++

            } while (list.isNotEmpty())

            for (element in _priceList) {
                Log.d("checkSum", "fetchOrderList: $element")
            }
        }

        Log.d("orderDetails", "count of total orders: ${_finalList.count()}")
    }
}
