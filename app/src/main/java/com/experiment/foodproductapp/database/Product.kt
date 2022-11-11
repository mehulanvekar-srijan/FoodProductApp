package com.experiment.foodproductapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(primaryKeys = ["email","id"])
open class Product(
    @NotNull var email: String = "",
    @NotNull var id: Int,
    var url: String,
    var title: String,
    var description: String,
    var price: Int,
    var count: Int = 1,
) {
    constructor() : this(id = -1, url = "", title = "", description = "", price = -1, count = 1) {}
}
