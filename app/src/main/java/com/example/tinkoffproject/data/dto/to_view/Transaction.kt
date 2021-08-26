package com.example.tinkoffproject.data.dto.to_view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id: Long,
    val date: Long,
    val isIncome: Boolean,
    val category: Category,
    val amount: Int,
    val amountFormatted: String,
) : Parcelable