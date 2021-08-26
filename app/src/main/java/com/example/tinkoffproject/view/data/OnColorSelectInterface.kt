package com.example.tinkoffproject.view.data

import com.example.tinkoffproject.model.data.mapper.COLOR

fun interface OnColorSelectInterface {
    fun onItemSelect(color: COLOR)
}