package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.database.entity.HomeItems
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SplashScreenViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    val email = mutableStateOf("")
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        Log.d("testDI", "SplashScreenViewModel: ${databaseRepository.hashCode()}")
    }

    val splashDuration: Long = 3000  // Milliseconds

    fun execute(){

        viewModelScope.launch(Dispatchers.IO) {

            dummyData()

            delay(splashDuration)

            val loggedInEmail = databaseRepository.getLoggedInUser()

            if(loggedInEmail == null){
                validationEventChannel.send(ValidationEvent.Failure)
            }
            else{
                email.value=loggedInEmail
                validationEventChannel.send(ValidationEvent.Success)
            }

//            if (loggedInEmail == null) {
//                withContext(Dispatchers.Main) {
//                    navHostController.navigate(Screen.SignInScreen.route) {
//                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
//                    }
//                }
//            } else {
//                withContext(Dispatchers.Main) {
//                    navHostController.navigate(Screen.HomeScreen.routeWithData(loggedInEmail))
//                    {
//                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
//                    }
//                }
//            }
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
                    alcohol = 0
                ),
                HomeItems(
                    id = 1,
                    url = "https://www.bigbasket.com/media/uploads/p/xxl/40122150_2-coolberg-beer-mint-non-alcoholic.jpg",
//                    url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                    title = "Coolberg Non Alcoholic Beer - Mint",
                    description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
                    price = 79,
                    alcohol = 0
                ),
                HomeItems(
                    id = 2,
                    url = "https://www.bigbasket.com/media/uploads/p/xxl/40213060_2-coolberg-non-alcoholic-beer-cranberry.jpg",
//                    url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                    title = "Coolberg Non Alcoholic Beer - Cranberry",
                    description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
                    price = 79,
                    alcohol = 0
                ),
                HomeItems(
                    id = 3,
                    url = "https://www.bigbasket.com/media/uploads/p/xxl/40213059_2-coolberg-non-alcoholic-beer-strawberry.jpg",
//                    url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                    title = "Coolberg Non Alcoholic Beer - Strawberry",
                    description = "Coolberg Malt Beer is a Non-Alcoholic Beer. This NAB has toasty notes of barley malts and hops and a distinctive musky aroma. It is made from the finest natural barley malts extracts. It is carbonated and has a serious spunk. As it contains less carbonation and often develops a beer-like head when poured into a glass. It is a perfect blend of crisp, bold and smooth flavour. Enjoy it with your choice of snack in the evening or serve it at a party.",
                    price = 79,
                    alcohol = 0
                ),
                HomeItems(
                    id = 4,
                    url = "https://d1j4fphs4leb29.cloudfront.net/product_img/159150/380494-1.jpg",
//                    url = "https://products3.imgix.drizly.com/ci-budweiser-24269668d4e23c97.jpeg",
                    title = "Coolberg Ginger Non Alcoholic Beer",
                    description = "Coolberg Ginger is a sweet pleasure-packed brew that tingles your taste buds with a zing. It has been brewed for an impeccable taste and a flawless aftertaste. It is a favourite among those who choose to take the wilder walk",
                    price = 71,
                    alcohol = 0
                ),
                HomeItems(
                    id = 5,
                    url = "https://cdn.shopify.com/s/files/1/0471/0907/9198/products/san-miguel-0-0-alcohol-free-lager-0-abv--116-1-p_503x503.png?v=1660226013",
                    title = "San Miguel 0.0 Non Alcoholic Beer",
                    description = "A taste of Spain recommends that before consuming, read carefully the ingredients, nutricional information, recommendations for use and conservation as well as all other information. Be warned that there may be transcription errors.",
                    price = 80,
                    alcohol = 1
                ),
                HomeItems(
                    id = 6,
                    url = "https://files.ekmcdn.com/bb537e/images/clausthaler-unfiltered-alcohol-free-beer-0.5-abv--2093-p.png?v=e73587a4-d7d0-442b-b4c2-a03805c8f381",
                    title = "Clausthaler Unfiltered Alcohol Free Beer",
                    description = "Clausthaler presents yet another non-alcoholic beer innovation Clausthaler Unfiltered. The pioneers latest innovation combines the craft beer",
                    price = 108,
                    alcohol = 2
                ),
                HomeItems(
                    id = 7,
                    url = "https://maxcommerce-cdn.freetls.fastly.net/liquorland/886668_20-1-5-12.png?format=pjpg&optimise=medium&auto=webp&width=800&height=800&fit=bounds&canvas=800,800",
                    title = "EXPORT CITRUS",
                    description = "A refreshing lemon flavoured low alcohol beer perfect on a summer's afternoon.",
                    price = 110,
                    alcohol = 2
                ),
                HomeItems(
                    id = 8,
                    url = "https://www.gourmetencasa-tcm.com/12759/clausthaler-lemon-33cl.jpg",
                    title = "Beer No Flavor Lemon – Non-Alcoholic Beer",
                    description = "Root beer which comprises 60% of lemonade. Clausthaler Lemon, perfect beer for athletes TCM has the best price online",
                    price = 90,
                    alcohol = 2
                ),
                HomeItems(
                    id = 9,
                    url = "https://maxcommerce-cdn.freetls.fastly.net/liquorland/886908_20-1-5-12.png?format=pjpg&optimise=medium&auto=webp&width=800&height=800&fit=bounds&canvas=800,800",
                    title = "EXPORT 33",
                    description = "Brewed 33% longer than a standard beer to remove unwanted sugars and reduce the beer's level of carbohydrates - without compromising taste.",
                    price = 90,
                    alcohol = 4
                ),
                HomeItems(
                    id = 10,
                    url = "https://maxcommerce-cdn.freetls.fastly.net/liquorland/897205_20-1-5-12.jpg?format=pjpg&optimise=medium&auto=webp&width=800&height=800&fit=bounds&canvas=800,800",
                    title = "HEINEKEN LIGHT BOTTLES 330ML",
                    description = "This exciting innovation from Heineken is set to cater for Heineken drinkers looking for a lighter alternative.",
                    price = 120,
                    alcohol = 4
                ),
                HomeItems(
                    id = 11,
                    url = "https://maxcommerce-cdn.freetls.fastly.net/liquorland/896395_20-1-5-12.jpg?format=pjpg&optimise=medium&auto=webp&width=800&height=800&fit=bounds&canvas=800,800",
                    title = "PURE BLONDE ULTRA LOW CARB",
                    description = "A refreshing, full-flavoured lager, brewed using the finest ingredients. Pure Blonde is brewed with no preservatives to deliver an easy-drinking, ultra low carb beer",
                    price = 140,
                    alcohol = 5
                ),
                HomeItems(
                    id = 12,
                    url = "https://i.pinimg.com/236x/6e/d5/d4/6ed5d4751254be1d2bad9a8eaf927c8d--heineken-creative-package-design.jpg",
                    title = "Trafiq Club Heineken Limited Edition",
                    description = "These bottles were presented as part of the Heineken Cities campaign at the Budapest Design Week",
                    price = 200,
                    alcohol = 5
                ),
                HomeItems(
                    id = 13,
                    url = "https://maxcommerce-cdn.freetls.fastly.net/liquorland/230284_20-1-5-12.JPG?format=pjpg&optimise=medium&auto=webp&width=800&height=800&fit=bounds&canvas=800,800",
                    title = "Speights Summit Ultra Lager",
                    description = "Speights Summit Ultra Lager is one of the most popular drink",
                    price = 80,
                    alcohol = 1
                ),
                HomeItems(
                    id = 14,
                    url = "https://maxcommerce-cdn.freetls.fastly.net/liquorland/891417_20-1-5-12.jpg?format=pjpg&optimise=medium&auto=webp&width=800&height=800&fit=bounds&canvas=800,800",
                    title = "SPEIGHTS MID ALE",
                    description = "Speights don't usually do things by halves, but they had the good idea of making a full flavoured 2% beer. Brewed using Crystal, Caramalt and Chocolate malts, it enjoys a smooth caramelised flavour and aroma, which is contrasted by the bitterness of Pacific Jade and Green Bullet hops. The finished product is a perfectly balanced, mid strength beer that's still full of flavour.",
                    price = 80,
                    alcohol = 2
                ),
                HomeItems(
                    id = 15,
                    url = "https://www.luluhypermarket.com/cdn-cgi/image/f=auto/medias/107813-001.jpg-1200Wx1200H?context=bWFzdGVyfGltYWdlc3wxNjI0ODh8aW1hZ2UvanBlZ3xpbWFnZXMvaGNkL2g5Yi85MDcxMTcwMTU4NjIyLmpwZ3xhMTQ5ZDdiNmQwMjBhM2IzZjhjNGNhN2FhNDAwNjlmYWVmYTk4NzZkMzJiNjVlNDAzMjgwMGVmYjkwNzZjMGEy",
                    title = "Holsten Classic Non Alcoholic Beer",
                    description = "Pomegranate flavour Non alcoholic malt beverage",
                    price = 100,
                    alcohol = 0
                ),
                HomeItems(
                    id = 16,
                    url = "https://cdn.shopify.com/s/files/1/0072/4074/5019/products/sapporo-Max-Quality_900x.jpg?v=1645673438",
                    title = "Sapporo Premium Beer bottle",
                    description = "Sapporo has embodied brewing excellence since 1876, With a rich gold color and refined bitterness, Sapporo Premium delivers an amazingly crisp, perfectly balanced taste and a smooth finish.",
                    price = 140,
                    alcohol = 5
                ),
                HomeItems(
                    id = 17,
                    url = "https://cdn.shopify.com/s/files/1/0072/4074/5019/products/CoronaBeerbottle-Max-Quality_2_900x.png?v=1653461397",
                    title = "Corona Beer Btl 330ml",
                    description = "Corona Extra is a great summer beer, so enjoy it with friends at your next barbecue, beach day or tailgate.",
                    price = 100,
                    alcohol = 5
                ),
                HomeItems(
                    id = 18,
                    url = "https://manila-wine.com/media/catalog/product/cache/1/image/500x500/9df78eab33525d08d6e5fb8d27136e95/s/a/sapporo-premium-beer.jpg",
                    title = "Sapporo Premium Beer | Japanese Beer",
                    description = "Sapporo is the oldest beer brand in Japan. Bottled at 5% ABV, Sapporo Premium has an amazingly crisp taste, refreshing flavour, and refined bitterness to leave a clean finish.",
                    price = 140,
                    alcohol = 5
                ),
                HomeItems(
                    id = 19,
                    url = "https://www.asiamarket.ie/pub/media/catalog/product/cache/109e8858775ac0e50c11367a96d3851c/5/0/5011046008368_800x800.jpg",
                    title = "Sapporo Premium Beer",
                    description = "Founded in 1876, Sapporo is the oldest brand of beer in Japan. Sapporo Premium Beer is made up of malted barley, water, yeast and hops. One of the major ingredients of Sapporo Premium Beer is rice. It has a crisp, clean and refreshing taste. It contains an alcohol strength of around 5% ABV.",
                    price = 140,
                    alcohol = 5
                ),
                HomeItems(
                    id = 20,
                    url = "https://nlliquor.com/wp-content/uploads/2021/07/1315_m_v5.jpg",
                    title = "Budweiser",
                    description = "The famous Budweiser beer. Labatt knows of no brand produced by any other brewer which costs so much to brew and age. The company’s exclusive beechwood aging produces a taste, a smoothness and a drinkability you will find in no other beer at any price.",
                    price = 90,
                    alcohol = 2
                ),
            ).forEach {
                try { databaseRepository.insertItems(it) }
                catch (_: android.database.sqlite.SQLiteConstraintException) { }
            }

        }
    }

}