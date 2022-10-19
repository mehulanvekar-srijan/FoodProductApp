package com.experiment.foodproductapp.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [ Index(value = ["email"], unique = true) ])
data class User(
    var firstName: String="",
    var lastName: String="",
    @PrimaryKey var email: String="",
    var password: String="",
    var dob: String="",
    var phoneNumber: String="",
)
