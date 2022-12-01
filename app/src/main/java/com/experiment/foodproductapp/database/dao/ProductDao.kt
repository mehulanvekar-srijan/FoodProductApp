package com.experiment.foodproductapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.experiment.foodproductapp.database.entity.Product

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

    //====

    @Query("SELECT * FROM Product WHERE email = :email")
    fun readAllProducts(email: String) : MutableList<Product>

    @Query("DELETE FROM Product WHERE id = :id AND email = :email")
    fun deleteProduct(id: Int,email: String)

    @Query("DELETE FROM Product WHERE email = :email")
    fun deleteAllProductByEmail(email: String)

    @Query("UPDATE Product SET count = :count WHERE id = :id AND email = :email")
    fun setCount(id: Int,email: String,count: Int)

    @Query("SELECT count FROM Product WHERE id = :id AND email = :email")
    fun getCount(id: Int,email: String): Int
}