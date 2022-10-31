package com.experiment.foodproductapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Product(
    @PrimaryKey var id: Int,
    var url: String,
    var title: String,
    var description: String,
    var price: Int,
    //var alcohol: Int = 5
): Parcelable
