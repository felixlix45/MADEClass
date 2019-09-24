package com.felix.madeclass


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.felix.madeclass.adapter.MovieAdapter
import com.felix.madeclass.model.Movie
import com.felix.madeclass.viewmodel.MoviesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies.*


class MoviesFragment : androidx.fragment.app.Fragment() {

    private var rvMovie: androidx.recyclerview.widget.RecyclerView? = null

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieAdapter: MovieAdapter

    private lateinit var url: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_movies, container, false)
        url = resources.getString(R.string.url_movie, BuildConfig.API_KEY)

        val swipeRefreshLayout: SwipeRefreshLayout = v.findViewById(R.id.swipeContainer)
        val shimmerFrameLayout: ShimmerFrameLayout = v.findViewById(R.id.shimmer_container)
        val svMovie: SearchView = v.findViewById(R.id.svMovie)
        svMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                val intent = Intent(activity, SearchActivity::class.java)
                intent.putExtra("EXTRA_QUERY", query)
                intent.putExtra("EXTRA_ACTIVITY", "Movies")
                startActivity(intent)
                svMovie.clearFocus()
                svMovie.setQuery("", true)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        rvMovie = v.findViewById(R.id.rvMovie)
        movieAdapter = MovieAdapter(requireContext())

        swipeRefreshLayout.isRefreshing = true
        moviesViewModel = ViewModelProviders.of(requireActivity()).get(MoviesViewModel::class.java)
        moviesViewModel.getMovies().observe(this, Observer<ArrayList<Movie>> { movieList ->
            if (movieList.isNotEmpty()) {
                movieAdapter.setData(movieList)
                swipeRefreshLayout.isRefreshing = false
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
            } else {
                Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "No Data Available", Snackbar.LENGTH_SHORT).show()
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            shimmerFrameLayout.visibility = View.VISIBLE
            shimmerFrameLayout.startShimmer()
            if (!isNetworkAvailable()) {
                swipeContainer.isRefreshing = false
                shimmerFrameLayout.visibility = View.GONE
                Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "Check your internet connection", Snackbar.LENGTH_LONG).show()
            } else {
                loadData()
                rvMovie = v.findViewById(R.id.rvMovie)
                buildRecycleView()
                swipeContainer.isRefreshing = false
            }
        }

        loadData()
        buildRecycleView()

        if (!isNetworkAvailable()) {
            shimmerFrameLayout.visibility = View.GONE
            Handler().postDelayed({ swipeRefreshLayout.isRefreshing = false }, 2000)
        }
        return v
    }

    private fun loadData() {
        if (!isNetworkAvailable()) {
            Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "Check your internet connection", Snackbar.LENGTH_LONG).show()
        } else {
            moviesViewModel.setMovie(url)
            movieAdapter = MovieAdapter(requireContext())
            movieAdapter.notifyDataSetChanged()
        }
    }

    private fun buildRecycleView() {
        val orientation: Int = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvMovie!!.layoutManager = LinearLayoutManager(requireActivity())
        } else {
            rvMovie!!.layoutManager = GridLayoutManager(requireActivity(), 2)
        }

        rvMovie!!.adapter = movieAdapter
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
