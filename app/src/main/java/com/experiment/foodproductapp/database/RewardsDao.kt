package com.experiment.foodproductapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RewardsDao {

    @Insert
    fun insertReward(rewards: Rewards)

    @Query("SELECT * FROM Rewards")
    fun readAllRewards() : List<Rewards>

}