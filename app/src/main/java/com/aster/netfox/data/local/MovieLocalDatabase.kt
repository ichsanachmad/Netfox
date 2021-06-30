package com.aster.netfox.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aster.netfox.data.model.Movie

@Database(entities = [Movie::class], version = MovieLocalDatabase.VERSION, exportSchema = false)
abstract class MovieLocalDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
        const val DB_NAME = "movie_db"
    }

    abstract fun movieDao(): MovieDao
}