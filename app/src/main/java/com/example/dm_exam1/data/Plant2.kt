package com.example.dm_exam1.data


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Plant2(
    val description: String,
    val image: String,
    val name: String,
    val wateringInterval: String
) : Parcelable