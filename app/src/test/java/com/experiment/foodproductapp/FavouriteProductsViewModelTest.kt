package com.experiment.foodproductapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.experiment.foodproductapp.database.UserDatabase
import com.experiment.foodproductapp.database.dao.HomeItemsDao
import com.experiment.foodproductapp.database.dao.LikedItemsDao
import com.experiment.foodproductapp.database.dao.UserDao
import com.experiment.foodproductapp.database.entity.HomeItems
import com.experiment.foodproductapp.database.entity.LikedItems
import com.experiment.foodproductapp.database.entity.User
import com.experiment.foodproductapp.repository.DatabaseRepository
import com.experiment.foodproductapp.viewmodels.FavouriteProductsViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class FavouriteProductsViewModelTest : KoinTest {

    //lateinit var context: Context
    private val databaseRepository: DatabaseRepository by inject()

    private lateinit var db: UserDatabase

    private lateinit var testUserDao: UserDao
    private lateinit var testLikedItemsDao: LikedItemsDao
    private lateinit var testHomeItemsDao: HomeItemsDao

    @Before
    fun createDb() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build()

        testUserDao = db.userDao()
        testLikedItemsDao = db.likedItemsDao()
        testHomeItemsDao = db.homeItemsDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testItShouldFetchUserEmail() {

        val expectedEmail = "meh@ul.com"

        runTest{

            //Insert data into mock db
            val user = User(
                firstName = "mehul",
                lastName = "anvekar",
                password = "mehul123",
                email = "meh@ul.com",
                phoneNumber = "9999999999",
                dob = "27/12/1999",
                loggedIn = true,
            )

            launch(Dispatchers.IO){  databaseRepository.addUser(testUserDao,user) }

            advanceUntilIdle()

            val favouriteProductsViewModel = FavouriteProductsViewModel(databaseRepository)

            //Check if the fetchEmail() does the expected work by checking its output
            val fetchedEmail = favouriteProductsViewModel.fetchEmail(testUserDao)

            //If its equal, out fetching logic is correct
            assertEquals(expectedEmail,fetchedEmail)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testItShouldFetchAllTheFavouriteProductsOfTheUser(){

        val expectedItem = LikedItems(id = 0, email = "meh@ul.com")

        runTest {

            val favouriteProductsViewModel = FavouriteProductsViewModel(databaseRepository)

            favouriteProductsViewModel.insertFavouriteProduct(testLikedItemsDao, expectedItem)

            advanceUntilIdle()

            val favouriteProductList = favouriteProductsViewModel.fetchFavouriteProducts(testLikedItemsDao)

            assertEquals(expectedItem,favouriteProductList[0])
        }

    }

    // New
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testItShouldFetchAllTheFavouriteProductsOfTheUserByEmail(){

        //Input : email
        //Output: list of liked items

        //1. Created Expected Output
        val expectedList = listOf(
            LikedItems(id = 1, email = "meh@ul.com"),
            LikedItems(id = 3, email = "meh@ul.com"),
        )

        runTest {

            val favouriteProductsViewModel = FavouriteProductsViewModel(databaseRepository)

            //2. Insert data in mock db
            favouriteProductsViewModel.insertFavouriteProduct(testLikedItemsDao,expectedList[0])
            favouriteProductsViewModel.insertFavouriteProduct(testLikedItemsDao,LikedItems(id = 1, email = "romi@romi.com"))
            favouriteProductsViewModel.insertFavouriteProduct(testLikedItemsDao,expectedList[1])
            favouriteProductsViewModel.insertFavouriteProduct(testLikedItemsDao,LikedItems(id = 3, email = "sahil@test.com"))

            advanceUntilIdle()

            //3. Fetching logic
            val returnedList = favouriteProductsViewModel.fetchFavouriteProductsByEmail(
                likedItemsDao = testLikedItemsDao,
                email = "meh@ul.com"
            )

            //4. Check
            assertEquals(expectedList,returnedList)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testItShouldFetchTheHomeItemsById(){

        //======== Expected Output ========
        val expectedItem = HomeItems(
            id = 36,
            url = "https://www.beerparadise.co.uk/images/ww/product/187304-mi.jpg",
            title = "ERDINGER Dunkel Weisse",
            description = "Carefully selected dark malts with delicate roasting aromas give ERDINGER Dunkel its full-bodied flavor and strong character. This elegant wheat beer has a lustrous, deep dark-brown appearance in the glass",
            price = 80,
            alcohol = 6
        )

        //======== Input to mock db ========
        val homeItemInputList = listOf(
            expectedItem,
            HomeItems(
                id = 37,
                url = "https://www.beerparadise.co.uk/images/ww/product/187807-zi.jpg",
                title = "SCHNEIDER UND SOHN Aventinus",
                description = "For golden moments by the fireplace: Mein Aventinus - the wholehearted, dark ruby coloured wheat beer, intensive and fiery, warming, well-balanced and tender. Bavaria's oldest wheat Doppelbock - brewed since 1907! Its sturdy body in combination with its sweet malty aroma is an invitation to profound indulgence - an ingenious blend with a strong body. Perfectly matches rustic dishes, dark roasts and sweet desserts.",
                price = 140,
                alcohol = 8
            ),
        )

        runTest {

            launch(Dispatchers.IO){
                homeItemInputList.forEach{ homeItem ->
                    databaseRepository.insertItems(testHomeItemsDao, homeItem)
                }
            }

            advanceUntilIdle()

            //======== Fetch ========
            val favouriteProductsViewModel = FavouriteProductsViewModel(databaseRepository)

            val returnedItem = favouriteProductsViewModel.fetchHomeItemById(testHomeItemsDao,id = 36) //Function under test

            assertEquals(expectedItem.id,returnedItem.id)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun itShouldRemoveItemFromFavouriteTable(){

        //Delete item with id 2
        val itemToBeRemoved = LikedItems(id = 2, email = "meh@ul.com")

        //======== Input data in mock db ========
        val listOfInput = listOf(
            LikedItems(id = 1, email = "meh@ul.com"),
            itemToBeRemoved,
            LikedItems(id = 3, email = "meh@ul.com"),
        )

        val favouriteProductsViewModel = FavouriteProductsViewModel(databaseRepository)

        runTest {

            listOfInput.forEach{
                favouriteProductsViewModel.insertFavouriteProduct(testLikedItemsDao, it)
            }

            //======== Function under Test ========
            favouriteProductsViewModel.removeFromFavourites(
                likedItemsDao = testLikedItemsDao,
                id = itemToBeRemoved.id,
                email = "meh@ul.com"
            )

            //======== Fetch to check if item is deleted ========
            val list = favouriteProductsViewModel.fetchFavouriteProductsByEmail(
                likedItemsDao = testLikedItemsDao,
                email = "meh@ul.com"
            )

            advanceUntilIdle()

            assert(!list.contains(itemToBeRemoved))
        }

    }
}