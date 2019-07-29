package com.felix.madeclass.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.felix.madeclass.dao.MovieDao
import com.felix.madeclass.model.MovieFavorite

@Database(entities = [MovieFavorite::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, "movie_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(callback)
                        .build()
            }
            return INSTANCE
        }

        private val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(INSTANCE).execute()
            }
        }

        class PopulateDbAsyncTask(INSTANCE: MovieDatabase?) : AsyncTask<Void, Void, String>() {

            lateinit var movieDao: MovieDao

            fun PopulateDbAsyncTask(db: MovieDatabase) {
                movieDao = db.movieDao()

            }

            override fun doInBackground(vararg params: Void?): String? {
                return null
            }

        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }

}