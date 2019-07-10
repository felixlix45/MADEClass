package com.felix.madeclass

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.res.TypedArray
import android.media.tv.TvView
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
import com.felix.madeclass.viewmodel.TvViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

import java.util.ArrayList

class TvFragment : Fragment() {

    private lateinit var rvTV: RecyclerView

    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private lateinit var tvViewModel: TvViewModel
    private lateinit var tvAdapter: TvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tv, container, false)
        val url = getString(R.string.url_tv, API_KEY)

        tvViewModel = ViewModelProviders.of(requireActivity()).get(TvViewModel::class.java)
        tvViewModel.getTvs().observe(this, Observer<ArrayList<TvShow>>{tvList ->
            if(tvList!=null){
                tvAdapter.setData(tvList)
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
            }
        })

        tvViewModel.setTv(url)
        tvAdapter = TvAdapter(requireActivity())
        tvAdapter.notifyDataSetChanged()

        rvTV = v.findViewById(R.id.rvTV)
        rvTV.layoutManager = LinearLayoutManager(requireActivity())
        rvTV.adapter = tvAdapter

        shimmerFrameLayout = v.findViewById(R.id.shimmer_container)

        return v
    }



    companion object{
        private val TAG = "TvFragment"
        private val API_KEY = "6b7475ec840c3b36442bd75785b232d9"
    }
}
