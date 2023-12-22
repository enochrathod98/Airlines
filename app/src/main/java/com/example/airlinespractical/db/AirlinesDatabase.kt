package com.example.airlinespractical.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.airlinespractical.models.AirlinesRemoteKeys
import com.example.airlinespractical.models.Data

@Database(entities = [Data::class, AirlinesRemoteKeys::class], version = 1)
@TypeConverters(Converters::class)
abstract class AirlinesDatabase : RoomDatabase() {
    abstract fun airlinesDao(): AirlinesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}