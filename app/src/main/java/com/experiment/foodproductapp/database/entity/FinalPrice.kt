package com.experiment.foodproductapp.database.entity

import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(primaryKeys = ["email", "orderId"])
class FinalPrice(
    @NotNull var email: String,
    @NotNull var orderId: Int,
    var finalPrice: Double, // Final Price after discount
)