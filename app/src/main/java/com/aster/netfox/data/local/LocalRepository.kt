package com.aster.netfox.data.local

import com.aster.netfox.data.model.Movie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(private val movieDao: MovieDao) {
    suspend fun getMovieList(): List<Movie> = movieDao.getMovieList()
    suspend fun insertAllMovie(movies: List<Movie>) = movieDao.insertAll(movies)
}