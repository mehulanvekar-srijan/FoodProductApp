package com.experiment.foodproductapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class HomeItems(
    @PrimaryKey var id: Int,
    var url: String,
    var title: String,
    var description: String,
    var price: Int,
    var count: Int = 1,
){
    constructor(): this(-1,"","","",-1,1)
}