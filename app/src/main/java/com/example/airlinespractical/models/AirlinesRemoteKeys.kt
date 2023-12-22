package com.example.airlinespractical.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AirlinesRemoteKeys(

    @PrimaryKey(autoGenerate = false)
    val id: String,

    val prevPage: Int?,
    val nextPage: Int?
)