package com.felix.madeclass.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener

import com.felix.madeclass.model.TvShow
import org.json.JSONObject
import java.lang.Exception


class TvViewModel : ViewModel(){

    private var listTv: MutableLiveData<ArrayList<TvShow>> = MutableLiveData()

    fun setTv(url: String) {
        var listItem: ArrayList<TvShow> = ArrayList()
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
                                    val tv = TvShow()

                                    tv.title = movieObj.get("original_name").toString()
                                    tv.overview = movieObj.get("overview").toString()
                                    tv.photoHigh = "https://image.tmdb.org/t/p/original/" + movieObj.get("poster_path").toString()
                                    tv.photoLow = "https://image.tmdb.org/t/p/w154/" + movieObj.get("poster_path").toString()
                                    tv.rating = movieObj.get("vote_average").toString()
                                    tv.releaseDate = movieObj.get("first_air_date").toString()
                                    tv.photoBackdropLow =  "https://image.tmdb.org/t/p/original/" + movieObj.get("backdrop_path").toString()
                                    tv.photoBackdropHigh =  "https://image.tmdb.org/t/p/original/" + movieObj.get("backdrop_path").toString()

                                    listItem.add(tv)
                                }

                                listTv.postValue(listItem)
                            }else{
                                listTv.postValue(null)
                            }
                        }
                        override fun onError(anError: ANError?) {
                        }
                    })
        }catch (e:Exception){
            Log.d("Exception", e.message.toString())
        }

    }

    fun getTvs(): LiveData<ArrayList<TvShow>> {
        return listTv
    }
}