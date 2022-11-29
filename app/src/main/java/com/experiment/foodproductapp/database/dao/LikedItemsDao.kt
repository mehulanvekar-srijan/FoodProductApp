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
}