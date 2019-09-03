package com.felix.madeclass

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.shimmer.ShimmerFrameLayout

import com.felix.madeclass.adapter.TvAdapter
import com.felix.madeclass.model.TvShow
import com.felix.madeclass.viewmodel.TvViewModel

import java.util.ArrayList

class TvFragment : androidx.fragment.app.Fragment() {

    private lateinit var rvTV: RecyclerView

    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private lateinit var tvViewModel: TvViewModel
    private lateinit var tvAdapter: TvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tv, container, false)

        rvTV = v.findViewById(R.id.rvTV)
        tvAdapter = TvAdapter(requireActivity())

        tvViewModel = ViewModelProviders.of(requireActivity()).get(TvViewModel::class.java)
        tvViewModel.getTvs().observe(this, Observer<ArrayList<TvShow>> { tvList ->
            if (tvList.isNotEmpty()) {
                tvAdapter.setData(tvList)
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
            } else {
                Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "No Data Available", Snackbar.LENGTH_SHORT).show()
            }
        })

        loadData()
        buildRecyclerView()

        shimmerFrameLayout = v.findViewById(R.id.shimmer_container)

        return v
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun loadData() {

        if (!isNetworkAvailable()) {
            Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "Check your internet connection", Snackbar.LENGTH_LONG).show()
        } else {
            tvViewModel.setTv(resources.getString(R.string.url_tv, BuildConfig.API_KEY))
            tvAdapter.notifyDataSetChanged()
        }

    }

    private fun buildRecyclerView() {
        rvTV.layoutManager = LinearLayoutManager(requireActivity())
        rvTV.adapter = tvAdapter
    }

}
