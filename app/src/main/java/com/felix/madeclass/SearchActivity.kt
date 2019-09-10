package com.felix.madeclass

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.felix.madeclass.adapter.MovieAdapter
import com.felix.madeclass.adapter.TvAdapter
import com.felix.madeclass.model.Movie
import com.felix.madeclass.model.TvShow
import com.felix.madeclass.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var shimmerContainer: ShimmerFrameLayout
    private lateinit var rvSearch: RecyclerView
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var tvAdapter: TvAdapter
    private lateinit var activities: String
    private lateinit var query:String
    private lateinit var url: String

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val intent: Intent = getIntent()
        query = intent.getStringExtra(extra_query)
        activities = intent.getStringExtra(extra_activity)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        shimmerContainer = findViewById(R.id.shimmer_container)
        rvSearch = findViewById(R.id.rvSearch)

        setSupportActionBar(toolbar)
        if(supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val svSearch: SearchView = findViewById(R.id.svSearch)
        svSearch.setQuery(query, false)
        svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query.toString(), Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        if(activities == "Movies"){
            url = resources.getString(R.string.url_search_movie, BuildConfig.API_KEY, query)
            movieAdapter = MovieAdapter(this)
            searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
            searchViewModel.getSearchMovie().observe(this, Observer<ArrayList<Movie>>{list ->
                if(list.isNotEmpty()){
                    movieAdapter.setData(list)
                    shimmerContainer.stopShimmer()
                    shimmerContainer.visibility = View.GONE
                }else{
                    Toast.makeText(this, "Err.. something went wrong", Toast.LENGTH_SHORT).show()
                }
            })
            loadDataMovie()
            buildRecyclerView()
        }else{
            url = resources.getString(R.string.url_search_tv, BuildConfig.API_KEY, query)
            tvAdapter = TvAdapter(this)
            searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
            searchViewModel.getSearchTV().observe(this, Observer<ArrayList<TvShow>>{list ->
                if(list.isNotEmpty()){
                    tvAdapter.setData(list)
                    shimmerContainer.stopShimmer()
                    shimmerContainer.visibility = View.GONE
                }else{
                    Toast.makeText(this, "Err.. something went wrong", Toast.LENGTH_SHORT).show()
                }
            })
            loadDataMovie()
            buildRecyclerView()
        }
    }

    private fun loadDataMovie(){
        if(activities == "Movies"){
            searchViewModel.setMovie(url)
            movieAdapter = MovieAdapter(this)
            movieAdapter.notifyDataSetChanged()
        }else{
            searchViewModel.setTv(url)
            tvAdapter = TvAdapter(this)
            tvAdapter.notifyDataSetChanged()
        }

    }

    private fun buildRecyclerView(){
        if(activities == "Movies"){
            val orientation: Int = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                rvSearch.layoutManager = LinearLayoutManager(this)
            } else {
                rvSearch.layoutManager = GridLayoutManager(this, 2)
            }
            rvSearch.adapter = movieAdapter
        }else{
            rvSearch.layoutManager = LinearLayoutManager(this)
            rvSearch.adapter = tvAdapter
        }

    }

    companion object{
       const val extra_query: String = "EXTRA_QUERY"
       const val extra_activity:String = "EXTRA_ACTIVITY"
    }
}
