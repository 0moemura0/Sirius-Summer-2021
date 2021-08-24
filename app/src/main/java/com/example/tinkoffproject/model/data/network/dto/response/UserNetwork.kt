package com.example.tinkoffproject.model.data.network.dto.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.*

@Entity
@Serializable
data class UserNetwork(
    @PrimaryKey
    val id: Int,
    val username: String
)