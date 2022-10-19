package com.experiment.foodproductapp.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [ Index(value = ["userName"], unique = true) ])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)
