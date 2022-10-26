package com.experiment.foodproductapp.viewmodels

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.views.HomeScreenPage
import com.experiment.foodproductapp.views.SignInPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

    private var userEmail  = mutableStateOf("")

     val productsList =  listOf(
         Product(
             id = 0,
             url = "https://www.bigbasket.com/media/uploads/p/xxl/40213061_2-coolberg-non-alcoholic-beer-malt.jpg",
             title = "Coolberg Non Alcoholic Beer - Malt",
             description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
             price = 79,
         ),
         Product(
             id = 1,
             url = "https://www.bigbasket.com/media/uploads/p/xxl/40122150_2-coolberg-beer-mint-non-alcoholic.jpg",
             title = "Coolberg Non Alcoholic Beer - Mint",
             description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
             price = 79
         ),
         Product(
             id = 2,
             url = "https://www.bigbasket.com/media/uploads/p/xxl/40213060_2-coolberg-non-alcoholic-beer-cranberry.jpg",
             title = "Coolberg Non Alcoholic Beer - Cranberry",
             description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
             price = 79
         ),
         Product(
             id = 3,
             url = "https://www.bigbasket.com/media/uploads/p/xxl/40213059_2-coolberg-non-alcoholic-beer-strawberry.jpg",
             title = "Coolberg Non Alcoholic Beer - Strawberry",
             description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
             price = 79
         ),
    )

    fun setEmail(email: String?) {
        if(email != null) userEmail.value = email
    }

    fun navigateToUserDetails(navHostController: NavHostController){
        navHostController.navigate(Screen.UserDetails.routeWithDate(userEmail.value))
    }
    fun navigateToProductCart(navHostController: NavHostController){
        navHostController.navigate(Screen.ProductCart.route)
    }

    fun addProductToCart(item: Product,context: Context){
        viewModelScope.launch(Dispatchers.IO){
            DatabaseRepository(context).addProduct(item)
        }
    }
}
