package com.experiment.foodproductapp.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface RewardsDao {

    @Insert
    fun insertReward(rewards: Rewards)

}