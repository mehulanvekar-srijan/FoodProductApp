package com.experiment.foodproductapp.repository

import android.content.Context
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

    fun updateUserByEmail(oldEmail:String,firstName:String,lastName:String,email:String,dob:String,password:String,phoneNumber:String){
        dao.updateUserByEmail(oldEmail,firstName,lastName,email,dob,password,phoneNumber)
    }


    fun addProduct(product: Product) = productDao.insertProduct(product)

    fun removeProduct(id: Int) = productDao.deleteProduct(id)

    fun readAllProducts() = productDao.readAllProducts()
}