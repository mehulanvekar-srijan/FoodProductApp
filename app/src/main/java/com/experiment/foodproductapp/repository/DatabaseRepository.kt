package com.experiment.foodproductapp.repository

import android.content.Context
import com.experiment.foodproductapp.database.*

class DatabaseRepository(context: Context) {

    private val userDao = UserDatabase.getDatabase(context).userDao()
    private val productDao = UserDatabase.getDatabase(context).productDao()
    private val orderDetailsDao = UserDatabase.getDatabase(context).orderDetailsDao()
    private val rewardsDao = UserDatabase.getDatabase(context).rewardsDao()
    private val rewardsUsedDao = UserDatabase.getDatabase(context).rewardsUsedDao()

    //User
    fun addUser(user: User) = userDao.insertUser(user)

    fun readAllUsers() : List<User> = userDao.readAllUsers()

    fun getUserByEmail(email: String) : User = userDao.getUserByEmail(email)

    fun updateAddressByEmail(email:String,pincode:String,addressLine1:String,addressLine2:String,city:String,state:String){
        userDao.updateAddressByEmail(email,pincode,addressLine1,addressLine2,city,state)
    }

    fun updateUserByEmail(email:String,firstName:String,lastName:String,dob:String,password:String,phoneNumber:String){
        userDao.updateUserByEmail(email,firstName,lastName,dob,password,phoneNumber)
    }

    fun updateUserProfilePicture(email:String,uri: String) = userDao.updateUserProfilePicture(email,uri)

    fun getImagePath(email:String) : String = userDao.getImagePath(email)

    fun updateLoginStatus(email:String,loggedIn: Boolean){
        userDao.updateLoginStatus(email = email,loggedIn = loggedIn)
    }

    fun getLoggedInUser(): String? = userDao.getLoggedInUser()

    fun updatePassword(email: String,password: String) = userDao.updatePassword(email,password)

    fun getLatestOrderId(email: String) = userDao.getLatestOrderId(email)

    fun updateLatestOrderId(email: String, orderId: Int) = userDao.updateLatestOrderId(email,orderId)

    fun updateRewardPoints(email: String, rewardPoints: Int) = userDao.updateRewardPoints(email,rewardPoints)

    fun getRewardPoints(email: String): Int = userDao.getRewardPoints(email)


    //Product
    fun addProduct(product: Product) = productDao.insertProduct(product)

    fun removeProduct(id: Int,email: String) = productDao.deleteProduct(id,email)

    fun deleteAllProductByEmail(email: String) = productDao.deleteAllProductByEmail(email)

    fun readAllProducts(email: String): MutableList<Product> = productDao.readAllProducts(email)

    fun setCount(id: Int,email: String,count: Int): Unit = productDao.setCount(id,email,count)

    fun getCount(id: Int,email: String): Int = productDao.getCount(id,email)



    //Order details
    fun insertOrder(order: OrderDetails) = orderDetailsDao.insertOrder(order)

    fun readAllOrderDetails(email: String,count: Int): MutableList<OrderDetails> {
        return orderDetailsDao.readAllOrderDetails(email,count)
    }


    //Rewards
    fun insertReward(rewards: Rewards) = rewardsDao.insertReward(rewards)
    fun readAllRewards() : List<Rewards> = rewardsDao.readAllRewards()


    //RewardsUsedDao
    fun insertRewardUsed(rewardsUsed: RewardsUsed) = rewardsUsedDao.insertRewardUsed(rewardsUsed)
    fun readAllRewardsUsed(email: String) : List<RewardsUsed> = rewardsUsedDao.readAllRewardsUsed(email)

    //fun listOfAvailableRewards(email: String) = rewardsUsedDao.listOfAvailableRewards(email)


}
