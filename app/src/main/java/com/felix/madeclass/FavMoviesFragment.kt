package com.felix.madeclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felix.madeclass.adapter.FavoriteMovieAdapter
import com.felix.madeclass.model.MovieFavorite
import com.felix.madeclass.viewmodel.FavoriteMoviesViewModel


class FavMoviesFragment : Fragment() {

    private lateinit var favoriteMoviesViewModel: FavoriteMoviesViewModel
    private lateinit var movieAdapter: FavoriteMovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_fav_movies, container, false)

        val rvFavMovie: RecyclerView = v.findViewById(R.id.rvFavMovie)
        rvFavMovie.layoutManager = LinearLayoutManager(requireActivity())
        rvFavMovie.setHasFixedSize(true)


        movieAdapter = FavoriteMovieAdapter(requireActivity())
        rvFavMovie.adapter = movieAdapter
        favoriteMoviesViewModel = ViewModelProviders.of(requireActivity()).get(FavoriteMoviesViewModel::class.java)
        favoriteMoviesViewModel.getAllMovie().observe(requireActivity(), Observer<List<MovieFavorite>> { favMovies ->
            if (favMovies.isNotEmpty()) {
                movieAdapter.setData(favMovies)
                movieAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "No Data", Toast.LENGTH_SHORT).show()
            }

//            Toast.makeText(requireContext(), "onChanged", Toast.LENGTH_SHORT).show()

        })

        return v
    }
}