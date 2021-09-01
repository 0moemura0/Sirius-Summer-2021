package com.example.tinkoffproject.ui.main.carddetails

import android.view.View
import androidx.annotation.ColorRes
import com.example.tinkoffproject.R

enum class LimitStatus(
    val alpha: Float,
    val visibility: Int,
    @ColorRes val color: Int,
    val descriptionVisibility: Int
) {
    HIDE(1f, View.GONE, R.color.white, View.GONE),
    OVER(1f, View.VISIBLE, R.color.red_main, View.VISIBLE),
    NORMAL(0.6f, View.VISIBLE, R.color.white, View.GONE)
}