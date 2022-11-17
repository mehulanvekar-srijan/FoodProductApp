package com.experiment.foodproductapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RewardsUsedDao {
    @Insert fun insertRewardUsed(rewardsUsed: RewardsUsed)

    @Query("SELECT * FROM RewardsUsed WHERE email=:email")
    fun readAllRewardsUsed(email: String) : List<RewardsUsed>

//    @Query("SELECT * FROM Rewards WHERE code NOT IN (SELECT * FROM RewardsUsed WHERE email=:email)")
//    fun listOfAvailableRewards(email: String) : List<Rewards>
}