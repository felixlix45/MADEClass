package com.felix.madeclass.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.felix.madeclass.model.Movie

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie) {

    }

    @Update
    fun update(movie: Movie) {

    }

    @Delete
    fun delete(movie: Movie){

    }
    @Query("DELETE FROM movie_table")
    fun deleteAll(){

    }

    @Query("SELECT * FROM movie_table")
    fun getAllMovie(): LiveData<List<Movie>>

}