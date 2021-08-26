package com.example.tinkoffproject.data.dto.to_view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wallet(
    val id: Int,
    val name: String,
    val incomeAmount: Int,
    val expensesAmount: Int,
    val currency: Currency,
    val limit: Int?,
    val balance: Int?,
    val hidden: Boolean
) : Parcelable