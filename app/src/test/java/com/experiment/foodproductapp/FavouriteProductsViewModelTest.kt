package com.experiment.foodproductapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.experiment.foodproductapp.database.UserDatabase
import com.experiment.foodproductapp.database.dao.LikedItemsDao
import com.experiment.foodproductapp.database.dao.UserDao
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
    private val expectedEmail = "meh@ul.com"
    private val databaseRepository: DatabaseRepository by inject()

    private lateinit var db: UserDatabase

    private lateinit var userDao: UserDao
    private lateinit var likedItemsDao: LikedItemsDao

    @Before
    fun createDb() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build()

        userDao = db.userDao()
        likedItemsDao = db.likedItemsDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testItShouldFetchUserEmail() {

        runTest{

            val u = User(
                firstName = "",
                lastName = "",
                password = "",
                email = "meh@ul.com",
                phoneNumber = "",
                dob = "",
                loggedIn = true,
            )

            launch(Dispatchers.IO){  userDao.insertUser(u) }
            advanceUntilIdle()

            val favouriteProductsViewModel = FavouriteProductsViewModel(databaseRepository)
            val fetchedEmail = favouriteProductsViewModel.fetchEmail(userDao)

            assertEquals(expectedEmail,fetchedEmail)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testItShouldFetchAllTheFavouriteProductsOfTheUser(){

        val expectedItem = LikedItems(id = 0, email = "meh@ul.com")

        runTest {

            val favouriteProductsViewModel = FavouriteProductsViewModel(databaseRepository)

            favouriteProductsViewModel.insertFavouriteProduct(
                likedItemsDao,
                expectedItem,
            )

            advanceUntilIdle()

            val favouriteProductList = favouriteProductsViewModel.fetchFavouriteProducts(
                likedItemsDao
            )

            assertEquals(expectedItem,favouriteProductList[0])
        }

    }

}