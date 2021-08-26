package com.example.tinkoffproject.model.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int = 0,
    val name: String,
    val resIconId: Int,
    val color: Int,
    val isIncome: Boolean
) : Parcelable {
}