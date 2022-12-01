package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.R
import com.experiment.foodproductapp.constants.Level
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RewardsPageViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    init {
        Log.d("testDI", "RewardsPageViewModel: ${databaseRepository.hashCode()}")
    }

    private val _text = mutableStateOf("")
    val text = _text

    private val _rewardPointsState = mutableStateOf(0)
    val rewardPointsState = _rewardPointsState

    fun getRewardPoints(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _rewardPointsState.value = databaseRepository.getRewardPoints(email)
        }
    }

    fun calculateEquals():String {
        return when (_rewardPointsState.value) {
            in 0..500 -> {
                (_rewardPointsState.value/20).toString()
            }
            in 501..1000 -> {
                (_rewardPointsState.value/10).toString()
            }
            else -> {
                (_rewardPointsState.value/5).toString()
            }
        }
    }

    fun checkLevel(): Level {
        return when (_rewardPointsState.value) {
            in 0..500 -> Level.Bronze
            in 501..1000 -> Level.Silver
            else -> Level.Gold
        }
    }

    fun getLevel(level: String): String {
        return when (level) {
            "Bronze" -> {
                "1"
            }
            "Silver" -> {
                "2"
            }
            else -> {
                "3"
            }
        }
    }

    fun getDifference(level: String): String {
        return if (level == "Bronze") {
            (500 - _rewardPointsState.value).toString()
        } else {
            (1000 - _rewardPointsState.value).toString()
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

    fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString(prefix = "http://www.beerbasket.com/", separator = "")
    }

    fun getRedeemStringId(): Int {
        return when (_rewardPointsState.value) {
            in 0..500 -> {
                R.string.redeem_message_20_string
            }
            in 501..1000 -> {
                R.string.redeem_message_10_string
            }
            else -> {
                R.string.redeem_message_5_string
            }
        }
    }

    //navigation moved to view
//    fun navigateToRewardsDetails(navHostController: NavHostController) {
//        navHostController.navigate(Screen.RewardsDetailsPage.routeWithData(_rewardPointsState.value))
//    }
}