package com.example.tinkoffproject.ui.main

import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType

enum class NotificationType(val iconResId: Int, val textResId: Int) {
    UNKNOWN_ERROR(R.drawable.ic_error, R.string.error_smth_went_wrong),
    INTERNET_PROBLEM_ERROR(R.drawable.ic_wifi_off, R.string.error_internet_off);
}

fun interface UpdatableNotifications {
    fun showNotification(type: NotificationType)
}