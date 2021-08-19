package com.example.tinkoffproject

import android.content.Context
import java.text.DateFormat
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(context: Context, date: Date?, keyFormatResourceId: Int): String? {
    val textFormat = "{0}"
    return if (date == null) {
        null
    } else MessageFormat.format(
        textFormat,
        createDateFormat(context, keyFormatResourceId).format(date)
    )
}

fun createDateFormat(context: Context, resId: Int): DateFormat {
    return SimpleDateFormat(
        context.getString(resId),
        Locale.getDefault()
    )
}
