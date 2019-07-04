package com.felix.madeclass.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.felix.madeclass.DetailTvShowActivity;
import com.felix.madeclass.R;
import com.felix.madeclass.model.TvShow;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TvShow> listTv;

    public TvAdapter(Context context, ArrayList<TvShow> listTv) {
        this.context = context;
        this.listTv = listTv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tv, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.bind(listTv.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TvShow tvShow = new TvShow();
                tvShow.setTitle(listTv.get(viewHolder.getAdapterPosition()).getTitle());
                tvShow.setOverview(listTv.get(viewHolder.getAdapterPosition()).getOverview());
                tvShow.setRating(listTv.get(viewHolder.getAdapterPosition()).getRating());
                tvShow.setRelease_date(listTv.get(viewHolder.getAdapterPosition()).getRelease_date());
                tvShow.setPhoto(listTv.get(viewHolder.getAdapterPosition()).getPhoto());
                Intent toDetail = new Intent(v.getContext(), DetailTvShowActivity.class);
                toDetail.putExtra("extra_tv", tvShow);
                v.getContext().startActivity(toDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle, txtRating, txtReleaseDate;
        private ImageView imgPoster;
        private LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRating = itemView.findViewById(R.id.tvTvRating);
            txtReleaseDate = itemView.findViewById(R.id.tvTvReleaseDate);
            txtTitle = itemView.findViewById(R.id.tvTvTitle);
            imgPoster = itemView.findViewById(R.id.ivTvPoster);
            parentLayout = itemView.findViewById(R.id.lvParentTv);
        }

        public void bind(TvShow tvShow){
           txtTitle.setText(tvShow.getTitle());
           txtReleaseDate.setText(tvShow.getRelease_date());
           txtRating.setText(tvShow.getRating());
           imgPoster.setImageResource(tvShow.getPhoto());
        }
    }
}
