package com.experiment.foodproductapp.viewmodels

import androidx.lifecycle.ViewModel
import com.experiment.foodproductapp.database.OrderDetails

class OrderDetailsViewModel: ViewModel() {

    val orderlist= mutableListOf<OrderDetails>()


}
