package com.experiment.foodproductapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.experiment.foodproductapp.database.entity.OrderDetails

@Dao
interface OrderDetailsDao {

    @Insert
    fun insertOrder(order: OrderDetails)

    @Query("SELECT * FROM OrderDetails WHERE email = :email AND orderId = :orderId")
    fun readAllOrderDetails(email: String, orderId: Int): MutableList<OrderDetails>

    @Query("DELETE FROM OrderDetails WHERE id = :id AND email = :email")
    fun deleteProduct(id: Int,email: String)
}