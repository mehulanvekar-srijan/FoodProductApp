package com.experiment.foodproductapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDetailsDao {

    @Insert
    fun insertOrder(order: OrderDetails)

    @Query("SELECT * FROM OrderDetails WHERE email = :email")
    fun readAllOrderDetails(email: String): List<OrderDetails>

    @Query("DELETE FROM OrderDetails WHERE id = :id AND email = :email")
    fun deleteProduct(id: Int,email: String)
}