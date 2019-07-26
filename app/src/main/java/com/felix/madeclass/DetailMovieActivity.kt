package com.felix.madeclass


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.felix.madeclass.adapter.MovieAdapter
import com.felix.madeclass.model.Movie
import com.felix.madeclass.model.MovieFavorite
import com.felix.madeclass.viewmodel.FavoriteMoviesViewModel
import com.felix.madeclass.viewmodel.MoviesViewModel
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class DetailMovieActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var movieParcel: Movie
    private lateinit var movie: Movie
    private lateinit var name: String
    private lateinit var txtGenres:TextView
    private lateinit var txtDuration: TextView


    private lateinit var ibPlayTrailer: ImageButton

    private lateinit var shimmerDuration: ShimmerFrameLayout
    private lateinit var shimmerGenres: ShimmerFrameLayout
    private lateinit var shimmerSimilar: ShimmerFrameLayout

    var genre: String = ""


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ibPlayTrailer -> {
                val toYT = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$name"))
                v.context.startActivity(toYT)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->{
                onBackPressed()
                return true
            }
            R.id.menuFavorite ->{
                Toast.makeText(applicationContext, "Tapped", Toast.LENGTH_LONG).show()
                saveMovies()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveMovies(){
        val movieFavorite = MovieFavorite()

        movieFavorite.movieId = movie.movieId
        movieFavorite.photoHigh = movie.photoHigh
        movieFavorite.photoLow = movie.photoLow
        movieFavorite.photoBackdropHigh = movie.photoBackdropHigh
        movieFavorite.photoBackdropLow = movie.photoBackdropLow
        movieFavorite.title = movie.title
        movieFavorite.releaseDate = movie.releaseDate
        movieFavorite.rating = movie.rating
        movieFavorite.overview = movie.overview
        movieFavorite.adult = movie.adult
        Log.e("Detail", movieFavorite.toString())
        val favoriteMoviesViewModel = FavoriteMoviesViewModel(application)
        favoriteMoviesViewModel.insert(movieFavorite)
        Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val toolbar:Toolbar = findViewById(R.id.toolbar)

        val txtTitle: TextView = findViewById(R.id.tvMovieTitle)
        val txtOverview: TextView = findViewById(R.id.tvMovieOverview)
        val txtRating: TextView = findViewById(R.id.tvMovieRating)
        val txtReleaseDate: TextView = findViewById(R.id.tvMovieReleaseDate)
        val txtNoSimilarMovie: TextView = findViewById(R.id.tvNoSimilarMovie)

        val imgBackdrop: ImageView = findViewById(R.id.ivMovieBackdrop)
        val imgPoster: ImageView = findViewById(R.id.ivMoviePoster)

        ibPlayTrailer = findViewById(R.id.ibPlayTrailer)

        txtGenres = findViewById(R.id.tvGenres)
        txtDuration = findViewById(R.id.tvMovieDuration)
        shimmerDuration = findViewById(R.id.shimmerMovieDuration)
        shimmerGenres = findViewById(R.id.shimmerMovieGenre)

        shimmerSimilar = findViewById(R.id.shimmerSimilar)

        setSupportActionBar(toolbar)
        ibPlayTrailer.visibility = View.GONE
        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            if (intent.getParcelableExtra<Movie>(EXTRA_MOVIE) != null) {
                movie = intent.getParcelableExtra(EXTRA_MOVIE)
                movieParcel = movie

                val title = movie.title
                val overview = movie.overview
                val rating = movie.rating
                var releaseDate = movie.releaseDate

                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val dateFormat = SimpleDateFormat(resources.getString(R.string.release_date), Locale.getDefault())
                val date: Date = formatter.parse(releaseDate)
                releaseDate = dateFormat.format(date).toString()

                Glide.with(baseContext)
                        .load(movie.photoBackdropHigh)
                        .error(
                                Glide.with(baseContext)
                                        .load(R.drawable.no_image_available)
                        )
                        .priority(com.bumptech.glide.Priority.IMMEDIATE)
                        .thumbnail(
                                Glide.with(baseContext)
                                        .load(movie.photoBackdropLow)
                                        .error(
                                                Glide.with(baseContext)
                                                        .load(R.drawable.no_image_available)
                                        )
                                        .priority(com.bumptech.glide.Priority.HIGH)
                                        .thumbnail(
                                                Glide.with(baseContext)
                                                        .load(R.drawable.inkling_spinner)
                                                        .centerCrop()
                                        )
                        )
                        .into(imgBackdrop)
                Glide.with(baseContext)
                        .load(movie.photoHigh)
                        .fallback(R.drawable.no_image_available)

                        .thumbnail(
                                Glide.with(baseContext)
                                        .load(movie.photoLow)
                                        .priority(com.bumptech.glide.Priority.HIGH)
                                        .thumbnail(
                                                Glide.with(baseContext)
                                                        .load(R.drawable.inkling_spinner)


                                        )
                        )
                        .into(imgPoster)
                txtTitle.text = title
                txtOverview.text = overview
                txtRating.text = rating
                txtReleaseDate.text = releaseDate



                shimmerDuration.startShimmer()
                shimmerGenres.startShimmer()
                shimmerDuration.visibility = View.VISIBLE
                shimmerDuration.visibility = View.VISIBLE

                getMoreDetail()



                ibPlayTrailer.setOnClickListener(this)


                //Similar Movies
                val movieAdapter = MovieAdapter(this)
                val rvSimilarMovie: RecyclerView = findViewById(R.id.rvSimilarMovie)
                val moviesViewModel: MoviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
                moviesViewModel.getMovies().observe(this, androidx.lifecycle.Observer { movieList ->
                    if(movieList.isNotEmpty()){
                        movieAdapter.setData(movieList)
                    }else{
                        txtNoSimilarMovie.visibility = View.VISIBLE
                        rvSimilarMovie.visibility = View.GONE
                    }
                    shimmerSimilar.visibility = View.GONE
                })
                moviesViewModel.setMovie(resources.getString(R.string.url_movie_similar, movie.movieId, BuildConfig.API_KEY))
                movieAdapter.notifyDataSetChanged()

                rvSimilarMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                rvSimilarMovie.adapter = movieAdapter

                supportActionBar?.title = title
            } else {
                Toast.makeText(baseContext, "Err... Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }


    companion object {
        private const val EXTRA_MOVIE = "extra_movie"

    }

    private fun getMoreDetail(){
        AndroidNetworking.get(resources.getString(R.string.url_movie_detail, movie.movieId.toString() ,BuildConfig.API_KEY))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject) {
                        val duration = response.get("runtime").toString() + " minutes"
                        if(!response.isNull("runtime")){
                            txtDuration.text = duration
                        }else{
                            txtDuration.text = resources.getString(R.string.no_duration)
                        }
                        val genres = response.getJSONArray("genres")

                        for(j in 0 until genres.length()){
                            if(j == genres.length()-1){
                                genre += genres.getJSONObject(j).get("name").toString()
                            }else{
                                genre += genres.getJSONObject(j).get("name").toString() + " | "
                            }
                        }
                        txtGenres.text = genre
                        shimmerDuration.stopShimmer()
                        shimmerGenres.stopShimmer()
                        shimmerDuration.visibility = View.GONE
                        shimmerGenres.visibility = View.GONE
                        txtDuration.visibility = View.VISIBLE
                        txtGenres.visibility = View.VISIBLE
                    }

                    override fun onError(anError: ANError?) {
                        txtDuration.text = resources.getString(R.string.no_duration)
                    }
                })

        AndroidNetworking.get(resources.getString(R.string.url_movie_trailer, movie.movieId.toString(), BuildConfig.API_KEY))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject) {
                        val jArray = response.getJSONArray("results")
                        if(!jArray.isNull(0)){
                            name = jArray.getJSONObject(0)?.get("key").toString()
                            ibPlayTrailer.visibility = View.VISIBLE
                        }
                    }

                    override fun onError(anError: ANError?) {
                        ibPlayTrailer.visibility = View.GONE
                    }
                })
    }
}
