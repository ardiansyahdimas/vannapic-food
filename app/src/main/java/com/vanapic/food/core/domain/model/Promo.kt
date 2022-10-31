package com.vanapic.food.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Promo(
    val id: Int,
    val name: String,
    val photo: String,
    val description: String,
    val stock: Int,
    val price: Int,
    val type: String,
) : Parcelable