package com.felix.madeclass

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.felix.madeclass.adapter.ViewPageAdapter
import com.google.android.material.tabs.TabLayout

class FavoritesFragment : androidx.fragment.app.Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_favorites, container, false)

        val viewPager: ViewPager = v.findViewById(R.id.view_pager)
        val adapter = ViewPageAdapter(childFragmentManager)
        //val imgNoInternet:ImageView = v.findViewById(R.id.ivNoInternet)

        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.count - 1

        val tabLayout: TabLayout = v.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        return v
    }

    private fun isNetworkAvailable(): Boolean{
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}