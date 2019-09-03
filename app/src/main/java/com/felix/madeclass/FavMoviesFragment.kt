package com.felix.madeclass

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felix.madeclass.adapter.FavoriteMovieAdapter
import com.felix.madeclass.model.MovieFavorite
import com.felix.madeclass.viewmodel.FavoriteMoviesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavMoviesFragment : Fragment() {

    private lateinit var favoriteMoviesViewModel: FavoriteMoviesViewModel
    private lateinit var movieAdapter: FavoriteMovieAdapter
    private lateinit var rvFavMovie: RecyclerView
    private lateinit var ivNodata: ImageView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_fav_movies, container, false)

        ivNodata = v.findViewById(R.id.ivNoDataMovie)

        rvFavMovie = v.findViewById(R.id.rvFavMovie)
        rvFavMovie.layoutManager = LinearLayoutManager(requireActivity())
        rvFavMovie.setHasFixedSize(true)

       loadData()

        val fab: FloatingActionButton = v.findViewById(R.id.fabDeleteAllMovie)
        fab.setOnClickListener {
           AlertDialog.Builder(requireActivity())
                    .setTitle("Are you sure?")
                    .setMessage("This will delete all of your favorite movies")
                    .setPositiveButton("Ok") {dialog, _ ->
                        favoriteMoviesViewModel.deleteAll()
                        Handler().postDelayed({ loadData() }, 50)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel"){dialog, _ ->
                        dialog.dismiss()
                    }
                   .show()


        }

        return v
    }

    private fun loadData(){
        movieAdapter = FavoriteMovieAdapter(requireActivity())
        rvFavMovie.adapter = movieAdapter
        favoriteMoviesViewModel = ViewModelProviders.of(requireActivity()).get(FavoriteMoviesViewModel::class.java)
        favoriteMoviesViewModel.getAllMovie().observe(requireActivity(), Observer<List<MovieFavorite>> { favMovies ->
            if (favMovies.isNotEmpty()) {
                movieAdapter.setData(favMovies)
                movieAdapter.notifyDataSetChanged()
                ivNodata.visibility = View.GONE
            }else {
                ivNodata.visibility = View.VISIBLE
            }
        })
    }

}