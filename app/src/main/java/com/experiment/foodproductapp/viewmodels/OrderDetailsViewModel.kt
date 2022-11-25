package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _finalList = mutableStateListOf<MutableList<OrderDetails>>()
    val finalList = _finalList

    private val _priceMap = mutableStateMapOf<Int,Int>()
    val priceMap = _priceMap

    private val email = mutableStateOf("")
    val userEmail = email

    private val _finalAmount = mutableStateOf(-1.0)
    val finalAmount = _finalAmount

    fun setEmail(mail: String?) {
        email.value = mail.toString()
    }

    // to access the order on description page
    private val _orderDetails = mutableStateListOf<OrderDetails>()
    val orderDetails = _orderDetails

    //assign the details of the order clicked on
    fun addOrderId(newOrderId: Int) {

        _orderDetails.clear()

        // fetch the order from db
        viewModelScope.launch(Dispatchers.IO) {
            val list = databaseRepository.readAllOrderDetails(email.value, newOrderId)
            list.forEach { _orderDetails.add(it) }
        }

    }

    //moved navigation to view
//    fun navigateToProductOrderDescriptionPage(navHostController: NavHostController) {
//        navHostController.navigate(Screen.OrderDescriptionPage.route) {
//            popUpTo(Screen.OrderDetails.route) { inclusive = false }
//        }
//    }


    fun calculateSum(list: List<OrderDetails>): Int {
        var sum = 0
        var index = 0
        do {
            sum += list[index].price * list[index].count
            index++
        } while (index < list.size)
        return sum
    }

    fun calculateRedeemedAmount() {
        viewModelScope.launch(Dispatchers.IO) {
            finalAmount.value =
                databaseRepository.getFinalPrice(email.value, orderDetails[0].orderId)
        }
    }

        //moved navigation to view
//    fun navigateToHomeScreenPage(navHostController: NavHostController) {
//        navHostController.navigate(Screen.HomeScreen.routeWithData(email.value)) {
//            popUpTo(Screen.HomeScreen.route) { inclusive = true }
//        }
//    }

    fun fetchOrderList() {

        var orderId = 1

        viewModelScope.launch(Dispatchers.IO) {

            do {
                val list: MutableList<OrderDetails> = databaseRepository.readAllOrderDetails(email.value, orderId)

                if (list.isNotEmpty()) {

                    _finalList.add(list)

                    val finalSum: Double = databaseRepository.getFinalPrice(email.value, orderId)

                    _priceMap[orderId] = finalSum.toInt()
                }

                orderId++

            } while (list.isNotEmpty())

        }

    }
}
