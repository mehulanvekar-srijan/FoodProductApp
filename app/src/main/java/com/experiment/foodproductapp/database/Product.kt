package com.experiment.foodproductapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey var id: Int,
    var url: String,
    var title: String,
    var description: String,
    var price: Int,
)