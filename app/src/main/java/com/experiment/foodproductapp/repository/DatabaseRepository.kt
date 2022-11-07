package com.experiment.foodproductapp.repository

import android.content.Context
import android.net.Uri
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.database.User
import com.experiment.foodproductapp.database.UserDatabase

class DatabaseRepository(context: Context) {

    private val dao = UserDatabase.getDatabase(context).userDao()
    private val productDao = UserDatabase.getDatabase(context).productDao()


    fun addUser(user: User) = dao.insertUser(user)

    fun readAllUsers() : List<User> {
        return dao.readAllUsers()
    }

    fun getUserByEmail(email: String) : User {
        return dao.getUserByEmail(email)
    }

    fun updateAddressByEmail(email:String,pincode:String,addressLine1:String,addressLine2:String,city:String,state:String){
        dao.updateAddressByEmail(email,pincode,addressLine1,addressLine2,city,state)
    }

    fun updateUserByEmail(email:String,firstName:String,lastName:String,dob:String,password:String,phoneNumber:String){
        dao.updateUserByEmail(email,firstName,lastName,dob,password,phoneNumber)
    }

    fun updateUserProfilePicture(email:String,uri: String){
        dao.updateUserProfilePicture(email,uri)
    }

    fun getImagePath(email:String) : String{
        return dao.getImagePath(email)
    }

    fun updateLoginStatus(email:String,loggedIn: Boolean){
        dao.updateLoginStatus(email = email,loggedIn = loggedIn)
    }

    fun getLoggedInUser(): String? = dao.getLoggedInUser()


    fun addProduct(product: Product) = productDao.insertProduct(product)

    fun removeProduct(id: Int) = productDao.deleteProduct(id)

    fun readAllProducts() = productDao.readAllProducts()

    fun setCount(id: Int,count: Int): Unit = productDao.setCount(id,count)
    fun getCount(id: Int): Int = productDao.getCount(id)
}
