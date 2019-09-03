package com.felix.madeclass.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.felix.madeclass.R
import com.felix.madeclass.model.TvShowFavorite
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FavoriteTvAdapter(var context: Context) : RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder>() {

    private var listTv: List<TvShowFavorite> = ArrayList()

    fun setData(tv: List<TvShowFavorite>){
        this.listTv = tv
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemRow:View = LayoutInflater.from(parent.context).inflate(R.layout.item_tv, parent, false)
        return ViewHolder(itemRow)
    }

    override fun getItemCount(): Int {
        return listTv.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTv[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val txtTitle: TextView = itemView.findViewById(R.id.tvTvTitle)
        private val txtReleaseDate: TextView = itemView.findViewById(R.id.tvTvReleaseDate)
        private val txtRating: TextView = itemView.findViewById(R.id.tvTvRating)
        private val imgPoster: ImageView = itemView.findViewById(R.id.ivTvPoster)

        fun bind(tvShow: TvShowFavorite){
            txtTitle.text = tvShow.title
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            val date: Date = formatter.parse(tvShow.releaseDate)
            val releaseDate = dateFormat.format(date).toString()
            txtReleaseDate.text = releaseDate
            txtRating.text = tvShow.rating

            Glide.with(context)
                    .load(tvShow.photoHigh)
                    .fallback(R.drawable.no_image_available)
                    .thumbnail(
                            Glide.with(context)
                                    .load(tvShow.photoLow)
                    )
                    .into(imgPoster)
        }
    }

}