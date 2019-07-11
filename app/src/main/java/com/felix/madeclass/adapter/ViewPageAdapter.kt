package com.felix.madeclass.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.felix.madeclass.MoviesFragment
import com.felix.madeclass.TvFragment

class ViewPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        lateinit var selectedFragment: Fragment
        when(position){
            0 -> {
                selectedFragment = MoviesFragment()
            }
            1 ->{
                selectedFragment =  TvFragment()
            }
        }
        return selectedFragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {
                return "Movies"
            }
            1-> {
                return "TV"
            }
        }
        return "Favorites"
    }

    override fun getCount(): Int {
        return 2
    }
}