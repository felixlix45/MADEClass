package com.felix.madeclass

import android.arch.lifecycle.ViewModelProvider
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

import java.util.ArrayList
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


class MoviesFragment : Fragment() {

    private var rvMovie: RecyclerView? = null
    private var movies: ArrayList<Movie>? = null
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private lateinit var moviesViewModel: MoviesViewModel

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
        movieAdapter.notifyDataSetChanged()
        rvMovie!!.adapter = movieAdapter

    }

    private fun addItem() {
        movies = ArrayList()

        val url = resources.getString(R.string.url_movie, API_KEY)
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
                                movie.photoLow = "https://image.tmdb.org/t/p/w154/" + movieObj.get("poster_path").toString()
                                movie.rating = movieObj.get("vote_average").toString()
                                movie.release_date = movieObj.get("release_date").toString()
                                movie.photoBackdropHigh ="https://image.tmdb.org/t/p/original/" + movieObj.get("backdrop_path").toString()
                                movie.photoBackdropLow = "https://image.tmdb.org/t/p/w154/" + movieObj.get("backdrop_path").toString()
                                Log.e(TAG, movie.photoBackdropHigh.toString())
                                movies!!.add(movie)
                            }
                            showRecyclerView()
                        }else{
                            Toast.makeText(requireActivity(), "Failed", Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onError(anError: ANError?) {
                        if(anError!!.errorDetail == "connectionError"){
                            Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "Err... Connection Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
    }

    companion object {
        private val TAG = "MoviesFragment"

        private val API_KEY = "6b7475ec840c3b36442bd75785b232d9"
    }
}
