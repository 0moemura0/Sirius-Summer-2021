package com.example.tinkoffproject.data.dto.to_view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wallet(
    val id: Long,
    val name: String,
    val incomeAmount: Int,
    val expensesAmount: Int,
    val currency: Currency,
    val limit: Int?,
    val hidden: Boolean
) : Parcelable