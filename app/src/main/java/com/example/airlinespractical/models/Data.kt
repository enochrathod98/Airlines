package com.example.airlinespractical.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.airlinespractical.db.Converters

@Entity(tableName = "Airline")
data class Data(
    val __v: Int,
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val airline: List<Airline>,
    val name: String,
    val trips: Int
)