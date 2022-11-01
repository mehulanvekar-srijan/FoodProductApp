package com.experiment.foodproductapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM User")
    fun readAllUsers() : List<User>

    @Query("SELECT * FROM User WHERE email=:email")
    fun getUserByEmail(email: String): User

    @Query("UPDATE User SET firstName=:firstName,lastname=:lastName,dob=:dob,password=:password,phoneNumber=:phoneNumber WHERE email=:email ")
    fun updateUserByEmail(email:String,firstName:String,lastName:String,dob:String,password:String,phoneNumber:String)

}