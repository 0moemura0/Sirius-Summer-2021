package com.example.tinkoffproject.model.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*


fun getDate(milliSeconds: Long, dateFormat: String): String? {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())

    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}

fun isYesterday(date: Long, now: Calendar = Calendar.getInstance()): Boolean {
    val userDate = Calendar.getInstance()
    userDate.timeInMillis = date
    now.add(Calendar.DATE, -1)
    return isOneDay(now, userDate)
}

fun isToday(date: Long): Boolean = DateUtils.isToday(date)


fun isOneDay(date1: Long, date2: Long):Boolean {
    val calendar1 = Calendar.getInstance().apply {
        timeInMillis = date1
    }
    val calendar2 = Calendar.getInstance().apply {
        timeInMillis = date2
    }

    return isOneDay(calendar1, calendar2)
}

fun isOneDay(date1: Calendar, date2: Calendar) =
    date1[Calendar.YEAR] == date2[Calendar.YEAR]
            && date1[Calendar.MONTH] == date2[Calendar.MONTH]
            && date1[Calendar.DATE] == date2[Calendar.DATE]
