package com.felix.madeclass.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.felix.madeclass.model.MovieFavorite

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: MovieFavorite)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Query("DELETE FROM movie_table WHERE movieId = :id")
    fun deleteMovie(id: String)

    @Query("SELECT * FROM movie_table")
    fun getAllMovie(): LiveData<List<MovieFavorite>>

    @Query("SELECT * FROM movie_table WHERE movieId = :id")
    fun getMovie(id: String): Int


}