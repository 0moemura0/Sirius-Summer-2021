package com.example.tinkoffproject.data.dto.to_view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    val shortName: String,
    val longName: String,
    val symbol: String,
    val isUp: Boolean = true,
    val rate: Double = 0.0
) : Parcelable