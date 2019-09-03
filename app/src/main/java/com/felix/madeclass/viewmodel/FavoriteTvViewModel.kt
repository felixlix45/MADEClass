package com.felix.madeclass.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.felix.madeclass.model.TvShowFavorite
import com.felix.madeclass.repository.TvShowRepository

class FavoriteTvViewModel(application: Application) :AndroidViewModel(application) {

    private var repository = TvShowRepository(application)
    private var allTv: LiveData<List<TvShowFavorite>>

    init {
        allTv = repository.getAllTvShow()
    }

    fun insert(tv: TvShowFavorite){
        repository.insert(tv)
    }

    fun deleteTv(id: String){
        repository.deleteTv(id)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

    fun getAllTvShow(): LiveData<List<TvShowFavorite>>{
        return allTv
    }

}