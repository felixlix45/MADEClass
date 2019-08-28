package com.felix.madeclass.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.felix.madeclass.dao.MovieDao
import com.felix.madeclass.database.MovieDatabase
import com.felix.madeclass.model.MovieFavorite

class MovieRepository(application: Application) {

    private var movieDao: MovieDao
    private var allMovie: LiveData<List<MovieFavorite>>

    init {
        val database = MovieDatabase.getInstance(application)
        movieDao = database!!.movieDao()
        allMovie = movieDao.getAllMovie()
    }

    fun insert(movie: MovieFavorite) {
        InsertMovieAsyncTask(movieDao).execute(movie)
    }

    fun update(movie: MovieFavorite) {
        UpdateMovieAsyncTask(movieDao).execute(movie)
    }

    fun delete(movie: MovieFavorite) {
        DeleteMovieAsyncTask(movieDao).execute(movie)
    }

    fun deleteAll() {
        DeleteAllMovieAsyncTask(movieDao).execute()
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

        class UpdateMovieAsyncTask(private val movieDao: MovieDao) : AsyncTask<MovieFavorite, Void, String>() {

            override fun doInBackground(vararg params: MovieFavorite): String? {
                movieDao.update(params[0])
                return null
            }
        }

        class DeleteMovieAsyncTask(private val movieDao: MovieDao) : AsyncTask<MovieFavorite, Void, String>() {

            override fun doInBackground(vararg params: MovieFavorite): String? {
                movieDao.delete(params[0])
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