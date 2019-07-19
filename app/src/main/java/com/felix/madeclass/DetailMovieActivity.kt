package com.felix.madeclass


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.felix.madeclass.model.Movie
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class DetailMovieActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var movieParcel: Movie
    private lateinit var name: String

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ibPlayTrailer -> {
                val toYT = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=$name+trailer"))
                v.context.startActivity(toYT)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val txtTitle: TextView = findViewById(R.id.tvMovieTitle)
        val txtOverview: TextView = findViewById(R.id.tvMovieOverview)
        val txtRating: TextView = findViewById(R.id.tvMovieRating)
        val txtReleaseDate: TextView = findViewById(R.id.tvMovieReleaseDate)
        val imgBackdrop: ImageView = findViewById(R.id.ivMovieBackdrop)
        val imgPoster: ImageView = findViewById(R.id.ivMoviePoster)
        val ibPlayTrailer: ImageButton = findViewById(R.id.ibPlayTrailer)
        val txtDuration: TextView = findViewById(R.id.tvMovieDuration)
        val shimmerDuration: ShimmerFrameLayout = findViewById(R.id.shimmerMovieDuration)
        val toolbar:Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            if (intent.getParcelableExtra<Movie>(EXTRA_MOVIE) != null) {
                val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
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
                        .fallback(R.drawable.no_image_available)
                        .priority(com.bumptech.glide.Priority.IMMEDIATE)
                        .thumbnail(
                                Glide.with(baseContext)
                                        .load(movie.photoBackdropLow)
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
                name = title?.replace("\\s+".toRegex(), "+") ?: ""

                shimmerDuration.startShimmer()
                shimmerDuration.visibility = View.VISIBLE
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
                                shimmerDuration.stopShimmer()
                                shimmerDuration.visibility = View.GONE
                                txtDuration.visibility = View.VISIBLE
                            }

                            override fun onError(anError: ANError?) {
                                txtDuration.text = resources.getString(R.string.no_duration)
                            }
                        })

                ibPlayTrailer.setOnClickListener(this)


                supportActionBar?.title = title
            } else {
                Toast.makeText(baseContext, "Err... Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }



    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putParcelable("movie", movieParcel)
    }

    companion object {
        private const val EXTRA_MOVIE = "extra_movie"
    }
}
