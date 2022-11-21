package com.experiment.foodproductapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.entity.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductCartViewModel : ViewModel() {

    val checkedState = mutableStateOf(false)

    private var _cartList = mutableStateListOf<Product>()
    val cartList = _cartList

    private val _email = mutableStateOf("")
    val email = _email

    fun onDismiss(context: Context, item: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFromProductList(item)
            removeFromDatabase(context, item)
            updateSum()
            updateFinalSum()
        }
    }

    private fun removeFromProductList(item: Product) = _cartList.remove(item)

    private fun removeFromDatabase(context: Context, item: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepository(context).removeProduct(id = item.id, email = email.value)
        }
    }

    fun fetchCartList(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = DatabaseRepository(context).readAllProducts(_email.value)
            list.forEach { _cartList.add(it) }
            updateSum()
            updateFinalSum()
        }
    }

    //===== count feature =====
    private val _sum = mutableStateOf(computeSum())
    val sum = _sum

    private fun computeSum(): Int = _cartList.fold(0) { result, value ->
        result + (value.price * value.count)
    }

    private fun updateSum() {
        _sum.value = computeSum()
    }

    //Get count from db and set state
    fun getProductCount(context: Context, id: Int, state: MutableState<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = DatabaseRepository(context).getCount(id = id, email = email.value)
        }
    }

    //Get current count from db, increment value, set state
    fun incrementProductCount(context: Context, id: Int, state: MutableState<Int>) {

        viewModelScope.launch(Dispatchers.IO) {

            var currentCount = DatabaseRepository(context).getCount(id = id, email = email.value)

            currentCount += 1

            DatabaseRepository(context).setCount(
                id = id,
                count = currentCount,
                email = email.value
            ) //set count in db
            getProductCount(
                context = context,
                id = id,
                state = state
            )          //set count of UI state
            _cartList.forEach {
                if (it.id == id) it.count = currentCount
            }       //set count of list in RAM
            // or state.value = currentCount

            updateSum()   // update sum vale

            updateFinalSum()   // update final sum value after Redeemed pts
        }

    }

    //Get current count from db, decrement value, set state
    fun decrementProductCount(context: Context, id: Int, state: MutableState<Int>) {

        viewModelScope.launch(Dispatchers.IO) {

            var currentCount = DatabaseRepository(context).getCount(id = id, email = email.value)

            currentCount -= 1

            DatabaseRepository(context).setCount(
                id = id,
                count = currentCount,
                email = email.value
            ) //set count in db
            getProductCount(
                context = context,
                id = id,
                state = state
            )          //set count of UI state
            _cartList.forEach {
                if (it.id == id) it.count = currentCount
            }       //set count of list in RAM
            // or state.value = currentCount

            updateSum()     // update sum vale

            updateFinalSum()   // update final sum value after Redeemed pts
        }

    }

    fun navigateToCheckout(navHostController: NavHostController, context: Context) {

        //Compute necessary details
        if (flag != null && checkedState.value) {
            if (flag == true) _totalPoints.value -= remainingPoints * 10
            else _totalPoints.value = _totalPoints.value - (_redeemAmount.value * 10)
        }
        Log.d(
            "testredeemAmount",
            "navigateToCheckout: email=${email.value} , _totalPoints=${_totalPoints.value}"
        )

        //Clear the cart list
        _cartList.clear()

        navHostController.navigate(
            Screen.CheckoutPage.routeWithData(
                email = email.value,
                sum = finalSum.value,
                points = _totalPoints.value
            )
        )
    }

    fun navigateToRewards(navHostController: NavHostController, email: String?) {

        //Clear the cart list
        _cartList.clear()

        //Then navigate
        if (email != null) navHostController.navigate(Screen.Rewards.routeWithData(email))
    }


    //===== Redeem point =====
    private val _totalPoints = mutableStateOf(0)
    val totalPoints = _totalPoints

    private val _redeemAmount = mutableStateOf(0)
    val redeemAmount = _redeemAmount

    fun initTotalPointsAndRedeemedAmount(context: Context, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _totalPoints.value = DatabaseRepository(context).getRewardPoints(email)
            _redeemAmount.value = _totalPoints.value / 10

            Log.d("testCB", "initPRA: _totalPoints=${_totalPoints.value} _redeemAmount=${_redeemAmount.value}")

        }
    }

    private val _finalSum = mutableStateOf(0)
    val finalSum = _finalSum

    var flag: Boolean? = null
    var remainingPoints = 0

    fun updateFinalSum() {
        if (checkedState.value && sum.value>=100) {
            Log.d(
                "testredeemAmount",
                "updateFinalSum : email=${email.value} , redeemedAmount=${_redeemAmount.value} , _totalPoints=${_totalPoints.value}"
            )

            val maxDiscount = _sum.value / 10   //10% of order value

            if (_redeemAmount.value >= maxDiscount) {

                remainingPoints = maxDiscount

                _finalSum.value = _sum.value - maxDiscount

                flag = true

                Log.d(
                    "testredeemAmount",
                    "updateFinalSum: if : email=${email.value} , redeemedAmount=${_redeemAmount.value} , _totalPoints=${_totalPoints.value} maxDiscount=$maxDiscount"
                )
            } else {
                _finalSum.value = _sum.value - _redeemAmount.value

                flag = false

                Log.d(
                    "testredeemAmount",
                    "updateFinalSum: else : email=${email.value} , redeemedAmount=${_redeemAmount.value} , _totalPoints=${_totalPoints.value}, maxDiscount=$maxDiscount"
                )
            }
        } else {
            finalSum.value = sum.value
            checkedState.value=false
        }
    }
}