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

    fun addProduct(product: Product) = productDao.insertProduct(product)

    fun readAllProducts() = productDao.readAllProducts()
}