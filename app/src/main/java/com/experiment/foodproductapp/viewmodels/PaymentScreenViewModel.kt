package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.MainActivity
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.OrderDetails
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentScreenViewModel : ViewModel() {

    fun navigateOnSuccess(
        navHostController: NavHostController,
        context: Context,
        email: String?,
        sum: Int?,
        points: Int?,
        activity: MainActivity,
    ){

        viewModelScope.launch(Dispatchers.IO){
            if(email != null) {

                //Fetch products from Cart
                val cartList: MutableList<Product> = DatabaseRepository(context).readAllProducts(email)

                //Delete fetched products from cart
                DatabaseRepository(context).deleteAllProductByEmail(email)

                //Fetch latest order Id
                var orderId = DatabaseRepository(context).getLatestOrderId(email)

                //Add the order details in DB
                cartList.forEach{ item ->
                    val order = OrderDetails(
                        email = item.email,
                        id = item.id,
                        url = item.url,
                        title = item.title,
                        count=item.count,
                        description =  item.description,
                        price = item.price,
                        orderId = orderId,
                        canceled = false,
                    )

                    DatabaseRepository(context).insertOrder(order)
                }

                //Update order Id
                DatabaseRepository(context).updateLatestOrderId(email,++orderId)

                //Compute and save reward points
                if(sum != null){
                    //sum is multiplied by 100 in previous screen, Hence divide it by 100
                    val rewardPoints = (sum/100) / 2
                    var currentRewardPoints = DatabaseRepository(context).getRewardPoints(email = email)
                    currentRewardPoints += rewardPoints
                    DatabaseRepository(context).updateRewardPoints(email = email, rewardPoints = currentRewardPoints)
                    Log.d("testPTS", "navigateOnSuccess: sum=$sum rp=$rewardPoints crp=$currentRewardPoints")
                }

                //Update the remaining Redeemed Amount
                Log.d("testredeemAmount", "PaymentScreenViewModel: email=${email} , finalSum=${sum} , points=${points}")

                //Update points
                if(points != null && sum != null) {
                    //sum is multiplied by 100 in previous screen, Hence divide it my 100
                    val value = (sum/100) / 2
                    Log.d("testredeemAmount", "PaymentScreenViewModel: email=${email} , finalSum=${sum} , new points=${points + value}")
                    DatabaseRepository(context).updateRewardPoints(email,points + value)
                }

                delay(2000)

                withContext(Dispatchers.Main){
                    navHostController.navigate(Screen.HomeScreen.routeWithData(email)){
                        popUpTo(Screen.HomeScreen.route) { inclusive = true }
                    }
                    activity.status.value = null
                }

            }

            else {
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Internal Error Occurred",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun navigateOnFailure(
        navHostController: NavHostController,
        context: Context,
        email: String?,
        activity: MainActivity,
    ){
        viewModelScope.launch(Dispatchers.Main){
            if(email != null) {
                delay(2000)
                navHostController.navigate(Screen.ProductCart.routeWithData(email)){
                    popUpTo(Screen.ProductCart.route)  { inclusive = true }
                }
                activity.status.value = null
            }

            else {
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Internal Error Occurred",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}