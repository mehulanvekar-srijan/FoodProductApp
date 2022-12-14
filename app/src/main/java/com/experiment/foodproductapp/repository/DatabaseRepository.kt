package com.experiment.foodproductapp.repository

import android.content.Context
import com.experiment.foodproductapp.database.*
import com.experiment.foodproductapp.database.dao.HomeItemsDao
import com.experiment.foodproductapp.database.dao.LikedItemsDao
import com.experiment.foodproductapp.database.dao.UserDao
import com.experiment.foodproductapp.database.entity.*

class DatabaseRepository(context: Context) {

    private val userDao = UserDatabase.getDatabase(context).userDao()
    private val productDao = UserDatabase.getDatabase(context).productDao()
    private val orderDetailsDao = UserDatabase.getDatabase(context).orderDetailsDao()
    private val homeItemsDao = UserDatabase.getDatabase(context).homeItemsDao()
    private val finalPriceDao = UserDatabase.getDatabase(context).finalPriceDao()
    private val likedItemsDao = UserDatabase.getDatabase(context).likedItemsDao()

    //Getter of Dao
    fun getUserDao() = userDao
    fun getProductDao() = productDao
    fun getOrderDetailsDao() = orderDetailsDao
    fun getHomeItemsDao() = homeItemsDao
    fun getFinalPriceDao() = finalPriceDao
    fun getLikedItemsDao() = likedItemsDao

    //User
    fun addUser(user: User) = userDao.insertUser(user)
    fun addUser(userDao: UserDao, user: User) = userDao.insertUser(user)

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
    fun getLoggedInUser(userDao: UserDao): String? = userDao.getLoggedInUser()

    fun updatePassword(email: String,password: String) = userDao.updatePassword(email,password)

    fun getLatestOrderId(email: String) = userDao.getLatestOrderId(email)

    fun updateLatestOrderId(email: String, orderId: Int) = userDao.updateLatestOrderId(email,orderId)

    fun updateRewardPoints(email: String, rewardPoints: Int) = userDao.updateRewardPoints(email,rewardPoints)

    fun getRewardPoints(email: String): Int = userDao.getRewardPoints(email)

    fun updateRedeemedAmount(email: String, redeemedAmount: Int) = userDao.updateRedeemedAmount(email,redeemedAmount)

    fun getRedeemedAmount(email: String): Int = userDao.getRedeemedAmount(email)


    //Product
    fun addProduct(product: Product) = productDao.insertProduct(product)

    fun removeProduct(id: Int,email: String) = productDao.deleteProduct(id,email)

    fun deleteAllProductByEmail(email: String) = productDao.deleteAllProductByEmail(email)

    fun readAllProducts(email: String): MutableList<Product> = productDao.readAllProducts(email)

    fun setCount(id: Int,email: String,count: Int): Unit = productDao.setCount(id,email,count)

    fun getCount(id: Int,email: String): Int = productDao.getCount(id,email)



    //Order details
    fun insertOrder(order: OrderDetails) = orderDetailsDao.insertOrder(order)

    fun readAllOrderDetails(email: String, orderId: Int): MutableList<OrderDetails> {
        return orderDetailsDao.readAllOrderDetails(email, orderId)
    }


    //Home Items
    fun insertItems(homeItems: HomeItems) = homeItemsDao.insertItems(homeItems)
    fun insertItems(homeItemsDao: HomeItemsDao, homeItems: HomeItems) = homeItemsDao.insertItems(homeItems)

    fun readAllItems() : List<HomeItems> = homeItemsDao.readAllItems()

    fun readItemById(homeItemsDao: HomeItemsDao, id: Int) : HomeItems = homeItemsDao.readItemById(id)

    fun readOrderId(id: Int) : HomeItems {
        return homeItemsDao.readOrderId(id)
    }


    //Final Price Table
    fun insertFinalPrice(finalPrice: FinalPrice) = finalPriceDao.insertFinalPrice(finalPrice)
    fun getFinalPrice(email: String, orderId: Int): Double = finalPriceDao.getFinalPrice(email,orderId)


    //Liked Item
    fun insertLikedItem(item: LikedItems) = likedItemsDao.insertLikedItem(item)
    fun insertLikedItem(likedItemsDao: LikedItemsDao, item: LikedItems) = likedItemsDao.insertLikedItem(item)

    fun readAllLikedItems(): List<LikedItems> = likedItemsDao.readAllLikedItems()
    fun readAllLikedItems(likedItemsDao: LikedItemsDao): List<LikedItems> = likedItemsDao.readAllLikedItems()

    //fun readItemsByEmail(email: String): List<LikedItems> = likedItemsDao.readItemsByEmail(email)
    fun readItemsByEmail(likedItemsDao: LikedItemsDao, email: String): List<LikedItems> = likedItemsDao.readItemsByEmail(email)

    fun deleteItem(likedItemsDao: LikedItemsDao, id: Int, email: String) = likedItemsDao.deleteItem(id = id,email = email)

}
