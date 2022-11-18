package com.experiment.foodproductapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.experiment.foodproductapp.database.entity.FinalPrice

@Dao
interface FinalPriceDao {

    @Insert
    fun insertFinalPrice(finalPrice: FinalPrice)

    @Query("SELECT finalPrice FROM FinalPrice WHERE email=:email AND orderId=:orderId")
    fun getFinalPrice(email: String, orderId: Int): Double

}