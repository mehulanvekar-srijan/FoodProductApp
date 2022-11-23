package com.experiment.foodproductapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.experiment.foodproductapp.database.entity.HomeItems

@Dao
interface HomeItemsDao {

    @Insert
    fun insertItems(homeItems: HomeItems)

    @Query("SELECT * FROM HomeItems")
    fun readAllItems() : List<HomeItems>


    @Query("SELECT * FROM HomeItems WHERE id = :id")
    fun readOrderId(id: Int) : HomeItems

}