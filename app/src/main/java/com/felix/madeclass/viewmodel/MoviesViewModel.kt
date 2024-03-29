package com.felix.madeclass.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener
import com.felix.madeclass.model.Movie
import okhttp3.Response
import org.json.JSONObject


class MoviesViewModel : ViewModel() {

    private var listMovie: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    fun setMovie(url: String) {
        val listItem: ArrayList<Movie> = ArrayList()
        try {
            AndroidNetworking.get(url)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsOkHttpResponseAndJSONObject(object : OkHttpResponseAndJSONObjectRequestListener {
                        override fun onResponse(okHttpResponse: Response?, response: JSONObject?) {
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

    fun getMovies(): LiveData<ArrayList<Movie>> {
        return listMovie
    }

}