package com.example.tinkoffproject.model.data.dto

data class Currency(
    val shortName: String,
    val longName: String,
    val isUp: Boolean,
    val rate: Float
)