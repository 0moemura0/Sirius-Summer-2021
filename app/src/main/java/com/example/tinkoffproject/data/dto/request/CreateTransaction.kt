package com.example.tinkoffproject.data.dto.request

import android.annotation.SuppressLint
import java.util.*
import kotlinx.serialization.*
import java.text.SimpleDateFormat

@Serializable
data class CreateTransaction(
    val value: Double?,
    val isIncome: Boolean?,
    val ts: String?,
    val currencyShortStr: String?,
    val walletId: Int?,
    val categoryId: Int?
){
    @SuppressLint("SimpleDateFormat")
    fun getDate(): Date? {
        val myFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return if(ts != null) myFormat.parse(ts) else null
    }
}
