package com.felix.madeclass.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.felix.madeclass.R
import com.felix.madeclass.model.MovieFavorite
import java.text.SimpleDateFormat
import java.util.*
import android.text.TextUtils
import kotlin.collections.ArrayList


class FavoriteMovieAdapter(var context: Context) : RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {


    private var listMovie:List<MovieFavorite> = ArrayList()

    fun setData(movies: List<MovieFavorite>) {
        this.listMovie = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val currentActivity = this.context.toString()
        lateinit var itemRow:View
        if (!TextUtils.isEmpty(currentActivity)) {
            if(currentActivity.contains("com.felix.madeclass.DetailMovieActivity")) {
                itemRow = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie_similar, viewGroup, false)
            } else{
                itemRow = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie, viewGroup, false)
            }
        }
        return ViewHolder(itemRow)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(listMovie[i])

        viewHolder.constraintLayout.setOnClickListener {
            val movie = MovieFavorite()

            movie.movieId = listMovie[viewHolder.adapterPosition].movieId
            movie.title = listMovie[viewHolder.adapterPosition].title
            movie.overview = listMovie[viewHolder.adapterPosition].overview
            movie.rating = listMovie[viewHolder.adapterPosition].rating
            movie.releaseDate = listMovie[viewHolder.adapterPosition].releaseDate
            movie.photoHigh = listMovie[viewHolder.adapterPosition].photoHigh
            movie.photoLow = listMovie[viewHolder.adapterPosition].photoLow
            movie.photoBackdropHigh = listMovie[viewHolder.adapterPosition].photoBackdropHigh
            movie.photoBackdropLow = listMovie[viewHolder.adapterPosition].photoBackdropLow
            movie.adult = listMovie[viewHolder.adapterPosition].adult

//            val toDetail = Intent(v.context, DetailMovieActivity::class.java)
//            toDetail.putExtra("extra_movie", movie)
//            v.context.startActivity(toDetail)
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)
        private val txtRating: TextView = itemView.findViewById(R.id.tvMovieRating)
        private val txtReleaseDate: TextView = itemView.findViewById(R.id.tvReleaseDate)
        private val imgPoster: ImageView = itemView.findViewById(R.id.ivMoviePoster)
        private val ivPG: ImageView = itemView.findViewById(R.id.ivParentalGuidance)
        private val ivMA: ImageView = itemView.findViewById(R.id.ivMature)
        internal val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.lvParentMovie)


        fun bind(movie: MovieFavorite) {
            txtTitle.text = movie.title
            txtRating.text = movie.rating
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val dateFormat = SimpleDateFormat(context.resources.getString(R.string.release_date), Locale.getDefault())
            val date: Date = formatter.parse(movie.releaseDate)
            val releaseDate = dateFormat.format(date).toString()
            txtReleaseDate.text = releaseDate
            Glide.with(context)
                    .load(movie.photoHigh)
                    .fallback(R.drawable.no_image_available)
                    .thumbnail(
                            Glide.with(context)
                                    .load(movie.photoLow)
                    )
                    .into(imgPoster)

            if (movie.adult == true){
                ivMA.visibility = View.VISIBLE
                ivPG.visibility = View.GONE
            }else{
                ivMA.visibility = View.GONE
                ivPG.visibility = View.VISIBLE
            }
        }
    }
}
