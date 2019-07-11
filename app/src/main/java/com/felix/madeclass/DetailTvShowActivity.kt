package com.felix.madeclass

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.bumptech.glide.Glide
import com.felix.madeclass.model.TvShow

class DetailTvShowActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_detail_tv_show)

        val txtTitle: TextView = findViewById(R.id.tvTvTitle)
        val txtOverview: TextView = findViewById(R.id.tvTvOverview)
        val txtRating: TextView = findViewById(R.id.tvTvRating)
        val txtReleaseDate: TextView = findViewById(R.id.tvTvReleaseDate)
        val imgPoster: ImageView = findViewById(R.id.ivTvPoster)

        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.getParcelableExtra<TvShow>(EXTRA_TV) != null) {
            val tvShow = intent.getParcelableExtra<TvShow>(EXTRA_TV)
            val title = tvShow.title
            val overview = tvShow.overview
            val rating = tvShow.rating
            val releaseDate = tvShow.releaseDate

            txtTitle.text = title
            txtOverview.text = overview
            txtRating.text = rating
            txtReleaseDate.text = releaseDate
            Glide.with(baseContext)
                    .load(tvShow.photoHigh)
                    .fallback(R.drawable.no_image_available)
                    .thumbnail(
                            Glide.with(baseContext)
                                    .load(R.drawable.inkling_spinner)
                    )
                    .into(imgPoster)

            supportActionBar?.title = title
        } else {
            Toast.makeText(baseContext, "Err... Something went wrong", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private val EXTRA_TV = "extra_tv"
    }
}
