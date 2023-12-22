package com.example.airlinespractical.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.airlinespractical.models.Data

@Dao
interface AirlinesDao {

    @Query("SELECT * FROM airline")
    fun getAirlines(): PagingSource<Int, Data>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAirlines(quotes: List<Data>)

    @Query("DELETE FROM airline")
    suspend fun deleteAirlines()
}