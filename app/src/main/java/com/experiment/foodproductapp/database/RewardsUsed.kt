package com.experiment.foodproductapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RewardsUsed(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var email: String,
    var code: String,
)