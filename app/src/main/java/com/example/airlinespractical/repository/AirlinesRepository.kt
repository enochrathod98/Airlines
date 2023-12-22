package com.example.airlinespractical.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.airlinespractical.db.AirlinesDatabase
import com.example.airlinespractical.paging.AirlinesRemoteMediator
import com.example.airlinespractical.retrofit.AirlinesApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AirlinesRepository @Inject constructor(
    private val airlinesApi: AirlinesApi,
    private val airlinesDatabase: AirlinesDatabase
) {

    fun getAirlines() = Pager(
        config = PagingConfig(
            pageSize = 30,
            maxSize = 500
        ),
        remoteMediator = AirlinesRemoteMediator(airlinesApi, airlinesDatabase),
        pagingSourceFactory = { airlinesDatabase.airlinesDao().getAirlines() }
    ).liveData

}