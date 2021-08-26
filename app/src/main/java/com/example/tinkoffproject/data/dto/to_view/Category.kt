package com.example.tinkoffproject.data.dto.to_view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: Int = 0,
    val name: String,
    val resIconId: Int,
    val color: Int,
    val isIncome: Boolean
) : Parcelable {
}