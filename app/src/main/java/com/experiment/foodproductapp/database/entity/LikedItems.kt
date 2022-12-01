package com.experiment.foodproductapp.database.entity

import androidx.room.Entity

@Entity(primaryKeys = ["email", "id"])
data class LikedItems(
    var email: String,
    var id: Int,
)