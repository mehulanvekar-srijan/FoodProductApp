package com.experiment.foodproductapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDetailsDao {

    @Insert
    fun insertProduct(order: OrderDetails)

    @Query("SELECT * FROM OrderDetails WHERE email = :email")
    fun readAllOrderDetails(email: String): List<OrderDetails>

    @Query("DELETE FROM OrderDetails WHERE id = :id AND email = :email")
    fun deleteProduct(id: Int,email: String)

    @Query("UPDATE OrderDetails SET orderId = :orderId WHERE id = :id AND email = :email")
    fun setOrderedId(orderId: Int, id: Int, email: String, count: Int)

    @Query("UPDATE OrderDetails SET canceled = :canceled WHERE id = :id AND email = :email")
    fun setOrderedId(canceled: Boolean, id: Int, email: String, count: Int)
}