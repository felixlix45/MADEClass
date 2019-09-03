package com.felix.madeclass

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.room.Room
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.felix.madeclass.database.FavoriteDatabase
import com.felix.madeclass.model.TvShow
import com.felix.madeclass.model.TvShowFavorite
import com.felix.madeclass.viewmodel.FavoriteTvViewModel
import org.json.JSONObject

class DetailTvShowActivity : AppCompatActivity() {

    lateinit var txtSeasonsAndEpisodes: TextView
    private lateinit var tvShow: TvShow

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
            }
            R.id.menuFavorite ->{
                saveTv()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun saveTv(){
        Toast.makeText(baseContext, "Tapped", Toast.LENGTH_SHORT).show()
        val tvFavorite = TvShowFavorite()

        val favoriteTvViewModel = FavoriteTvViewModel(application)

        tvFavorite.tvId = tvShow.tvId
        tvFavorite.seasons = tvShow.seasons
        tvFavorite.episodes = tvShow.episodes
        tvFavorite.overview = tvShow.overview
        tvFavorite.photoBackdropHigh = tvShow.photoBackdropHigh
        tvFavorite.photoBackdropLow = tvShow.photoBackdropLow
        tvFavorite.photoHigh = tvShow.photoHigh
        tvFavorite.photoLow = tvShow.photoLow
        tvFavorite.releaseDate = tvShow.releaseDate
        tvFavorite.title = tvShow.title
        tvFavorite.rating = tvShow.rating

        val instance = Room.databaseBuilder(this.baseContext, FavoriteDatabase::class.java, "favorite_database").allowMainThreadQueries().build()
        if(instance.tvDao().getTv(tvShow.tvId.toString())!= 0){
            favoriteTvViewModel.deleteTv(tvShow.tvId.toString())
            Toast.makeText(applicationContext, "Deleted from favorite", Toast.LENGTH_SHORT).show()
        }else{
            favoriteTvViewModel.insert(tvFavorite)
            Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
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
        val imgBackground: ImageView = findViewById(R.id.ivTvBackground)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        txtSeasonsAndEpisodes = findViewById(R.id.tvTvSeasonsAndEpisodes)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.getParcelableExtra<TvShow>(EXTRA_TV) != null) {
            tvShow = intent.getParcelableExtra(EXTRA_TV)
            val title = tvShow.title
            val overview = tvShow.overview
            val rating = tvShow.rating
            val releaseDate = tvShow.releaseDate
            val tvID = tvShow.tvId
            if (tvID != null) {
                getMoreDetail(tvID)
            }
            txtTitle.text = title
            txtOverview.text = overview
            txtReleaseDate.text = releaseDate
            txtRating.text = rating
            if(rating != null){
                if(rating.toFloat() >= 7){
                    txtRating.setBackgroundResource(R.drawable.rounded_shape_green)
                }
            }


            Glide.with(baseContext)
                    .load(tvShow.photoHigh)
                    .error(
                            Glide.with(baseContext)
                                    .load(R.drawable.no_image_available)
                    )
                    .thumbnail(
                            Glide.with(baseContext)
                                    .load(tvShow.photoLow)
                                    .thumbnail(
                                            Glide.with(baseContext)
                                                    .load(R.drawable.inkling_spinner)
                                    )
                    )
                    .into(imgPoster)

            Glide.with(baseContext)
                    .load(tvShow.photoBackdropHigh)
                    .error(
                            Glide.with(baseContext)
                                    .load(R.drawable.no_image_available)
                    )
                    .thumbnail(
                            Glide.with(baseContext)
                                    .load(tvShow.photoBackdropLow)
                                    .thumbnail(
                                            Glide.with(baseContext)
                                                    .load(R.drawable.inkling_spinner)
                                    )
                    )
                    .into(imgBackground)
            supportActionBar?.title = title
        } else {
            Toast.makeText(baseContext, "Err... Something went wrong", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getMoreDetail(tvID: String){
        AndroidNetworking.get(resources.getString(R.string.url_tv_detail, tvID, BuildConfig.API_KEY))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        val episodes = response?.getString("number_of_episodes").toString()
                        val seasons = response?.getString("number_of_seasons").toString()
                        txtSeasonsAndEpisodes.text = resources.getString(R.string.sum_tv_seasons_episodes, seasons, episodes)
                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(applicationContext, "Error occured, please try again.", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    companion object {
        private const val EXTRA_TV = "extra_tv"
    }
}
