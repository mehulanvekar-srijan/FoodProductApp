package com.experiment.foodproductapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.experiment.foodproductapp.database.entity.LikedItems

@Dao
interface LikedItemsDao {
    @Insert fun insertLikedItem(item: LikedItems)

    @Query("SELECT * FROM LikedItems")
    fun readAllLikedItems(): List<LikedItems>

    @Query("SELECT * FROM LikedItems WHERE email=:email")
    fun readItemsByEmail(email: String): List<LikedItems>

    @Query("DELETE FROM LikedItems WHERE id = :id AND email=:email")
    fun deleteItem(id: Int, email: String)
}