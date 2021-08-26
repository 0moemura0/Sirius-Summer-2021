package com.example.tinkoffproject.data.dto.to_view

data class Category(
    val id: Int = 0,
    val name: String,
    val resIconId: Int,
    val color: Int,
    val isIncome: Boolean
) {
}