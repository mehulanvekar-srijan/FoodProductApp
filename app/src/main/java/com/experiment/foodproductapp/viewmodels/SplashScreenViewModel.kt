package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.OrderDetails
import com.experiment.foodproductapp.database.Rewards
import com.experiment.foodproductapp.database.RewardsUsed
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.*
import java.lang.Exception

class SplashScreenViewModel : ViewModel() {

    val splashDuration: Long = 3000  // Milliseconds

    fun execute(context: Context,navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {

            delay(splashDuration)

            dummyData(context)

            val loggedInEmail: String? = DatabaseRepository(context).getLoggedInUser()

            if(loggedInEmail == null){
                withContext(Dispatchers.Main) {
                    navHostController.navigate(Screen.SignInScreen.route){
                        popUpTo(Screen.SplashScreen.route){ inclusive = true }
                    }
                }
            }
            else{
                withContext(Dispatchers.Main) {
                    navHostController.navigate(Screen.HomeScreen.routeWithData(loggedInEmail)){
                        popUpTo(Screen.SplashScreen.route){ inclusive = true }
                    }
                }
            }
        }
    }

    private fun dummyData(context: Context) {
        viewModelScope.launch(Dispatchers.IO){

            listOf(
                Rewards("MX6969","offer","get 10% off"),
                Rewards("DX6969","discount","get 20% off"),
                Rewards("SX6969","discount","get 30% off"),
            ).forEach { DatabaseRepository(context).insertReward(it) }

            listOf(
                RewardsUsed(id = 0, email = "meh@ul.com", code = "MX6969"),
                RewardsUsed(id = 0,email = "meh@ul.com",code = "SX6969"),
                RewardsUsed(id = 0,email  = "romi@romi.com",code = "DX6969"),
            ).forEach { DatabaseRepository(context).insertRewardUsed(it) }


            //====================


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