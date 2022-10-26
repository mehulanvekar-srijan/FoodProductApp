package com.experiment.foodproductapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class,Product::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun productDao() :ProductDao

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