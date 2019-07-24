package com.felix.madeclass


import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var imgNoInternet:ImageView

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieAdapter: MovieAdapter

    private lateinit var url: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_movies, container, false)
        url = resources.getString(R.string.url_movie, BuildConfig.API_KEY)

        val swipeRefreshLayout: SwipeRefreshLayout = v.findViewById(R.id.swipeContainer)
        imgNoInternet = v.findViewById(R.id.ivNoInternet)
        rvMovie = v.findViewById(R.id.rvMovie)

        movieAdapter = MovieAdapter(requireContext())
        moviesViewModel = ViewModelProviders.of(requireActivity()).get(MoviesViewModel::class.java)
        moviesViewModel.getMovies().observe(this, Observer<ArrayList<Movie>> { movieList ->
            if (movieList.isNotEmpty()) {
                movieAdapter.setData(movieList)
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
            } else {
                Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "No Data Available", Snackbar.LENGTH_SHORT).show()
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            shimmerFrameLayout.visibility = View.VISIBLE
            shimmerFrameLayout.startShimmer()
            if(!isNetworkAvailable()){
                imgNoInternet.visibility = View.VISIBLE
                swipeContainer.isRefreshing = false
                Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "Check your internet connection", Snackbar.LENGTH_LONG)
                        .setAction("Try Again", View.OnClickListener {
                            loadData()
                            buildRecycleView()
                        })
                        .show()
            }else {
                loadData()
                rvMovie = v.findViewById(R.id.rvMovie)
                buildRecycleView()
                swipeContainer.isRefreshing = false
            }
        }

        loadData()

        buildRecycleView()

        shimmerFrameLayout = v.findViewById(R.id.shimmer_container)

        return v
    }

    private fun loadData(){
        if(!isNetworkAvailable()){
            imgNoInternet.visibility = View.VISIBLE
            Snackbar.make(activity!!.findViewById(R.id.coordinatorLayout), "Check your internet connection", Snackbar.LENGTH_LONG)
                    .setAction("Try Again", View.OnClickListener {
                        loadData()
                        buildRecycleView()
                    })
                    .show()
        }else{
            imgNoInternet.visibility = View.GONE
            moviesViewModel.setMovie(url)
            movieAdapter = MovieAdapter(requireContext())
            movieAdapter.notifyDataSetChanged()
        }

    }
    private fun buildRecycleView(){
        val orientation: Int = resources.configuration.orientation
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            rvMovie!!.layoutManager = LinearLayoutManager(requireActivity())
        }else{
            rvMovie!!.layoutManager = GridLayoutManager(requireActivity(),2)
        }

        rvMovie!!.adapter = movieAdapter
    }

    private fun isNetworkAvailable(): Boolean{
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
