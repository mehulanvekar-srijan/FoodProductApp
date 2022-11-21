package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.MainActivity
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.entity.FinalPrice
import com.experiment.foodproductapp.database.entity.OrderDetails
import com.experiment.foodproductapp.database.entity.Product
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
                val orderId = DatabaseRepository(context).getLatestOrderId(email)

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
                        alcohol = item.alcohol,
                        canceled = false,
                    )

                    DatabaseRepository(context).insertOrder(order)
                }

                //Update order Id
                DatabaseRepository(context).updateLatestOrderId(email,orderId+1)

                //Compute and save reward points in Users table
//                if(sum != null){
//                    //sum is multiplied by 100 in previous screen, Hence divide it by 100
//                    val rewardPoints = (sum/100) / 2
//                    var currentRewardPoints = DatabaseRepository(context).getRewardPoints(email = email)
//                    currentRewardPoints += rewardPoints
//                    DatabaseRepository(context).updateRewardPoints(email = email, rewardPoints = currentRewardPoints)
//                    Log.d("testPTS", "navigateOnSuccess: sum=$sum rp=$rewardPoints crp=$currentRewardPoints")
//                }

                //Update points
                if(points != null && sum != null) {
                    //sum is multiplied by 100 in previous screen, Hence divide it my 100

                    val newRewardPoints = (sum/100) / 2

                    DatabaseRepository(context).updateRewardPoints(email,points + newRewardPoints)

                    //Store Final price in the FinalPrice Table
                    DatabaseRepository(context).insertFinalPrice(FinalPrice(email = email,orderId = orderId, finalPrice = (sum/100.0)))
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
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Internal Error Occurred",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}