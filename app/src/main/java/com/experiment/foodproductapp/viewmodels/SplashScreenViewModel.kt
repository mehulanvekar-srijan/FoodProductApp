package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.OrderDetails
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                DatabaseRepository(context).insertOrder(
                    OrderDetails(
                        email = "romi@romi.com",
                        id = 0,
                        url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                        title = "Coolberg Non Alcoholic Beer - Malt",
                        description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
                        price = 79,
                        orderId = 1,
                        canceled = false
                    )
                )
                DatabaseRepository(context).insertOrder(
                    OrderDetails(
                        email = "romi@romi.com",
                        id = 1,
                        url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                        title = "Coolberg Non Alcoholic Beer - Mint",
                        description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
                        price = 79,
                        orderId = 1,
                        canceled = false,
                    )
                )
                DatabaseRepository(context).insertOrder(
                    OrderDetails(
                        email = "romi@romi.com",
                        id = 2,
                        url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                        title = "Coolberg Non Alcoholic Beer - Cranberry",
                        description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
                        price = 79,
                        orderId = 2,
                        canceled = false
                    )
                )
            }
            catch (e: android.database.sqlite.SQLiteConstraintException){ }
        }
    }

}