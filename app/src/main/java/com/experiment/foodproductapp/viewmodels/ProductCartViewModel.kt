package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.entity.HomeItems
import com.experiment.foodproductapp.database.entity.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductCartViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    init {
        Log.d("testDI", "ProductCartViewModel: ${databaseRepository.hashCode()}")
    }

    val checkedState = mutableStateOf(false)

    private var _cartList = mutableStateListOf<Product>()
    val cartList = _cartList

    private val _email = mutableStateOf("")
    val email = _email

    private val _sum = mutableStateOf(computeSum())
    val sum = _sum

    private val _availablePoints = mutableStateOf(0)
    val availablePoints = _availablePoints

    private val _redeemAmount = mutableStateOf(0)

    private val _finalSum = mutableStateOf(0)
    val finalSum = _finalSum

    private var redeemedAmount = 0

    private val _newlyDeletedItem: MutableState<Product> = mutableStateOf(Product())
    val newlyDeletedItem: State<Product> = _newlyDeletedItem

    fun setNewlyDeletedItem(item: Product) {
        _newlyDeletedItem.value = item
    }

    fun onDismiss(item: Product) {
        removeFromProductList(item)
        removeFromDatabase(item)
        updateSum()
        updateFinalSum()
    }

    private fun removeFromProductList(item: Product) = _cartList.remove(item)

    private fun removeFromDatabase(item: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.removeProduct(id = item.id, email = _email.value)
        }
    }

    fun fetchCartList() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = databaseRepository.readAllProducts(_email.value)
            list.forEach { _cartList.add(it) }
            updateSum()
            updateFinalSum()
        }
    }

    private fun computeSum(): Int = _cartList.fold(0) { result, value ->
        result + (value.price * value.count)
    }

    private fun updateSum() {
        _sum.value = computeSum()
    }

    //Get count from db and set state
    fun getProductCount(id: Int, state: MutableState<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = databaseRepository.getCount(id = id, email = _email.value)
        }
    }

    //Get current count from db, increment value, set state
    fun incrementProductCount(id: Int, state: MutableState<Int>) {

        viewModelScope.launch(Dispatchers.IO) {

            var currentCount = databaseRepository.getCount(id = id, email = _email.value)

            currentCount += 1

            databaseRepository.setCount(
                id = id,
                count = currentCount,
                email = _email.value
            ) //set count in db
            getProductCount(
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
    fun decrementProductCount(id: Int, state: MutableState<Int>) {

        viewModelScope.launch(Dispatchers.IO) {

            var currentCount = databaseRepository.getCount(id = id, email = _email.value)

            currentCount -= 1

            databaseRepository.setCount(
                id = id,
                count = currentCount,
                email = _email.value
            ) //set count in db
            getProductCount(
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

    fun navigateToCheckout(navHostController: NavHostController) {

        //Compute currently available points only after applying redeemed amount
        if (checkedState.value) updateAvailablePoints()

        //Clear the cart list
        _cartList.clear()

        navHostController.navigate(
            Screen.CheckoutPage.routeWithData(
                email = _email.value,
                sum = _finalSum.value,
                points = _availablePoints.value
            )
        )
    }

    private fun updateAvailablePoints() {
        if (_availablePoints.value in 1..500) {
            _availablePoints.value -= (redeemedAmount * 20) //Multiplied by 20 to convert rupees to points
            Log.d("Redeem", "Updated Points: ${_availablePoints.value}")
        }
        else if (_availablePoints.value in 501..1000) {
            _availablePoints.value -= (redeemedAmount * 10) //Multiplied by 10 to convert rupees to points
            Log.d("Redeem", "Updated Points: ${_availablePoints.value}")
        }
        else {
            _availablePoints.value -= (redeemedAmount * 5) //Multiplied by 5 to convert rupees to points
            Log.d("Redeem", "Updated Points: ${_availablePoints.value}")
        }
    }

    fun navigateToRewards(navHostController: NavHostController, email: String?) {

        //Clear the cart list
        _cartList.clear()

        //Then navigate
        if (email != null) navHostController.navigate(Screen.Rewards.routeWithData(email))
    }

    fun initAvailablePointsAndRedeemedAmount(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _availablePoints.value = databaseRepository.getRewardPoints(email)
            Log.d("Redeem", "TotalRedeempoints: ${_availablePoints.value}")
            if (_availablePoints.value in 1..500) {
                _redeemAmount.value = _availablePoints.value / 20
                Log.d("Redeem", "Max Redeemvalue: ${_redeemAmount.value}")
            }
            else if (_availablePoints.value in 501..1000) {
                _redeemAmount.value = _availablePoints.value / 10
                Log.d("Redeem", "Max Redeemvalue: ${_redeemAmount.value}")
            }
            else {
                _redeemAmount.value = _availablePoints.value / 5
                Log.d("Redeem", "Max Redeemvalue: ${_redeemAmount.value}")
            }
        }
    }

    fun updateFinalSum() {
        //Offer is applicable only if CheckButton is checked & cart sum is >= 100
        if (checkedState.value && _sum.value >= 100) {

            //User can get discount up to only 10% of the cart sum
            val maxDiscount = _sum.value / 10   //10% of order value

            /**
             * NOTE: _redeemAmount is the value in your wallet and
             *      redeemedAmount is the value you remove from wallet
             * */
            if (_redeemAmount.value >= maxDiscount) {

                redeemedAmount = maxDiscount
                Log.d("Redeem", "Redeemeedvalue: $redeemedAmount")

                _finalSum.value = _sum.value - redeemedAmount

            } else {

                redeemedAmount = _redeemAmount.value
                Log.d("Redeem", "Redeemeedvalue: $redeemedAmount")

                _finalSum.value = _sum.value - redeemedAmount

            }
        } else {
            _finalSum.value = _sum.value
            checkedState.value = false
        }
    }

    fun onRestore(product: Product){
        addProductToCart(product)
        addProductToCartList(product)
        updateSum()
        updateFinalSum()
    }
    fun addProductToCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {

            //Inset into Product Table
            try {
                databaseRepository.addProduct(product)
            }
            catch (_: android.database.sqlite.SQLiteConstraintException) { }

        }
    }

    fun addProductToCartList(product: Product) {
        _cartList.add(product)
    }

}