package com.aster.netfox.data.network

import com.aster.netfox.data.model.Base
import com.aster.netfox.data.model.Movie
import com.aster.netfox.data.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val networkInterface: NetworkInterface){
    suspend fun getMovies(): Flow<Resource<Base<List<Movie>>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val result = networkInterface.getMovieList()
                emit(Resource.Success(result))
            }catch (e:Exception){
                emit(Resource.Error("Something went wrong"))
            }
        }
    }
}