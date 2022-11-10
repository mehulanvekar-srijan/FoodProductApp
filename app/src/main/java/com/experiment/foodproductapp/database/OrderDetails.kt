package com.experiment.foodproductapp.database

import androidx.room.Entity

@Entity
class OrderDetails(
    email: String,
    id: Int,
    url: String,
    title: String,
    description: String,
    price: Int,
    var orderId :Int,
    var canceled :Boolean,
) : Product(email = email, id = id, url = url, title = title, description = description, price = price)