package com.example.tinkoffproject.model.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDate(milliSeconds: Long, dateFormat: String): String? {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())

    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}