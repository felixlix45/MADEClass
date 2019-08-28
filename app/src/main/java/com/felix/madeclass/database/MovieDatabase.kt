package com.felix.madeclass.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.felix.madeclass.dao.MovieDao
import com.felix.madeclass.model.MovieFavorite

@Database(entities = [MovieFavorite::class], version = 2, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, "movie_database")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }

}