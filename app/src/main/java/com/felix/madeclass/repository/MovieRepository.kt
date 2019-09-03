package com.felix.madeclass.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.felix.madeclass.dao.MovieDao
import com.felix.madeclass.database.FavoriteDatabase
import com.felix.madeclass.model.MovieFavorite

class MovieRepository(application: Application) {

    private var movieDao: MovieDao
    private var allMovie: LiveData<List<MovieFavorite>>

    init {
        val database = FavoriteDatabase.getInstance(application)
        movieDao = database!!.movieDao()
        allMovie = movieDao.getAllMovie()
    }

    fun insert(movie: MovieFavorite) {
        InsertMovieAsyncTask(movieDao).execute(movie)
    }

    fun deleteAll() {
        DeleteAllMovieAsyncTask(movieDao).execute()
    }

    fun deleteMovie(movieId: String){
        DeleteMovieIdAsyncTask(movieDao).execute(movieId)
    }

    fun getAllMovies(): LiveData<List<MovieFavorite>> {
        return allMovie
    }


    companion object {
        class InsertMovieAsyncTask(private val movieDao: MovieDao) : AsyncTask<MovieFavorite, Void, String>() {

            override fun doInBackground(vararg params: MovieFavorite): String? {
                movieDao.insert(params[0])
                return null
            }
        }

        class DeleteMovieIdAsyncTask(private val movieDao: MovieDao) :AsyncTask<String, Void, String>(){
            override fun doInBackground(vararg params: String): String? {
                movieDao.deleteMovie(params[0])
                return null
            }
        }

        class DeleteAllMovieAsyncTask(private val movieDao: MovieDao) : AsyncTask<Void, Void, String>() {

            override fun doInBackground(vararg params: Void): String? {
                movieDao.deleteAll()
                return null
            }
        }
    }
}