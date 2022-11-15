package com.experiment.foodproductapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var firstName: String,
    var lastName: String,
    @PrimaryKey var email: String,
    var password: String,
    var dob: String,
    var phoneNumber: String,
    var imagePath: String? = null,
    var loggedIn: Boolean = false,

    //Address
    var pincode: String? = null,
    var addressLine1: String? = null,
    var addressLine2: String? = null,
    var city: String? = null,
    var state: String? = null,

    //Order History
    var latestOrderId: Int = 1,

    //Rewards
    var rewardPoints: Int = 0,
    ){
    constructor() : this("","","","","","")
}
