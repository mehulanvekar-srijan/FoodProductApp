package com.experiment.foodproductapp.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.experiment.foodproductapp.constants.Screen
import com.experiment.foodproductapp.constants.ValidationEvent
import com.experiment.foodproductapp.database.entity.HomeItems
import com.experiment.foodproductapp.database.entity.LikedItems
import com.experiment.foodproductapp.repository.DatabaseRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SplashScreenViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> = _email

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
                _email.value=loggedInEmail
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
                HomeItems(
                    id = 21,
                    url = "https://mishry.com/wp-content/uploads/2019/08/Carlsberg.png",
                    title = "Carlsberg Smooth Lager Beer",
                    description = "The Carlsberg smooth lager has been made with the finest European barley. It has a smooth and rich taste specially crafted for the Indian palate",
                    price = 88,
                    alcohol = 5
                ),
                HomeItems(
                    id = 22,
                    url = "https://mishry.com/wp-content/uploads/2019/08/simba-wit.jpg",
                    title = "Simba Wit",
                    description = "Simba beers have been crafted by beer lovers for beer lovers. The company gives priority to quality and choose the best possible raw ingredients. This refreshing and crisp wheat beer has the subtle flavors of orange peel, lemongrass, and coriander in it",
                    price = 80,
                    alcohol = 3
                ),
                HomeItems(
                    id = 23,
                    url = "https://i.pinimg.com/736x/b6/6f/5b/b66f5b13e0677222938aa2f08b296327.jpg",
                    title = "Clausthaler Grapefruit Non-Alcoholic Beer",
                    description = "Clausthaler, the 40-year-old German brewery known for its non-alcoholic beers, has announced that its non-alcoholic grapefruit beer is available in the U.S. More details from the brewery are below. NORWALK, Connecticut -- Clausthaler, the pioneer in non-alcoholic malt beverages which has been producing award-winning non-alcoholic beer for 40 years, today announced an exciting and refreshing addition to its lineup - Clausthaler Grapefruit. Brewed in Germany and imported into the United",
                    price = 100,
                    alcohol = 0
                ),
                HomeItems(
                    id = 24,
                    url = "https://cdn.shopify.com/s/files/1/0509/0210/6271/products/winclewibblywallaby_1024x1024@2x.png?v=1612111189",
                    title = "Wincle Wibbly Wallaby",
                    description = "Legendary and award winning. Wallabies on the roaches. Unconfirmed reports that Wallaby makes you Wibbly.",
                    price = 90,
                    alcohol = 4
                ),
                HomeItems(
                    id = 25,
                    url = "https://www.liquorland.co.in/public/assets/images/products/kloudbeer.png",
                    title = "Kloud Beer",
                    description = "Kloud is a Korean Lager beer from lotte. It is a water free original gravity recipe made from 100% Malt. Kloud has only 5% alcoholic content by volume",
                    price = 70,
                    alcohol = 6
                ),
                HomeItems(
                    id = 26,
                    url = "https://dydza6t6xitx6.cloudfront.net/ci-pilsner-urquell-68942c4af233c846.jpeg",
                    title = "Pilsner Urquell Beer",
                    description = "Plzen in the Czech Republic was literally born to make beer. When King Wenceslas II of Bohemia founded the city in 1295, he granted its 260 households the right to make beer. For hundreds of years, this worked great. Yet as might be expect with this many home brewers, quality varied over time. It was easy to get beer but not so easy to get good beer",
                    price = 110,
                    alcohol = 4
                ),
                HomeItems(
                    id = 27,
                    url = "https://cdn.shopify.com/s/files/1/0056/9019/6077/products/HellesBelles_1512x.png?v=1637056992",
                    title = "HELLES BELLES PILSNER LAGER",
                    description = "Ultimate in refreshment, this Helles lager was a pet project of the brewery team in 2020 and it proved so popular that we have made it a core line.",
                    price = 100,
                    alcohol = 4
                ),
                HomeItems(
                    id = 28,
                    url = "https://cdn.shopify.com/s/files/1/0056/9019/6077/products/OldHag_1080x.png?v=1637057483",
                    title = "OLD HAG STOUT",
                    description = "A traditional style stout porter. Discretion prevents us from revealing the inspiration for this one!",
                    price = 120,
                    alcohol = 5
                ),
                HomeItems(
                    id = 29,
                    url = "https://cdn.shopify.com/s/files/1/0056/9019/6077/products/BurkesSpecial_1080x.png?v=1637057220",
                    title = "BURKES SPECIAL ESB",
                    description = "A exceptional bitter in more ways than one. One of our absolute favourites, and a winner with lovers of darker beers. Enjoy ideally with a faithful companion.",
                    price = 110,
                    alcohol = 5
                ),
                HomeItems(
                    id = 30,
                    url = "https://cdn.shopify.com/s/files/1/0056/9019/6077/products/HellesBelles.png?v=1637056992",
                    title = "HELLES BELLES PILSNER LAGER",
                    description = "Ultimate in refreshment, this Helles lager was a pet project of the brewery team in 2020 and it proved so popular that we have made it a core line.",
                    price = 90,
                    alcohol = 4
                ),
                HomeItems(
                    id = 31,
                    url = "https://www.seekpng.com/png/detail/878-8780069_craft-beer-beer-bottle.png",
                    title = "Night Swim",
                    description = "Dark and luxurious like a midnight dip in the warm gulf waters off St. Pete Beach. Rich and Roasty with a hint of chocolate. Both this porter, and a night swim, are best enjoyed with a friend",
                    price = 150,
                    alcohol = 2
                ),
                HomeItems(
                    id = 32,
                    url = "https://blackdonkeybeer.com/wp-content/uploads/2021/06/UW-Savage-500B-1-1.png",
                    title = "Underworld Savage",
                    description = "Aromas of candied citrus, blackberries, yeast and malt. Dry with flavours of lemon zest, forest fruits and funky wild yeast spice. Clean lingering bitterness",
                    price = 120,
                    alcohol = 3
                ),
                HomeItems(
                    id = 33,
                    url = "https://images.squarespace-cdn.com/content/v1/553bfa4be4b0786b41dbc163/1649329453619-FPHCE0ZP2W020W6EXJ94/Tartarus+Beers+Pishacha+-+Export+India+Porter+6.8%25.JPG?format=1000w",
                    title = "Tartarus Beers Pishacha",
                    description = "Built on a rich roasty porter base, Simcoe, Cascade & Chinook hops are used in the boil and dry hop",
                    price = 120,
                    alcohol = 7
                ),
                HomeItems(
                    id = 34,
                    url = "https://images.squarespace-cdn.com/content/v1/553bfa4be4b0786b41dbc163/1585678305114-DCTNSSKY68NQHNZG7YWK/Yorkshire+Heart+Brewery+Ghost+Porter.JPG?format=1000w",
                    title = "Yorkshire Heart Brewery Ghost Porter",
                    description = "Ingredients and Allergen advice: Contains water, malted barley, wheat, hops, yeast",
                    price = 100,
                    alcohol = 5
                ),
                HomeItems(
                    id = 35,
                    url = "https://www.beerparadise.co.uk/images/ww/product/181833-mi.jpg",
                    title = "SAMUEL SMITH BREWERY",
                    description = "Brewed with well water (the original well at the Old Brewery, sunk in 1758, is still in use, with the hard well water being drawn from 85 feet underground); best malted barley and a generous amount of choicest aroma hops",
                    price = 100,
                    alcohol = 5
                ),
                HomeItems(
                    id = 36,
                    url = "https://www.beerparadise.co.uk/images/ww/product/187304-mi.jpg",
                    title = "ERDINGER Dunkel Weisse",
                    description = "Carefully selected dark malts with delicate roasting aromas give ERDINGER Dunkel its full-bodied flavor and strong character. This elegant wheat beer has a lustrous, deep dark-brown appearance in the glass",
                    price = 80,
                    alcohol = 6
                ),
                HomeItems(
                    id = 37,
                    url = "https://www.beerparadise.co.uk/images/ww/product/187807-zi.jpg",
                    title = "SCHNEIDER UND SOHN Aventinus",
                    description = "For golden moments by the fireplace: Mein Aventinus - the wholehearted, dark ruby coloured wheat beer, intensive and fiery, warming, well-balanced and tender. Bavaria's oldest wheat Doppelbock - brewed since 1907! Its sturdy body in combination with its sweet malty aroma is an invitation to profound indulgence - an ingenious blend with a strong body. Perfectly matches rustic dishes, dark roasts and sweet desserts.",
                    price = 140,
                    alcohol = 8
                ),
            ).forEach {
                try { databaseRepository.insertItems(it) }
                catch (_: android.database.sqlite.SQLiteConstraintException) { }
            }

        }

//        viewModelScope.launch(Dispatchers.IO){
//
//            try {
//                databaseRepository.insertLikedItem(LikedItems(id = 1, email = "meh@ul.com"))
//                databaseRepository.insertLikedItem(LikedItems(id = 2, email = "meh@ul.com"))
//                databaseRepository.insertLikedItem(LikedItems(id = 10, email = "meh@ul.com"))
//            }
//            catch (_: android.database.sqlite.SQLiteConstraintException) { }
//
//            val list = databaseRepository.readAllLikedItems()
//            Log.d("testLikedItem", "dummyData: $list")
//        }
    }

}