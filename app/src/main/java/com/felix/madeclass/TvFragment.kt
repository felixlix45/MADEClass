package com.felix.madeclass

import android.content.res.TypedArray
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.felix.madeclass.adapter.TvAdapter
import com.felix.madeclass.model.TvShow

import java.util.ArrayList

class TvFragment : Fragment() {

    private var tvTitle: Array<String>? = null
    private var tvRating: Array<String>? = null
    private var tvReleaseDate: Array<String>? = null
    private var tvOverview: Array<String>? = null
    private var tvPoster: TypedArray? = null
    private var rvTV: RecyclerView? = null
    private var tvList: ArrayList<TvShow>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tv, container, false)
        rvTV = v.findViewById(R.id.rvTV)

        prepare()
        addItem()

        showRecyclerView()

        return v
    }

    private fun showRecyclerView() {
        rvTV!!.layoutManager = LinearLayoutManager(activity)
        val tvAdapter = TvAdapter(requireActivity(), tvList!!)
        rvTV!!.adapter = tvAdapter
    }

    private fun addItem() {
        tvList = ArrayList()

        for (i in tvTitle!!.indices) {
            val tvShow = TvShow()
            tvShow.title = tvTitle!![i]
            tvShow.rating = tvRating!![i]
            tvShow.release_date = tvReleaseDate!![i]
            tvShow.photo = tvPoster!!.getResourceId(i, -1)
            tvShow.overview = tvOverview!![i]
            tvList!!.add(tvShow)
        }

    }

    private fun prepare() {
        tvTitle = resources.getStringArray(R.array.tvTitle)
        tvRating = resources.getStringArray(R.array.tvRating)
        tvReleaseDate = resources.getStringArray(R.array.tvPremiere)
        tvPoster = resources.obtainTypedArray(R.array.tvPoster)
        tvOverview = resources.getStringArray(R.array.tvOverview)
    }
}
