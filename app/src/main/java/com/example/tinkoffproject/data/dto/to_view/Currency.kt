package com.example.tinkoffproject.data.dto.to_view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    val shortName: String,
    val longName: String,
    val isUp: Boolean,
    val rate: Double
) : Parcelable