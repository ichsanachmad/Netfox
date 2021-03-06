package com.aster.netfox.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aster.netfox.data.model.Movie
import com.aster.netfox.data.network.NetworkRepository
import com.aster.netfox.data.resource.Resource
import com.aster.netfox.domain.usecase.GetMovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val insertAll: GetMovieList) :
    ViewModel() {
    private val _movieListUiState = MutableStateFlow<Resource<List<Movie>>>(
        Resource.Success(
            listOf()
        )
    )
    val movieListUiState: StateFlow<Resource<List<Movie>>> get() = _movieListUiState

    init {
        viewModelScope.launch {
            insertAll()
                .map {
                    when (it) {
                        is Resource.Loading -> _movieListUiState.emit(Resource.Loading)
                        is Resource.Success -> _movieListUiState.emit(Resource.Success(it.data))
                        is Resource.Error -> _movieListUiState.emit(Resource.Error(it.message))
                    }
                }
                .collect()
        }
    }
}