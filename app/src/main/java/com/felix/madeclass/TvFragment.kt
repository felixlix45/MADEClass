package com.felix.madeclass

import android.content.res.TypedArray
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.facebook.shimmer.ShimmerFrameLayout

import com.felix.madeclass.adapter.TvAdapter
import com.felix.madeclass.model.TvShow
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

import java.util.ArrayList

class TvFragment : Fragment() {

    private var rvTV: RecyclerView? = null
    private var tvList: ArrayList<TvShow>? = null
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tv, container, false)
        rvTV = v.findViewById(R.id.rvTV)
        shimmerFrameLayout = v.findViewById(R.id.shimmer_container)
        addItem()

        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        AndroidNetworking.forceCancelAll()
    }


    private fun showRecyclerView() {
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(activity)
        rvTV!!.layoutManager = linearLayoutManager
        val tvAdapter = TvAdapter(requireActivity(), tvList!!)
        tvAdapter.notifyDataSetChanged()
        rvTV!!.adapter = tvAdapter
    }

    private fun addItem() {
        tvList = ArrayList()

        shimmerFrameLayout.startShimmer()
        shimmerFrameLayout.visibility = View.VISIBLE

        val url = resources.getString(R.string.url_tv, API_KEY)
        AndroidNetworking.initialize(requireActivity())

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        shimmerFrameLayout.stopShimmer()
                        shimmerFrameLayout.visibility = View.GONE
                        if(response != null){
                            val tvArr = response.getJSONArray("results")
                            for(i in 0 until tvArr.length()){
                                val tvObj = tvArr.getJSONObject(i)
                                val tv = TvShow()

                                tv.title = tvObj.get("name").toString()
                                tv.overview = tvObj.get("overview").toString()
                                tv.photoHigh = "https://image.tmdb.org/t/p/original/" + tvObj.get("poster_path").toString()
                                tv.photoLow = "https://image.tmdb.org/t/p/w154/" + tvObj.get("poster_path").toString()
                                tv.rating = tvObj.get("vote_average").toString()
                                tv.release_date = tvObj.get("first_air_date").toString()

                                tvList!!.add(tv)

                            }
                            showRecyclerView()
                        }else{
                            Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "No Data Available", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(anError: ANError?) {
                        if(anError!!.errorDetail == "connectionError"){
                            Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "Err... Connection Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
    }

    companion object{
        private val TAG = "TvFragment"
        private val API_KEY = "6b7475ec840c3b36442bd75785b232d9"
    }
}
