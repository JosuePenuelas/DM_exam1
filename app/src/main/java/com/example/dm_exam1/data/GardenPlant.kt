package com.example.dm_exam1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GardenPlant(
    val image: String,
    val name: String,
    val plantingDate: String,
    val wateringInterval: Int
): Parcelable