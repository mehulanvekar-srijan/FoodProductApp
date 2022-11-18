package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.constants.Level
import com.experiment.foodproductapp.database.entity.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RewardsPageViewModel : ViewModel() {

    private val _redeemedpoints = mutableStateOf("")
    val redeemedpoints = _redeemedpoints

    val sum = mutableStateOf(0)

    private val _rewardPointsState = mutableStateOf(0)
    val rewardPointsState = _rewardPointsState

    fun getRewardPoints(context: Context, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _rewardPointsState.value = DatabaseRepository(context).getRewardPoints(email)
        }
    }

    fun setRewardPoints(context: Context, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(context).updateRewardPoints(email, rewardPointsState.value)
        }
    }

    fun updateUserPoints(value: String) {
        _redeemedpoints.value = value
    }

    fun updateRewardPoints(context: Context, email: String, rewardPoints: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(context).updateRewardPoints(email, rewardPoints)
        }
    }

    suspend fun isCartEmpty(context: Context, email: String): Boolean {
        val deferred: Deferred<Boolean> = viewModelScope.async(Dispatchers.IO) {
            val list: List<Product> = DatabaseRepository(context).readAllProducts(email)
            while (list.isNotEmpty()) {
                for (item in list) {
                    sum.value = +item.price * item.count
                }
            }
            list.isEmpty()
        }
        return deferred.await()
    }

    private fun setRedeemedAmount(context: Context, email: String, amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentRedeemedAmount = DatabaseRepository(context).getRedeemedAmount(email) * 10
            DatabaseRepository(context).updateRedeemedAmount(
                email,
                (currentRedeemedAmount + amount) / 10
            )
        }
    }

    fun checkLevel(): Level {
        return when (rewardPointsState.value) {
            in 0..500 -> Level.Bronze
            in 501..1000 -> Level.Silver
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
            (500 - rewardPointsState.value).toString()
        } else {
            (1000 - rewardPointsState.value).toString()
        }
    }

    fun calculateProgress(value: Int): Float {
        return when (value) {
            in 0..500 -> {
                (value / 501.0f)
            }
            in 501..1000 -> {
                (value / 1001f)
            }
            else -> {
                1f
            }
        }
    }

    fun validateRewards(context: Context, email: String): Toast {

        if (redeemedpoints.value == "") {

            return Toast.makeText(context, "Enter points", Toast.LENGTH_SHORT)

        } else if (redeemedpoints.value.toInt() > rewardPointsState.value) {

            return Toast.makeText(context, "Not enough points to redeem", Toast.LENGTH_SHORT)
        } else if (redeemedpoints.value.toInt() in 0..9) {

            return Toast.makeText(
                context,
                "Minimum points to be redeemed should be 10",
                Toast.LENGTH_SHORT
            )

        } else if (redeemedpoints.value.toInt() % 10 != 0) {

            return Toast.makeText(
                context,
                "Points should be redeemed in multiples of 10",
                Toast.LENGTH_SHORT
            )

        } else {
            setRedeemedAmount(context, email, redeemedpoints.value.toInt())
            rewardPointsState.value -= redeemedpoints.value.toInt()
            setRewardPoints(context, email)
            redeemedpoints.value = ""
            return Toast.makeText(context, "Points redeemed successfully", Toast.LENGTH_SHORT)
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