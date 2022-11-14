package com.experiment.foodproductapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Rewards(
    @PrimaryKey var code: String,
    var title: String,
    var description: String,
)