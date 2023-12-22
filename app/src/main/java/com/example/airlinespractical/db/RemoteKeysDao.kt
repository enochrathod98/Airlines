package com.example.airlinespractical.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.airlinespractical.models.AirlinesRemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM AirlinesRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id: String): AirlinesRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<AirlinesRemoteKeys>)

    @Query("DELETE FROM AirlinesRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}