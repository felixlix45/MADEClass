package com.felix.madeclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.felix.madeclass.adapter.ViewPageAdapter
import com.google.android.material.tabs.TabLayout

class FavoritesFragment : androidx.fragment.app.Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_favorites, container, false)

        val viewPager: ViewPager = v.findViewById(R.id.view_pager)
        val adapter = ViewPageAdapter(childFragmentManager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.count - 1
        val tabLayout: TabLayout = v.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        return v
    }
}