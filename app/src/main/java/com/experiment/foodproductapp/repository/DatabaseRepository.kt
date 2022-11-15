package com.experiment.foodproductapp.repository

import android.content.Context
import com.experiment.foodproductapp.database.*

class DatabaseRepository(context: Context) {

    private val dao = UserDatabase.getDatabase(context).userDao()
    private val productDao = UserDatabase.getDatabase(context).productDao()
    private val orderDetailsDao = UserDatabase.getDatabase(context).orderDetailsDao()
    private val rewardsDao = UserDatabase.getDatabase(context).rewardsDao()
    private val rewardsUsedDao = UserDatabase.getDatabase(context).rewardsUsedDao()

    //User
    fun addUser(user: User) = dao.insertUser(user)

    fun readAllUsers() : List<User> = dao.readAllUsers()

    fun getUserByEmail(email: String) : User = dao.getUserByEmail(email)

    fun updateAddressByEmail(email:String,pincode:String,addressLine1:String,addressLine2:String,city:String,state:String){
        dao.updateAddressByEmail(email,pincode,addressLine1,addressLine2,city,state)
    }

    fun updateUserByEmail(email:String,firstName:String,lastName:String,dob:String,password:String,phoneNumber:String){
        dao.updateUserByEmail(email,firstName,lastName,dob,password,phoneNumber)
    }

    fun updateUserProfilePicture(email:String,uri: String) = dao.updateUserProfilePicture(email,uri)

    fun getImagePath(email:String) : String = dao.getImagePath(email)

    fun updateLoginStatus(email:String,loggedIn: Boolean){
        dao.updateLoginStatus(email = email,loggedIn = loggedIn)
    }

    fun getLoggedInUser(): String? = dao.getLoggedInUser()

    fun updatePassword(email: String,password: String) = dao.updatePassword(email,password)

    fun getLatestOrderId(email: String) = dao.getLatestOrderId(email)

    fun updateLatestOrderId(email: String, orderId: Int) = dao.updateLatestOrderId(email,orderId)



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
