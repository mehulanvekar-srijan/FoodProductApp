package com.experiment.foodproductapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.experiment.foodproductapp.database.dao.*
import com.experiment.foodproductapp.database.entity.*

@Database(
    entities = [
        User::class,
        Product::class,
        OrderDetails::class,
        HomeItems::class,
        FinalPrice::class,
        LikedItems::class,
        ],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun productDao() : ProductDao
    abstract fun orderDetailsDao() : OrderDetailsDao
    abstract fun homeItemsDao() : HomeItemsDao
    abstract fun finalPriceDao() : FinalPriceDao
    abstract fun likedItemsDao() : LikedItemsDao

    companion object Static {
        private var database : UserDatabase? = null

        fun getDatabase(context: Context) : UserDatabase {
            var tempInstance = database
            return if (tempInstance != null ) tempInstance
            else {
                tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "User"
                ).build()
                database = tempInstance
                return tempInstance
            }
        }

    }
}