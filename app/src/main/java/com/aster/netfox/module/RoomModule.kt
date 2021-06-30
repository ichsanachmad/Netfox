package com.aster.netfox.module

import android.app.Application
import androidx.room.Room
import com.aster.netfox.data.local.MovieDao
import com.aster.netfox.data.local.MovieLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun providesMovieDatabase(application: Application): MovieLocalDatabase {
        return Room.databaseBuilder(
            application,
            MovieLocalDatabase::class.java,
            MovieLocalDatabase.DB_NAME
        )
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesMovieDao(movieLocalDatabase: MovieLocalDatabase): MovieDao {
        return movieLocalDatabase.movieDao()
    }
}