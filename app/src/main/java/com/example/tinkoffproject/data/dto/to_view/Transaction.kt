package com.example.tinkoffproject.data.dto.to_view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id: Int,
    val value: Int,
    val ts: Long,
    val isIncome: Boolean,
    val category: Category,
    val categoryId: Int,
    val amountFormatted: String,
    val currency: Currency,
    val walletId: Int,
) : Parcelable