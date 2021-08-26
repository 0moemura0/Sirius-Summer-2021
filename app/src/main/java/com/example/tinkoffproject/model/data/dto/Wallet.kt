package com.example.tinkoffproject.model.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wallet(
    val id: Long,
    val name: String,
    val incomeAmount: Int,
    val expensesAmount: Int,
    val currency: Currency,
    val limit: Int?
) : Parcelable