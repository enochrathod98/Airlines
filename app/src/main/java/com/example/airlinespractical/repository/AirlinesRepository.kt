package com.example.airlinespractical.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.airlinespractical.db.AirlinesDatabase
import com.example.airlinespractical.paging.AirlinesPagingSource
import com.example.airlinespractical.paging.AirlinesRemoteMediator
import com.example.airlinespractical.retrofit.AirlinesApi
import com.example.airlinespractical.retrofit.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalPagingApi::class)
class AirlinesRepository @Inject constructor(
    private val airlinesApi: AirlinesApi,
    private val airlinesDatabase: AirlinesDatabase,
    @ApplicationContext val context: Context
) {

    fun getAirlines() = Pager(
        config = PagingConfig(
            pageSize = 30,
            maxSize = 500
        ),
        remoteMediator = AirlinesRemoteMediator(airlinesApi, airlinesDatabase),
        pagingSourceFactory = {
            airlinesDatabase.airlinesDao().getAirlines()
        }
    ).liveData

}