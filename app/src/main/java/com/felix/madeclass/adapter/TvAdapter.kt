package com.felix.madeclass.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.felix.madeclass.DetailTvShowActivity
import com.felix.madeclass.R
import com.felix.madeclass.model.TvShow
import java.util.*

class TvAdapter(private val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<TvAdapter.ViewHolder>() {


    private val listTv = ArrayList<TvShow>()

    fun setData(tv: ArrayList<TvShow>) {
        listTv.clear()
        listTv.addAll(tv)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_tv, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(listTv[i])

        viewHolder.parentLayout.setOnClickListener { v ->
            val tvShow = TvShow()

            tvShow.title = listTv[viewHolder.adapterPosition].title
            tvShow.overview = listTv[viewHolder.adapterPosition].overview
            tvShow.rating = listTv[viewHolder.adapterPosition].rating
            tvShow.releaseDate = listTv[viewHolder.adapterPosition].releaseDate
            tvShow.photoHigh = listTv[viewHolder.adapterPosition].photoHigh
            tvShow.photoLow = listTv[viewHolder.adapterPosition].photoLow
            tvShow.photoBackdropHigh = listTv[viewHolder.adapterPosition].photoBackdropHigh
            tvShow.photoBackdropLow = listTv[viewHolder.adapterPosition].photoBackdropLow

            val toDetail = Intent(v.context, DetailTvShowActivity::class.java)
            toDetail.putExtra("extra_tv", tvShow)
            v.context.startActivity(toDetail)
        }

    }

    override fun getItemCount(): Int {
        return listTv.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        private val txtTitle: TextView = itemView.findViewById(R.id.tvTvTitle)
        private val txtRating: TextView = itemView.findViewById(R.id.tvTvRating)
        private val txtReleaseDate: TextView = itemView.findViewById(R.id.tvTvReleaseDate)
        private val imgPoster: ImageView = itemView.findViewById(R.id.ivTvPoster)
        internal val parentLayout: LinearLayout = itemView.findViewById(R.id.lvParentTv)

        fun bind(tvShow: TvShow) {
            txtTitle.text = tvShow.title
            txtReleaseDate.text = tvShow.releaseDate
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
