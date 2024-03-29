package com.felix.madeclass.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.felix.madeclass.model.TvShow
import org.json.JSONObject


class TvViewModel : ViewModel() {

    private var listTv: MutableLiveData<ArrayList<TvShow>> = MutableLiveData()

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

                                listTv.postValue(listItem)
                            } else {
                                listTv.postValue(null)
                            }
                        }

                        override fun onError(anError: ANError?) {
                        }
                    })
        } catch (e: Exception) {
            Log.d("Exception", e.message.toString())
        }

    }

    fun getTvs(): LiveData<ArrayList<TvShow>> {
        return listTv
    }
}