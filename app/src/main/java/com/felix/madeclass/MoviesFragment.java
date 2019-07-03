package com.felix.madeclass;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.felix.madeclass.adapter.MovieAdapter;
import com.felix.madeclass.model.Movie;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {
    private String[] movieTitle;
    private String[] movieRating;
    private String[] movieReleaseDate;
    private String[] movieOverview;
    private TypedArray moviePoster;
    private RecyclerView rvMovie;
    private ArrayList<Movie> movies;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_movies, container, false);
        rvMovie = v.findViewById(R.id.rvMovie);

        prepare();
        addItem();

        showRecyclerList();
        return v;
    }

    private void showRecyclerList() {
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieAdapter movieAdapter = new MovieAdapter(getActivity(), movies);
        rvMovie.setAdapter(movieAdapter);
    }

    private void addItem(){
        movies = new ArrayList<>();

        for(int i = 0; i < movieTitle.length; i++){
            Movie movie = new Movie();
            movie.setTitle(movieTitle[i]);
            movie.setRating(movieRating[i]);
            movie.setRelease_date(movieReleaseDate[i]);
            movie.setPhoto(moviePoster.getResourceId(i, -1));
            movie.setOverview(movieOverview[i]);
            movies.add(movie);
        }

    }

    private void prepare(){
        movieTitle = getResources().getStringArray(R.array.movieTitle);
        movieRating = getResources().getStringArray(R.array.movieRating);
        movieReleaseDate = getResources().getStringArray(R.array.movieReleaseDate);
        moviePoster = getResources().obtainTypedArray(R.array.moviePoster);
        movieOverview = getResources().getStringArray(R.array.movieOverview);
    }
}
