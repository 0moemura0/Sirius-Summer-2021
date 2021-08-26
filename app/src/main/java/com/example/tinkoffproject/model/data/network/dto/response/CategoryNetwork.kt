package com.example.tinkoffproject.model.data.network.dto.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class CategoryNetwork(
    @PrimaryKey
    val id: Int,
    val isIncome: Boolean,
    val iconId: Int,
    val iconColor: String,
    val name: String
) {
    constructor() : this(0, false, 0, "", "")
}
