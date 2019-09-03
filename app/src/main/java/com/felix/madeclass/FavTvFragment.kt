package com.felix.madeclass

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felix.madeclass.adapter.FavoriteTvAdapter
import com.felix.madeclass.model.TvShowFavorite
import com.felix.madeclass.viewmodel.FavoriteTvViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavTvFragment : Fragment() {

    private lateinit var favoriteTvViewModel: FavoriteTvViewModel
    private lateinit var tvAdapter: FavoriteTvAdapter
    private lateinit var rvFavTv: RecyclerView
    private lateinit var ivNoData: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_fav_tv, container, false)

        ivNoData = v.findViewById(R.id.ivNodataTv)

        rvFavTv = v.findViewById(R.id.rvFavTv)
        rvFavTv.layoutManager = LinearLayoutManager(requireActivity())
        rvFavTv.setHasFixedSize(true)

        loadData()

        val fab: FloatingActionButton = v.findViewById(R.id.fabDeleteAllTv)
        fab.setOnClickListener {

            AlertDialog.Builder(requireActivity())
                    .setTitle("Are you sure?")
                    .setMessage("This will delete all of your favorite tv show")
                    .setPositiveButton("Ok"){dialog, _ ->
                        favoriteTvViewModel.deleteAll()
                        Handler().postDelayed({ loadData() }, 50)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel"){dialog, _->
                        dialog.dismiss()
                    }
                    .show()

        }

        return v
    }

    private fun loadData(){
        tvAdapter = FavoriteTvAdapter(requireActivity())
        rvFavTv.adapter = tvAdapter
        favoriteTvViewModel = ViewModelProviders.of(requireActivity()).get(FavoriteTvViewModel::class.java)
        favoriteTvViewModel.getAllTvShow().observe(requireActivity(), Observer<List<TvShowFavorite>>{favTvShow ->
            if(favTvShow.isNotEmpty()){
                tvAdapter.setData(favTvShow)
                tvAdapter.notifyDataSetChanged()
                ivNoData.visibility = View.GONE
            }else{
                ivNoData.visibility = View.VISIBLE
            }
        })
    }
}