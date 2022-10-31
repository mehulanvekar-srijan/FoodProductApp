package com.experiment.foodproductapp.repository

import android.content.Context
import com.experiment.foodproductapp.database.Product
import com.experiment.foodproductapp.database.User
import com.experiment.foodproductapp.database.UserDatabase

class DatabaseRepository(context: Context) {

    private val dao = UserDatabase.getDatabase(context).userDao()
    private val productDao = UserDatabase.getDatabase(context).productDao()


    fun addUser(user: User) = dao.insertUser(user)

    fun readAllUsers() : List<User> = dao.readAllUsers()

    fun getUserByEmail(email: String) : User = dao.getUserByEmail(email)

    fun addProduct(product: Product) = productDao.insertProduct(product)

    fun removeProduct(id: Int) = productDao.deleteProduct(id)

    fun readAllProducts() = productDao.readAllProducts()

    fun setCount(id: Int,count: Int) = productDao.setCount(id,count)
    fun getCount(id: Int) = productDao.getCount(id)
}
