package com.felix.madeclass

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.felix.madeclass.model.Movie

class DetailMovieActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
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

        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.getParcelableExtra<Movie>(EXTRA_MOVIE) != null) {
            val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
            val title = movie.title
            val overview = movie.overview
            val rating = movie.rating
            val releaseDate = movie.releaseDate
            Glide.with(baseContext)
                    .load(movie.photoBackdropHigh)
                    .fallback(R.drawable.no_image_available)
                    .thumbnail(
                            Glide.with(baseContext)
                                    .load(R.drawable.inkling_spinner)
                    )
                    .into(imgBackdrop)
            Glide.with(baseContext)
                    .load(movie.photoHigh)
                    .fallback(R.drawable.no_image_available)
                    .thumbnail(
                            Glide.with(baseContext)
                                    .load(movie.photoLow)
                    )
                    .into(imgPoster)
            txtTitle.text = title
            txtOverview.text = overview
            txtRating.text = rating
            txtReleaseDate.text = releaseDate


            supportActionBar?.title = title
        } else {
            Toast.makeText(baseContext, "Err... Something went wrong", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private val EXTRA_MOVIE = "extra_movie"
    }
}
