package com.example.airlinespractical.db

import androidx.room.TypeConverter
import com.example.airlinespractical.models.Airline
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromCountryLangList(value: List<Airline>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Airline>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCountryLangList(value: String): List<Airline> {
        val gson = Gson()
        val type = object : TypeToken<List<Airline>>() {}.type
        return gson.fromJson(value, type)
    }

    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}