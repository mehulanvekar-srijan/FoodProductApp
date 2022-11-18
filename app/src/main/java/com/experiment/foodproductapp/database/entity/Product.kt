package com.experiment.foodproductapp.database.entity

import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(primaryKeys = ["email", "id"])
open class Product(
    @NotNull var email: String = "",
    @NotNull id: Int,
    url: String,
    title: String,
    description: String,
    price: Int,
    count: Int = 1,
) : HomeItems(
    id = id,
    url = url,
    title = title,
    description = description,
    price = price,
    count = count
) {
    constructor() : this(
        email = "",
        id = -1,
        url = "",
        title = "",
        description = "",
        price = -1,
        count = 1
    )
}
