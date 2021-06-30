package com.aster.netfox.domain.usecase

import com.aster.netfox.data.local.LocalRepository
import com.aster.netfox.data.model.Movie
import com.aster.netfox.data.network.NetworkRepository
import com.aster.netfox.data.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieList @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Movie>>> {
        return flow {
            val networkData = networkRepository.getMovies()
            val localData = localRepository.getMovieList()
            networkData.collect {
                when (it) {
                    is Resource.Error -> {
                        if (localData.isNotEmpty())
                            emit(Resource.Success(localData))
                        else
                            emit(Resource.Error(it.message))
                    }
                    is Resource.Loading -> emit(Resource.Loading)
                    is Resource.Success -> {
                        localRepository.insertAllMovie(it.data.items)
                    }
                }
            }
            emit(Resource.Success(localData))
        }
    }
}