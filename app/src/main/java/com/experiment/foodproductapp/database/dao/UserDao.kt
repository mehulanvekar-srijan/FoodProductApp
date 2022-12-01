package com.experiment.foodproductapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.experiment.foodproductapp.database.entity.User

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

    @Query("UPDATE User SET imagePath=:uri WHERE email=:email")
    fun updateUserProfilePicture(email:String,uri: String)

    @Query("SELECT imagePath FROM User WHERE email=:email")
    fun getImagePath(email: String): String

    @Query("UPDATE User SET loggedIn=:loggedIn WHERE email=:email")
    fun updateLoginStatus(email:String,loggedIn: Boolean)

    @Query("SELECT email FROM User WHERE loggedIn=1")
    fun getLoggedInUser(): String?

    @Query("UPDATE User SET password=:password WHERE email=:email")
    fun updatePassword(email: String,password: String)

    @Query("SELECT latestOrderId FROM User WHERE email=:email")
    fun getLatestOrderId(email: String): Int

    @Query("UPDATE User SET latestOrderId=:orderId WHERE email=:email")
    fun updateLatestOrderId(email: String, orderId: Int)

    @Query("UPDATE User SET rewardPoints=:rewardPoints WHERE email=:email")
    fun updateRewardPoints(email: String, rewardPoints: Int)

    @Query("SELECT rewardPoints FROM User WHERE email=:email")
    fun getRewardPoints(email: String): Int

    @Query("UPDATE User SET redeemedAmount=:redeemedAmount WHERE email=:email")
    fun updateRedeemedAmount(email: String, redeemedAmount: Int)

    @Query("SELECT redeemedAmount FROM User WHERE email=:email")
    fun getRedeemedAmount(email: String): Int
}