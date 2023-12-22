package com.example.airlinespractical.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.airlinespractical.db.AirlinesDatabase
import com.example.airlinespractical.models.AirlinesRemoteKeys
import com.example.airlinespractical.models.Data
import com.example.airlinespractical.retrofit.AirlinesApi

@OptIn(ExperimentalPagingApi::class)
class AirlinesRemoteMediator(
    private val airlinesApi: AirlinesApi,
    private val airlineDatabase: AirlinesDatabase
) : RemoteMediator<Int, Data>() {

    val airlinesDao = airlineDatabase.airlinesDao()
    val airlinesRemoteKeysDao = airlineDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Data>): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = airlinesApi.getAirlines(currentPage, 5)
            val endOfPaginationReached = response.pagination.totalPages == currentPage

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            airlineDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    airlinesDao.deleteAirlines()
                    airlinesRemoteKeysDao.deleteAllRemoteKeys()
                }

                airlinesDao.addAirlines(response.data)
                val keys = response.data.map { quote ->
                    AirlinesRemoteKeys(
                        id = quote._id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                airlinesRemoteKeysDao.addAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Data>
    ): AirlinesRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let { id ->
                airlinesRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Data>
    ): AirlinesRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                airlinesRemoteKeysDao.getRemoteKeys(id = quote._id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Data>
    ): AirlinesRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { quote ->
                airlinesRemoteKeysDao.getRemoteKeys(id = quote._id)
            }
    }
}