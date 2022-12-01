package com.experiment.foodproductapp.database.entity

import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(primaryKeys = ["email", "id", "orderId"])
class OrderDetails(
    @NotNull email: String,
    count: Int,
    @NotNull id: Int,
    url: String,
    title: String,
    description: String,
    price: Int,
    alcohol: Int,
    @NotNull var orderId: Int,
    var canceled: Boolean,
) : Product(
    email = email,
    id = id,
    count = count,
    url = url,
    title = title,
    description = description,
    price = price,
    alcohol = alcohol
)