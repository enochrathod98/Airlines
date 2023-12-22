package com.example.airlinespractical.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.airlinespractical.models.Data
import com.example.airlinespractical.retrofit.AirlinesApi

class AirlinesPagingSource(private val airlinesApi: AirlinesApi) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val position = params.key ?: Companion.STARTING_PAGE_INDEX
            val response = airlinesApi.getAirlines(position, 5)

            return LoadResult.Page(
                data = response.data,
                prevKey = if (position == Companion.STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (position == response.pagination.totalPages) null else position + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

}