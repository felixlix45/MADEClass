package com.felix.madeclass.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide

import com.felix.madeclass.DetailMovieActivity
import com.felix.madeclass.R
import com.felix.madeclass.model.Movie

import java.util.ArrayList




class MovieAdapter(var context: Context) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    private var listMovie = ArrayList<Movie>()

    fun setData(movies: ArrayList<Movie>){
        listMovie!!.clear()
        listMovie!!.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val itemRow = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie, viewGroup, false)
        return ViewHolder(itemRow)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(listMovie!![i])

        viewHolder.layoutParent.setOnClickListener { v ->
            val movie = Movie()

            movie.title = listMovie!![viewHolder.adapterPosition].title
            movie.overview = listMovie!![viewHolder.adapterPosition].overview
            movie.rating = listMovie!![viewHolder.adapterPosition].rating
            movie.release_date = listMovie!![viewHolder.adapterPosition].release_date
            movie.photoHigh = listMovie!![viewHolder.adapterPosition].photoHigh
            movie.photoLow = listMovie!![viewHolder.adapterPosition].photoLow
            movie.photoBackdropHigh = listMovie!![viewHolder.adapterPosition].photoBackdropHigh
            movie.photoBackdropLow = listMovie!![viewHolder.adapterPosition].photoBackdropLow

            val toDetail = Intent(v.context, DetailMovieActivity::class.java)
            toDetail.putExtra("extra_movie", movie)
            v.context.startActivity(toDetail)
        }
    }

    override fun getItemCount(): Int {
        return listMovie!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtTitle: TextView
        private val txtRating: TextView
        private val txtReleaseDate: TextView
        private val imgPoster: ImageView
        internal val layoutParent: LinearLayout

        init {

            txtRating = itemView.findViewById(R.id.tvMovieRating)
            txtTitle = itemView.findViewById(R.id.tvMovieTitle)
            txtReleaseDate = itemView.findViewById(R.id.tvReleaseDate)
            imgPoster = itemView.findViewById(R.id.ivMoviePoster)
            layoutParent = itemView.findViewById(R.id.lvParentMovie)

        }

        fun bind(movie: Movie) {
            txtTitle.text = movie.title
            txtRating.text = movie.rating
            txtReleaseDate.text = movie.release_date
            Glide.with(context)
                    .load(movie.photoHigh)
                    .fallback(com.felix.madeclass.R.drawable.no_image_available)
                    .thumbnail(
                            Glide.with(context)
                                    .load(movie.photoLow)
                    )
                    .into(imgPoster)
        }
    }
}
