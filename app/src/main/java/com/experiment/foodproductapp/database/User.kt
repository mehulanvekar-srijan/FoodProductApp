package com.experiment.foodproductapp.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [ Index(value = ["userName"], unique = true) ])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var userName: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var dob: String,
    var phoneNumber: String,
)
