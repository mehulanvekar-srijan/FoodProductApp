package com.experiment.foodproductapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.foodproductapp.database.Rewards
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
            rewardList= DatabaseRepository(context).readAllRewards()
        }
    }

}