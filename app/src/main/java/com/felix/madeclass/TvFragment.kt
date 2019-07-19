package com.felix.madeclass

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
        val url = getString(R.string.url_tv, BuildConfig.API_KEY)

        tvViewModel = ViewModelProviders.of(requireActivity()).get(TvViewModel::class.java)
        tvViewModel.getTvs().observe(this, Observer<ArrayList<TvShow>>{tvList ->
            if(tvList!=null){
                tvAdapter.setData(tvList)
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
            }else{
                Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "No Data Available", Snackbar.LENGTH_SHORT).show()
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

}
