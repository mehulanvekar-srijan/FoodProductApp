package com.experiment.foodproductapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.experiment.foodproductapp.database.UserDatabase
import com.experiment.foodproductapp.database.dao.UserDao
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

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build()
        userDao = db.userDao()


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

            val favouriteProductsViewModel = FavouriteProductsViewModel(databaseRepository)
            val u = User(
                firstName = "",
                lastName = "",
                password = "",
                email = "meh@ul.comn",
                phoneNumber = "",
                dob = "",
                loggedIn = true,
            )
            launch(Dispatchers.IO){  userDao.insertUser(u) }
            advanceUntilIdle()
            val email = favouriteProductsViewModel.fetchEmail(userDao)

            assertEquals(expectedEmail,email)

        }

    }

}