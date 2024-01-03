package com.example.airlinespractical.paging

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.airlinespractical.models.Data
import com.example.airlinespractical.repository.AirlinesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class AirlinesViewModel @Inject constructor(repository: AirlinesRepository) :
    ViewModel() {
    private var filterLiveData = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    var list = repository.getAirlines().cachedIn(viewModelScope)

    var filteredPagingDataLiveData: LiveData<PagingData<Data>> =
        filterLiveData.switchMap { filter ->
            list.map { originalPagingData ->
                filterPagingData(originalPagingData, filter)
            }
        }

    fun updateFilter(filter: String) {
        filterLiveData.value = filter
    }

    private fun filterPagingData(
        originalPagingData: PagingData<Data>,
        filter: String
    ): PagingData<Data> {
        return originalPagingData.filter { dataItem ->
            // Apply your filter condition here
            dataItem.name.contains(
                filter,
                ignoreCase = true
            ) || dataItem.airline[0].country.contains(filter, ignoreCase = true)
        }
    }
}