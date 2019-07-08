package com.felix.madeclass

import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
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

        val txtTitle: TextView
        val txtOverview: TextView
        val txtRating: TextView
        val txtReleaseDate: TextView
        val imgPoster: ImageView

        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        txtTitle = findViewById(R.id.tvMovieTitle)
        txtOverview = findViewById(R.id.tvMovieOverview)
        txtRating = findViewById(R.id.tvMovieRating)
        txtReleaseDate = findViewById(R.id.tvMovieReleaseDate)
        imgPoster = findViewById(R.id.ivMoviePoster)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        val title = movie.title
        val overview = movie.overview
        val rating = movie.rating
        val releaseDate = movie.release_date
        Glide.with(baseContext)
                .load(movie.photoHigh)
                .thumbnail(
                        Glide.with(baseContext)
                                .load(movie.photoLow)
                )
                .into(imgPoster)

        txtTitle.text = title
        txtOverview.text = overview
        txtRating.text = rating
        txtReleaseDate.text = releaseDate


        supportActionBar!!.title = title
    }

    companion object {
        private val EXTRA_MOVIE = "extra_movie"
    }
}
