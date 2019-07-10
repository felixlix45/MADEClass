package com.felix.madeclass

import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
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

        val txtTitle: TextView
        val txtOverview: TextView
        val txtRating: TextView
        val txtReleaseDate: TextView
        val imgPoster: ImageView

        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        txtTitle = findViewById(R.id.tvTvTitle)
        txtOverview = findViewById(R.id.tvTvOverview)
        txtRating = findViewById(R.id.tvTvRating)
        txtReleaseDate = findViewById(R.id.tvTvReleaseDate)
        imgPoster = findViewById(R.id.ivTvPoster)

        val tvShow = intent.getParcelableExtra<TvShow>(EXTRA_TV)
        val title = tvShow.title
        val overview = tvShow.overview
        val rating = tvShow.rating
        val releaseDate = tvShow.release_date

        txtTitle.text = title
        txtOverview.text = overview
        txtRating.text = rating
        txtReleaseDate.text = releaseDate
        Glide.with(baseContext)
                .load(tvShow.photoHigh)
                .thumbnail(
                        Glide.with(baseContext)
                                .load(tvShow.photoLow)
                )
                .into(imgPoster)

        supportActionBar!!.title = title
    }

    companion object {

        private val EXTRA_TV = "extra_tv"
    }
}
