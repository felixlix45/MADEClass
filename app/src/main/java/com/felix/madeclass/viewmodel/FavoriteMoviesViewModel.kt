package com.felix.madeclass.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.felix.madeclass.model.MovieFavorite
import com.felix.madeclass.repository.MovieRepository

class FavoriteMoviesViewModel(application: Application) : AndroidViewModel(application) {
    private var repository = MovieRepository(application)
    private var allMovies: LiveData<List<MovieFavorite>>

    init {
        allMovies = repository.getAllMovies()
    }

    fun insert(movie: MovieFavorite) {
        repository.insert(movie)
    }


    fun deleteAll() {
        repository.deleteAll()
    }

    fun deleteMovie(movieId: String){
        repository.deleteMovie(movieId)
    }

    fun getAllMovie(): LiveData<List<MovieFavorite>> {
        return allMovies
    }

}