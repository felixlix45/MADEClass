package com.felix.madeclass

import android.content.res.TypedArray
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.ANRequest
import com.androidnetworking.common.Priority

import com.felix.madeclass.adapter.MovieAdapter
import com.felix.madeclass.model.Movie

import org.json.JSONArray

import java.util.ArrayList
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MoviesFragment : Fragment() {
    private var movieTitle: Array<String>? = null
    private var movieRating: Array<String>? = null
    private var movieReleaseDate: Array<String>? = null
    private var movieOverview: Array<String>? = null
    private var moviePoster: TypedArray? = null
    private var rvMovie: RecyclerView? = null
    private var movies: ArrayList<Movie>? = null
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_movies, container, false)
        rvMovie = v.findViewById(R.id.rvMovie)
        shimmerFrameLayout = v.findViewById(R.id.shimmer_container)
        addItem()

        return v
    }

    private fun showRecyclerView() {
        val linearLayout: LinearLayoutManager = LinearLayoutManager(requireActivity())
        rvMovie!!.layoutManager = linearLayout
        val movieAdapter = MovieAdapter(requireActivity(), movies)

        rvMovie!!.adapter = movieAdapter


    }

    private fun addItem() {
        movies = ArrayList()
        var url = resources.getString(R.string.url_movie, API_KEY)
        AndroidNetworking.initialize(requireActivity())
        shimmerFrameLayout.startShimmer()
        shimmerFrameLayout.visibility = View.VISIBLE
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {

                        if (response != null){
                            shimmerFrameLayout.stopShimmer()
                            shimmerFrameLayout.visibility = View.GONE
                            val movieArr = response.getJSONArray("results")
                            for(i in 0 until movieArr.length()){
                                val movieObj = movieArr.getJSONObject(i)
                                val movie = Movie()

                                movie.title = movieObj.get("title").toString()
                                movie.overview = movieObj.get("overview").toString()
                                movie.photoHigh = "https://image.tmdb.org/t/p/original/" + movieObj.get("poster_path").toString()
                                movie.photoLow = "https://image.tmdb.org/t/p/w185/" + movieObj.get("poster_path").toString()
                                movie.rating = movieObj.get("vote_average").toString()
                                movie.release_date = movieObj.get("release_date").toString()

                                movies!!.add(movie)
                            }
                            showRecyclerView()
                        }else{
                            Toast.makeText(requireActivity(), "Failed", Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(requireActivity(), "Failed", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun prepare() {
        movieTitle = resources.getStringArray(R.array.movieTitle)
        movieRating = resources.getStringArray(R.array.movieRating)
        movieReleaseDate = resources.getStringArray(R.array.movieReleaseDate)
        moviePoster = resources.obtainTypedArray(R.array.moviePoster)
        movieOverview = resources.getStringArray(R.array.movieOverview)
    }

    companion object {
        private val TAG = "MoviesFragment"



        private val API_KEY = "6b7475ec840c3b36442bd75785b232d9"
    }
}
