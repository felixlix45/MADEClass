package com.felix.madeclass.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.felix.madeclass.model.MovieFavorite

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: MovieFavorite)

    @Update
    fun update(movie: MovieFavorite)

    @Delete
    fun delete(movie: MovieFavorite)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Query("SELECT * FROM movie_table")
    fun getAllMovie(): LiveData<List<MovieFavorite>>

}