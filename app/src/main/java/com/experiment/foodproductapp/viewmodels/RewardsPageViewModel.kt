package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.constants.Level
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.database.Rewards
import com.experiment.foodproductapp.database.RewardsUsed
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RewardsPageViewModel : ViewModel() {

    //TODO: remove rewards list
    val rewards = listOf(
        Rewards("offer", "MX6969", "get 10% off"),
        Rewards("offerfirst", "MX6979", "get a drink free on first order")
    )

    private val _rewardPointsState = mutableStateOf(0)
    val rewardPointsState = _rewardPointsState

    fun getRewardPoints(context: Context, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _rewardPointsState.value = DatabaseRepository(context).getRewardPoints(email)
        }
    }

    fun updateRewardPoints(context: Context, email: String, rewardPoints: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(context).updateRewardPoints(email, rewardPoints)
        }
    }

    fun isCartEmpty(context: Context, email: String): Boolean {
        val list: List<Product> = DatabaseRepository(context).readAllProducts(email)
        return list.isEmpty()
    }

    fun setRedeemedAmount(context: Context, email: String, amount :Int){
        viewModelScope.launch(Dispatchers.IO) {
            val currentRedeemedAmount = DatabaseRepository(context).getRedeemedAmount(email)
            DatabaseRepository(context).updateRedeemedAmount(email,currentRedeemedAmount+amount)
        }
    }

    fun checkLevel(): Level {
        return when (rewardPointsState.value) {
            in 0..100 -> Level.Bronze
            in 101..500 -> Level.Silver
            else -> Level.Gold
        }
    }

    fun getLevel(level: String): String {
        return if (level == "Bronze") {
            "1"
        } else if (level == "Silver") {
            "2"
        } else {
            "3"
        }
    }

    fun getDifference(level: String): String {
        return if (level == "Bronze") {
            (100-rewardPointsState.value).toString()
        } else {
            (500-rewardPointsState.value).toString()
        }
    }

    fun calculateProgress(value: Int): Float {
        return when (value) {
            in 0..100 -> {
                (value/101.0f)
            }
            in 101..500 -> {
                (value/501f)
            }
            else -> {
                1f
            }
        }
    }

//    var rewardList = listOf<Rewards>()

//    fun getRewards(context: Context){
//        viewModelScope.launch(Dispatchers.IO) {
//            rewardList = DatabaseRepository(context).readAllRewards()
//        }
//    }

//    fun getAvailableRewards(context: Context){
//        viewModelScope.launch(Dispatchers.IO) {
//
//            val allRewards: List<Rewards> = DatabaseRepository(context).readAllRewards()
//            val allRewardsUsed : List<RewardsUsed> = DatabaseRepository(context).readAllRewardsUsed("meh@ul.com")
//            val availableRewards : MutableList<Rewards> = mutableListOf()
//
//            allRewards.forEach { rewards ->
//
//                var flag = false
//
//                for(i in allRewardsUsed.indices){
//
//                    if(rewards.code == allRewardsUsed[i].code){
//                        flag = true
//                        break
//                    }
//
//                }
//
//                if(!flag) availableRewards.add(rewards)
//
//            }
//
//            Log.d("testRew", "readAllRewards: $allRewards")
//            Log.d("testRew", "readAllRewardsUsed: $allRewardsUsed")
//            Log.d("testRew", "availableRewards: $availableRewards")
//        }
//    }

}