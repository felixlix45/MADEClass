package com.felix.madeclass

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority

import com.felix.madeclass.adapter.MovieAdapter
import com.felix.madeclass.model.Movie

import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.facebook.shimmer.ShimmerFrameLayout
import org.json.JSONObject
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.util.Log
import com.felix.madeclass.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MoviesFragment : Fragment() {

    private var rvMovie: RecyclerView? = null
    private var movies: ArrayList<Movie>? = null
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_movies, container, false)
        val url:String = resources.getString(R.string.url_movie, API_KEY)

        moviesViewModel = ViewModelProviders.of(requireActivity()).get(MoviesViewModel::class.java)
        moviesViewModel.getMovies().observe(this, Observer<ArrayList<Movie>>{movieList ->
            if(movieList != null){
                movieAdapter.setData(movieList)
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
            }
        })

        moviesViewModel.setMovie(url)
        movieAdapter = MovieAdapter(requireContext())
        movieAdapter.notifyDataSetChanged()

        rvMovie = v.findViewById(R.id.rvMovie)
        rvMovie!!.layoutManager = LinearLayoutManager(requireActivity())
        rvMovie!!.adapter = movieAdapter

        rvMovie = v.findViewById(R.id.rvMovie)
        shimmerFrameLayout = v.findViewById(R.id.shimmer_container)

        return v
    }



    private fun showRecyclerView() {
        val linearLayout: LinearLayoutManager = LinearLayoutManager(requireActivity())
        rvMovie!!.layoutManager = linearLayout
        movieAdapter = MovieAdapter(requireActivity())
        movieAdapter.notifyDataSetChanged()
        rvMovie!!.adapter = movieAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        AndroidNetworking.forceCancelAll()
    }


    companion object {
        private val TAG = "MoviesFragment"

        private val API_KEY = "6b7475ec840c3b36442bd75785b232d9"
    }
}