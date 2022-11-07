package com.experiment.foodproductapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.experiment.foodproductapp.domain.use_case.ValidatePincode

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

    @Query("UPDATE User SET pincode=:pincode,addressLine1=:addressLine1,addressLine2=:addressLine2,city=:city,state=:state WHERE email=:email ")
    fun updateAddressByEmail(email:String,pincode:String,addressLine1:String,addressLine2:String,city:String,state:String)

    @Query("UPDATE User SET imagePath=:uri WHERE email=:email ")
    fun updateUserProfilePicture(email:String,uri: String)

    @Query("SELECT imagePath FROM User WHERE email=:email")
    fun getImagePath(email: String): String

}