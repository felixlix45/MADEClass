package com.felix.madeclass.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.felix.madeclass.model.MovieFavorite
import com.felix.madeclass.repository.MovieRepository

class FavoriteMoviesViewModel(application: Application) : AndroidViewModel(application) {
    var repository = MovieRepository(application)
    var allMovies: LiveData<List<MovieFavorite>>

    init{
        allMovies = repository.getAllMovies()
    }

    fun insert(movie:MovieFavorite){
        repository.insert(movie)
    }

    fun update(movie:MovieFavorite){
        repository.update(movie)
    }

    fun delete(movie:MovieFavorite){
        repository.delete(movie)
    }

    fun deleteAll(movie:MovieFavorite){
        repository.deleteAll()
    }

    fun getAllMovie(): LiveData<List<MovieFavorite>>{
        return allMovies
    }




}