package com.experiment.foodproductapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    var email: String? = null,
    @PrimaryKey var id: Int,
    var url: String,
    var title: String,
    var description: String,
    var price: Int,
    var count: Int = 1,
    //var alcohol: Int = 5
)
