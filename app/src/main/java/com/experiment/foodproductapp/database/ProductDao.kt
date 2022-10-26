package com.experiment.foodproductapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: Product)

    @Query("SELECT * FROM Product")
    fun readAllProducts() : List<Product>

    @Query("DELETE FROM Product WHERE id = :id")
    fun deleteProduct(id: Int)

}