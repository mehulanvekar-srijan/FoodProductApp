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

    @Query("UPDATE User SET firstName=:firstName,lastname=:lastName,dob=:dob,email=:email,password=:password,phoneNumber=:phoneNumber WHERE email=:oldEmail ")
    fun updateUserByEmail(oldEmail:String,firstName:String,lastName:String,email:String,dob:String,password:String,phoneNumber:String)

}