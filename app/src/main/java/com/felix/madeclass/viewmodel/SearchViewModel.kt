package com.felix.madeclass.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.felix.madeclass.model.Movie
import com.felix.madeclass.model.TvShow
import org.json.JSONObject

class SearchViewModel: ViewModel() {
    private var listMovie: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    private var listTV: MutableLiveData<ArrayList<TvShow>> = MutableLiveData()


    fun setMovie(url: String) {
        val listItem: ArrayList<Movie> = ArrayList()
        try {
            AndroidNetworking.get(url)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject?) {
                            if (response != null) {
                                val movieArr = response.getJSONArray("results")
                                for (i in 0 until movieArr.length()) {
                                    val movieObj = movieArr.getJSONObject(i)
                                    val movie = Movie()

                                    movie.movieId = movieObj.get("id").toString()
                                    movie.title = movieObj.get("title").toString()
                                    movie.overview = movieObj.get("overview").toString()
                                    movie.photoHigh = "https://image.tmdb.org/t/p/original/" + movieObj.get("poster_path").toString()
                                    movie.photoLow = "https://image.tmdb.org/t/p/w154/" + movieObj.get("poster_path").toString()
                                    movie.rating = movieObj.get("vote_average").toString()
                                    movie.releaseDate = movieObj.get("release_date").toString()
                                    if (movieObj.isNull("backdrop_path")) {
                                        movie.photoBackdropLow = "https://image.tmdb.org/t/p/w154/"
                                        movie.photoBackdropHigh = "https://image.tmdb.org/t/p/original/"
                                    } else {
                                        movie.photoBackdropLow = "https://image.tmdb.org/t/p/w154/" + movieObj.get("backdrop_path").toString()
                                        movie.photoBackdropHigh = "https://image.tmdb.org/t/p/original/" + movieObj.get("backdrop_path").toString()
                                    }

                                    movie.adult = movieObj.getBoolean("adult")

                                    listItem.add(movie)
                                }
                                listMovie.postValue(listItem)
                            } else {
                                listMovie.postValue(null)
                            }

                        }

                        override fun onError(anError: ANError?) {

                        }
                    })
        } catch (e: Exception) {
            Log.d("Exception", e.message.toString())
        }

    }

    fun setTv(url: String) {
        val listItem: ArrayList<TvShow> = ArrayList()
        try {
            AndroidNetworking.get(url)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject?) {
                            if (response != null) {
                                val movieArr = response.getJSONArray("results")
                                for (i in 0 until movieArr.length()) {
                                    val tvObj = movieArr.getJSONObject(i)
                                    val tv = TvShow()
                                    tv.tvId = tvObj.get("id").toString()
                                    tv.title = tvObj.get("original_name").toString()
                                    tv.overview = tvObj.get("overview").toString()
                                    tv.photoHigh = "https://image.tmdb.org/t/p/original/" + tvObj.get("poster_path").toString()
                                    tv.photoLow = "https://image.tmdb.org/t/p/w154/" + tvObj.get("poster_path").toString()
                                    tv.rating = tvObj.get("vote_average").toString()
                                    if(tvObj.isNull("first_air_date")){
                                        tv.releaseDate = ""
                                    }else{
                                        tv.releaseDate = tvObj.get("first_air_date").toString()
                                    }
                                    tv.photoBackdropLow = "https://image.tmdb.org/t/p/w154/" + tvObj.get("backdrop_path").toString()
                                    tv.photoBackdropHigh = "https://image.tmdb.org/t/p/original/" + tvObj.get("backdrop_path").toString()

                                    listItem.add(tv)
                                }

                                listTV.postValue(listItem)
                            } else {
                                listTV.postValue(null)
                            }
                        }

                        override fun onError(anError: ANError?) {
                        }
                    })
        } catch (e: Exception) {
            Log.d("Exception", e.message.toString())
        }

    }
    
    fun getSearchTV(): LiveData<ArrayList<TvShow>>{
        return listTV
    }
    
    fun getSearchMovie() : LiveData<ArrayList<Movie>>{
        return listMovie
    }
}