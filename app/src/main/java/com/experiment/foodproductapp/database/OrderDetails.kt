package com.experiment.foodproductapp.database

import androidx.room.Entity

@Entity(primaryKeys = ["email","id"])
class OrderDetails(
    var orderId :Int,
    var canceled :Boolean,
) : Product(id = -1, url = "", title = "", description = "", price = -1)