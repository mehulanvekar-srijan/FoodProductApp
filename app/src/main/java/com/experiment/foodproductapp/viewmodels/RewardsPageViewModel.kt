package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.Rewards
import com.experiment.foodproductapp.database.RewardsUsed
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RewardsPageViewModel : ViewModel() {

    var rewardList = listOf<Rewards>()

    val rewards = listOf(
        Rewards("offer","MX6969","get 10% off"),
        Rewards("offerfirst","MX6979","get a drink free on first order")
    )

    fun getRewards(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            rewardList = DatabaseRepository(context).readAllRewards()
        }
    }

    fun getAvailableRewards(context: Context){
        viewModelScope.launch(Dispatchers.IO) {

            val allRewards: List<Rewards> = DatabaseRepository(context).readAllRewards()
            val allRewardsUsed : List<RewardsUsed> = DatabaseRepository(context).readAllRewardsUsed("meh@ul.com")
            val availableRewards : MutableList<Rewards> = mutableListOf()

            allRewards.forEach { rewards ->

                var flag = false

                for(i in allRewardsUsed.indices){

                    if(rewards.code == allRewardsUsed[i].code){
                        flag = true
                        break
                    }

                }

                if(!flag) availableRewards.add(rewards)

            }

            Log.d("testRew", "readAllRewards: $allRewards")
            Log.d("testRew", "readAllRewardsUsed: $allRewardsUsed")
            Log.d("testRew", "availableRewards: $availableRewards")
        }
    }

}