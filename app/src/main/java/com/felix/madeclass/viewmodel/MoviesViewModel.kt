package com.felix.madeclass.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener

import com.felix.madeclass.model.Movie
import org.json.JSONObject
import java.lang.Exception


class MoviesViewModel : ViewModel(){

    fun setMovie(url: String) {
        var listItem: ArrayList<Movie> = ArrayList()
        try{
            AndroidNetworking.get(url)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject?) {
                            if (response != null){
                                val movieArr = response.getJSONArray("results")
                                for(i in 0 until movieArr.length()){
                                    val movieObj = movieArr.getJSONObject(i)
                                    val movie = Movie()

                                    movie.title = movieObj.get("title").toString()
                                    movie.overview = movieObj.get("overview").toString()
                                    movie.photoHigh = "https://image.tmdb.org/t/p/original/" + movieObj.get("poster_path").toString()
                                    movie.photoLow = "https://image.tmdb.org/t/p/w154/" + movieObj.get("poster_path").toString()
                                    movie.rating = movieObj.get("vote_average").toString()
                                    movie.release_date = movieObj.get("release_date").toString()
                                    movie.photoBackdropLow =  "https://image.tmdb.org/t/p/original/" + movieObj.get("backdrop_path").toString()
                                    movie.photoBackdropHigh =  "https://image.tmdb.org/t/p/original/" + movieObj.get("backdrop_path").toString()

                                    listItem.add(movie)
                                }

                            }
                            listMovie.postValue(listItem)
                        }
                        override fun onError(anError: ANError?) {
                        }
                    })
        }catch (e:Exception){
            Log.d("Exception", e.message.toString())
        }

    }

    fun getMovies(): LiveData<ArrayList<Movie>> {
        return listMovie
    }

    companion object{
        private val API_KEY = "6b7475ec840c3b36442bd75785b232d9"
        private var listMovie: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    }
}