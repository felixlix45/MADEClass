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

import com.felix.madeclass.DetailMovieActivity;
import com.felix.madeclass.R;
import com.felix.madeclass.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> listMovie;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    public ArrayList<Movie> getListMovie(){
        return listMovie;
    }

    public MovieAdapter(Context context, ArrayList<Movie> listMovie) {
        this.context = context;
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.bind(listMovie.get(i));

        viewHolder.layoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = new Movie();
                movie.setTitle(listMovie.get(viewHolder.getAdapterPosition()).getTitle());
                movie.setOverview(listMovie.get(viewHolder.getAdapterPosition()).getOverview());
                movie.setRating(listMovie.get(viewHolder.getAdapterPosition()).getRating());
                movie.setRelease_date(listMovie.get(viewHolder.getAdapterPosition()).getRelease_date());
                movie.setPhoto(listMovie.get(viewHolder.getAdapterPosition()).getPhoto());
                Intent toDetail = new Intent(v.getContext(), DetailMovieActivity.class);
                toDetail.putExtra("extra_movie", movie);
                v.getContext().startActivity(toDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle, txtRating, txtReleaseDate;
        private ImageView imgPoster;
        private LinearLayout layoutParent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRating = itemView.findViewById(R.id.tvMovieRating);
            txtTitle = itemView.findViewById(R.id.tvMovieTitle);
            txtReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            imgPoster = itemView.findViewById(R.id.ivMoviePoster);
            layoutParent = itemView.findViewById(R.id.lvParentMovie);

        }

        public void bind(Movie movie){
            txtTitle.setText(movie.getTitle());
            txtRating.setText(movie.getRating());
            txtReleaseDate.setText(movie.getRelease_date());
            imgPoster.setImageResource(movie.getPhoto());

        }
    }
}
