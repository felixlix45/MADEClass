package com.felix.madeclass


import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.bumptech.glide.Glide
import com.felix.madeclass.model.Movie
import java.text.SimpleDateFormat
import java.util.*


class DetailMovieActivity : AppCompatActivity() {

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

        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.getParcelableExtra<Movie>(EXTRA_MOVIE) != null) {


            val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
            val title = movie.title
            val overview = movie.overview
            val rating = movie.rating
            var releaseDate = movie.releaseDate

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            val date: Date = formatter.parse(releaseDate)
            releaseDate = dateFormat.format(date).toString()

            Glide.with(baseContext)
                    .load(movie.photoBackdropHigh)
                    .fallback(R.drawable.no_image_available)
                    .thumbnail(
                            Glide.with(baseContext)
                                    .load(movie.photoBackdropLow)
                                    .thumbnail(
                                            Glide.with(baseContext)
                                                    .load(R.drawable.inkling_spinner)
                                    )
                    )
                    .into(imgBackdrop)
            Glide.with(baseContext)
                    .load(movie.photoHigh)
                    .fallback(R.drawable.no_image_available)
                    .thumbnail(
                            Glide.with(baseContext)
                                    .load(R.drawable.inkling_spinner)
                    )
                    .into(imgPoster)
            txtTitle.text = title
            txtOverview.text = overview
            txtRating.text = rating
            txtReleaseDate.text = releaseDate
            val name = title?.replace("\\s+".toRegex(), "+")

            ibPlayTrailer.setOnClickListener { v->
                val toYT = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + name + "+trailer"))
                v.context.startActivity(toYT)
            }



            supportActionBar?.title = title
        } else {
            Toast.makeText(baseContext, "Err... Something went wrong", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private const val EXTRA_MOVIE = "extra_movie"
    }
}
