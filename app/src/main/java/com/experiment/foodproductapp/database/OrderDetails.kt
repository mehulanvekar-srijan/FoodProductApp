package com.experiment.foodproductapp.database

import androidx.room.Entity

@Entity(primaryKeys = ["email", "id", "orderId"])
class OrderDetails(
    email: String,
    count: Int,
    id: Int,
    url: String,
    title: String,
    description: String,
    price: Int,
    var orderId: Int,
    var canceled: Boolean,
) : Product(
    email = email,
    id = id,
    count = count,
    url = url,
    title = title,
    description = description,
    price = price
)