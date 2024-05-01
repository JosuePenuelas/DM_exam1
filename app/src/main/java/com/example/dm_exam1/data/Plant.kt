package com.example.dm_exam1.data


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Plant(
    val description: String,
    val growZoneNumber: Int,
    val imageUrl: String,
    val name: String,
    val plantId: String,
    val wateringInterval: Int
) : Parcelable