package com.felix.madeclass.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.felix.madeclass.model.TvShowFavorite

@Dao
interface TvShowDao {

    @Insert
    fun insert(tv: TvShowFavorite) :Long

    @Query("DELETE FROM TV_TABLE")
    fun deleteAll()

    @Query("DELETE FROM TV_TABLE WHERE tvId = :id")
    fun deleteTv(id:String)

    @Query("SELECT * FROM tv_table WHERE tvId = :id")
    fun getTv(id:String):Int

    @Query("SELECT * FROM tv_table")
    fun getAllTv() : LiveData<List<TvShowFavorite>>



}