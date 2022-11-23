package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.entity.HomeItems
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.*

class SplashScreenViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    init {
        Log.d("testDI", "SplashScreenViewModel: ${databaseRepository.hashCode()}")
    }

    val splashDuration: Long = 3000  // Milliseconds

    fun execute(navHostController: NavHostController) {

        viewModelScope.launch(Dispatchers.IO) {

            dummyData()

            delay(splashDuration)

            val loggedInEmail: String? = databaseRepository.getLoggedInUser()

            if (loggedInEmail == null) {
                withContext(Dispatchers.Main) {
                    navHostController.navigate(Screen.SignInScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    navHostController.navigate(Screen.HomeScreen.routeWithData(loggedInEmail))
                    {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            }
        }
    }

    private fun dummyData() {
        viewModelScope.launch(Dispatchers.IO) {

            listOf(
                HomeItems(
                    id = 0,
                    url = "https://www.bigbasket.com/media/uploads/p/xxl/40213061_2-coolberg-non-alcoholic-beer-malt.jpg",
//                    url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                    title = "Coolberg Non Alcoholic Beer - Malt",
                    description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
                    price = 79,
                    alcohol = 5
                ),
                HomeItems(
                    id = 1,
                    url = "https://www.bigbasket.com/media/uploads/p/xxl/40122150_2-coolberg-beer-mint-non-alcoholic.jpg",
//                    url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                    title = "Coolberg Non Alcoholic Beer - Mint",
                    description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
                    price = 79,
                    alcohol = 5
                ),
                HomeItems(
                    id = 2,
                    url = "https://www.bigbasket.com/media/uploads/p/xxl/40213060_2-coolberg-non-alcoholic-beer-cranberry.jpg",
//                    url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                    title = "Coolberg Non Alcoholic Beer - Cranberry",
                    description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
                    price = 79,
                    alcohol = 6
                ),
                HomeItems(
                    id = 3,
                    url = "https://www.bigbasket.com/media/uploads/p/xxl/40213059_2-coolberg-non-alcoholic-beer-strawberry.jpg",
//                    url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                    title = "Coolberg Non Alcoholic Beer - Strawberry",
                    description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
                    price = 79,
                    alcohol = 5
                ),
                HomeItems(
                    id = 4,
                    url = "https://d1j4fphs4leb29.cloudfront.net/product_img/159150/380494-1.jpg",
//                    url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                    title = "Coolberg Ginger Non Alcoholic Beer",
                    description = "Coolberg Ginger is a sweet pleasure-packed brew that tingles your taste buds with a zing. It has been brewed for an impeccable taste and a flawless aftertaste. It is a favourite among those who choose to take the wilder walk",
                    price = 71,
                    alcohol = 5
                ),
            ).forEach {
                try {
                    databaseRepository.insertItems(it)
                } catch (_: android.database.sqlite.SQLiteConstraintException) {
                }
            }

        }
    }

}