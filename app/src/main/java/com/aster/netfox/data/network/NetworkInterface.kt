package com.aster.netfox.data.network

import com.aster.netfox.data.model.Base
import com.aster.netfox.data.model.Movie
import retrofit2.http.GET

interface NetworkInterface {
    @GET("list/1")
    suspend fun getMovieList(): Base<List<Movie>>
}