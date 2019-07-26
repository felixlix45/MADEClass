package com.felix.madeclass.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.felix.madeclass.dao.MovieDao
import com.felix.madeclass.database.MovieDatabase
import com.felix.madeclass.model.MovieFavorite
import android.R.id.message
import androidx.room.Update


class MovieRepository(application: Application) {

    var movieDao: MovieDao
    var allMovie:LiveData<List<MovieFavorite>>

    init {
        val database = MovieDatabase.getInstance(application)
        movieDao = database!!.movieDao()
        allMovie = movieDao.getAllMovie()
    }

    fun insert(movie:MovieFavorite){
        InsertMovieAsyncTask(movieDao).execute(movie)
    }

    fun update(movie:MovieFavorite){
        UpdateMovieAsyncTask(movieDao).execute(movie)
    }

    fun delete(movie: MovieFavorite){
        DeleteMovieAsyncTask(movieDao).execute(movie)
    }

    fun deleteAll(){
        DeleteAllMovieAsyncTask(movieDao).execute()
    }

    fun getAllMovies(): LiveData<List<MovieFavorite>> {
        return allMovie
    }

    companion object{
        class InsertMovieAsyncTask(movieDao: MovieDao) : AsyncTask<MovieFavorite, Void, String>() {

            private lateinit var movieDao: MovieDao

            init {
                this.movieDao = movieDao
            }

            override fun doInBackground(vararg params: MovieFavorite): String? {
                movieDao.insert(params[0])
                return null
            }
        }

        class UpdateMovieAsyncTask(movieDao: MovieDao) : AsyncTask<MovieFavorite, Void, String>() {

            private lateinit var movieDao: MovieDao

            fun UpdateMovieAsyncTask(movieDao: MovieDao){
                this.movieDao = movieDao
            }

            override fun doInBackground(vararg params: MovieFavorite): String? {
                movieDao.update(params[0])
                return null
            }
        }

        class DeleteMovieAsyncTask(movieDao: MovieDao) : AsyncTask<MovieFavorite, Void, String>() {

            private lateinit var movieDao: MovieDao

            fun DeleteMovieAsyncTask(movieDao: MovieDao){
                this.movieDao = movieDao
            }

            override fun doInBackground(vararg params: MovieFavorite): String? {
                movieDao.delete(params[0])
                return null
            }
        }

        class DeleteAllMovieAsyncTask(movieDao: MovieDao) : AsyncTask<Void, Void, String>() {

            private lateinit var movieDao: MovieDao

            fun DeleteAllMovieAsyncTask(movieDao: MovieDao){
                this.movieDao = movieDao
            }

            override fun doInBackground(vararg params: Void): String? {
                movieDao.deleteAll()
                return null
            }
        }
    }
}