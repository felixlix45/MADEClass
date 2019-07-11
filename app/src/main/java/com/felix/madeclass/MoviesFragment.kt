package com.felix.madeclass

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.felix.madeclass.adapter.MovieAdapter
import com.felix.madeclass.model.Movie


import com.facebook.shimmer.ShimmerFrameLayout
import com.felix.madeclass.viewmodel.MoviesViewModel
import kotlin.collections.ArrayList


class MoviesFragment : Fragment() {

    private var rvMovie: RecyclerView? = null
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_movies, container, false)
        val url:String = resources.getString(R.string.url_movie, BuildConfig.API_KEY)

        moviesViewModel = ViewModelProviders.of(requireActivity()).get(MoviesViewModel::class.java)
        moviesViewModel.getMovies().observe(this, Observer<ArrayList<Movie>>{ movieList ->
            if(movieList != null){
                movieAdapter.setData(movieList)
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
            }else{
                Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "No Data Available", Snackbar.LENGTH_SHORT).show()
            }
        })

        moviesViewModel.setMovie(url)
        movieAdapter = MovieAdapter(requireContext())
        movieAdapter.notifyDataSetChanged()

        rvMovie = v.findViewById(R.id.rvMovie)
        rvMovie!!.layoutManager = LinearLayoutManager(requireActivity())
        rvMovie!!.adapter = movieAdapter

        shimmerFrameLayout = v.findViewById(R.id.shimmer_container)

        return v
    }
}
