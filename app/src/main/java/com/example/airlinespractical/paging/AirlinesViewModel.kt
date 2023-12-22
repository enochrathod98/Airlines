package com.example.airlinespractical.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.airlinespractical.repository.AirlinesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class AirlinesViewModel @Inject constructor(private val repository: AirlinesRepository) :
    ViewModel() {
    val list = repository.getAirlines().cachedIn(viewModelScope)
}