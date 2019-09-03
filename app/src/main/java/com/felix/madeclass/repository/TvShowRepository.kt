package com.felix.madeclass.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.felix.madeclass.dao.TvShowDao
import com.felix.madeclass.database.FavoriteDatabase
import com.felix.madeclass.model.TvShowFavorite

class TvShowRepository(application: Application) {
    private lateinit var tvShowDao: TvShowDao
    private lateinit var allTvShow: LiveData<List<TvShowFavorite>>

    init {
        val database = FavoriteDatabase.getInstance(application)
        if(database != null){
            tvShowDao = database.tvDao()
            allTvShow = tvShowDao.getAllTv()
        }
    }

    fun insert(tvShow : TvShowFavorite){
        InsertTvAsyncTask(tvShowDao).execute(tvShow)
    }

    fun deleteAll(){
        DeleteAllTvAyncTask(tvShowDao).execute()
    }

    fun deleteTv(tvId: String){
        DeleteTvIdAsyncTask(tvShowDao).execute(tvId)
    }

    fun getAllTvShow():LiveData<List<TvShowFavorite>>{
        return allTvShow
    }

    companion object{
        class InsertTvAsyncTask(private val tvDao: TvShowDao) :AsyncTask<TvShowFavorite, Void, String>(){
            override fun doInBackground(vararg params: TvShowFavorite): String? {
                tvDao.insert(params[0])
                return null
            }
        }

        class DeleteTvIdAsyncTask(private val tvDao: TvShowDao) : AsyncTask<String, Void, String>(){
            override fun doInBackground(vararg params: String): String? {
                tvDao.deleteTv(params[0])
                return null
            }
        }

        class DeleteAllTvAyncTask(private val tvDao: TvShowDao) :AsyncTask<TvShowFavorite, Void, String>(){
            override fun doInBackground(vararg params: TvShowFavorite): String? {
                tvDao.deleteAll()
                return null
            }
        }
    }
}