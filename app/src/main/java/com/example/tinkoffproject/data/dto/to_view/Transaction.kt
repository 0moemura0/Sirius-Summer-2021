package com.example.tinkoffproject.data.dto.to_view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val id: Int,
    val date: Long,
    val isIncome: Boolean,
    val category: Category,
    val amount: Int,
    val amountFormatted: String,
    val currency: Currency,
    val walletId: Int
    ) : Parcelable