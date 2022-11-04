package com.experiment.foodproductapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: Product)

    @Query("SELECT * FROM Product")
    fun readAllProducts() : MutableList<Product>

    @Query("DELETE FROM Product WHERE id = :id")
    fun deleteProduct(id: Int)

    @Query("UPDATE Product SET count = :count WHERE id = :id")
    fun setCount(id: Int,count: Int)

    @Query("SELECT count FROM Product WHERE id = :id")
    fun getCount(id: Int): Int

}