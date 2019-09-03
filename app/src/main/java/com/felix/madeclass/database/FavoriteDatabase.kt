package com.felix.madeclass.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.felix.madeclass.dao.MovieDao
import com.felix.madeclass.dao.TvShowDao
import com.felix.madeclass.model.MovieFavorite
import com.felix.madeclass.model.TvShowFavorite

@Database(
        entities = [MovieFavorite::class, TvShowFavorite::class],
        version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvShowDao

    companion object {
        private var INSTANCE: FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteDatabase::class.java, "favorite_database")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }
    }

}