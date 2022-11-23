package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
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

class RewardsPageViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    init {
        Log.d("testDI", "RewardsPageViewModel: ${databaseRepository.hashCode()}")
    }

    private val _redeemedPoints = mutableStateOf("")
    val redeemedPoints = _redeemedPoints

    val sum = mutableStateOf(0)

    private val _rewardPointsState = mutableStateOf(0)
    val rewardPointsState = _rewardPointsState

    fun getRewardPoints(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _rewardPointsState.value = databaseRepository.getRewardPoints(email)
        }
    }

    private fun setRewardPoints(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.updateRewardPoints(email, rewardPointsState.value)
        }
    }

    private fun setRedeemedAmount(email: String, amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentRedeemedAmount = databaseRepository.getRedeemedAmount(email) * 10
            databaseRepository.updateRedeemedAmount(
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

        if (redeemedPoints.value == "") {

            return Toast.makeText(context, "Enter points", Toast.LENGTH_SHORT)

        } else if (redeemedPoints.value.toInt() > rewardPointsState.value) {

            return Toast.makeText(context, "Not enough points to redeem", Toast.LENGTH_SHORT)
        } else if (redeemedPoints.value.toInt() in 0..9) {

            return Toast.makeText(
                context,
                "Minimum points to be redeemed should be 10",
                Toast.LENGTH_SHORT
            )

        } else if (redeemedPoints.value.toInt() % 10 != 0) {

            return Toast.makeText(
                context,
                "Points should be redeemed in multiples of 10",
                Toast.LENGTH_SHORT
            )

        } else {
            setRedeemedAmount(email, redeemedPoints.value.toInt())
            rewardPointsState.value -= redeemedPoints.value.toInt()
            setRewardPoints(email)
            redeemedPoints.value = ""
            return Toast.makeText(context, "Points redeemed successfully", Toast.LENGTH_SHORT)
        }
    }

    fun updateUserPoints(value: String) {
        _redeemedPoints.value = value
    }

    fun updateRewardPoints(email: String, rewardPoints: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.updateRewardPoints(email, rewardPoints)
        }
    }

    suspend fun isCartEmpty(email: String): Boolean {
        val deferred: Deferred<Boolean> = viewModelScope.async(Dispatchers.IO) {
            val list: List<Product> = databaseRepository.readAllProducts(email)
            while (list.isNotEmpty()) {
                for (item in list) {
                    sum.value = +item.price * item.count
                }
            }
            list.isEmpty()
        }
        return deferred.await()
    }

//    var rewardList = listOf<Rewards>()

//    fun getRewards(context: Context){
//        viewModelScope.launch(Dispatchers.IO) {
//            rewardList = databaseRepository.readAllRewards()
//        }
//    }

//    fun getAvailableRewards(context: Context){
//        viewModelScope.launch(Dispatchers.IO) {
//
//            val allRewards: List<Rewards> = databaseRepository.readAllRewards()
//            val allRewardsUsed : List<RewardsUsed> = databaseRepository.readAllRewardsUsed("meh@ul.com")
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