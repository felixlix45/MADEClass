package com.felix.madeclass;

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
import com.felix.madeclass.adapter.TvAdapter;
import com.felix.madeclass.model.Movie;
import com.felix.madeclass.model.TvShow;

import java.util.ArrayList;

public class TvFragment extends Fragment {

    private String[] tvTitle;
    private String[] tvRating;
    private String[] tvReleaseDate;
    private String[] tvOverview;
    private TypedArray tvPoster;
    private RecyclerView rvTV;
    private ArrayList<TvShow> tvList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tv, container, false);

        rvTV = v.findViewById(R.id.rvTV);

        prepare();
        addItem();

        showRecyclerList();

        return v;
    }

    private void showRecyclerList() {
        rvTV.setLayoutManager(new LinearLayoutManager(getActivity()));
        TvAdapter tvAdapter = new TvAdapter(getActivity(), tvList);
        rvTV.setAdapter(tvAdapter);
    }

    private void addItem(){
        tvList = new ArrayList<>();

        for(int i = 0; i < tvTitle.length; i++){
            TvShow tvShow = new TvShow();
            tvShow.setTitle(tvTitle[i]);
            tvShow.setRating(tvRating[i]);
            tvShow.setRelease_date(tvReleaseDate[i]);
            tvShow.setPhoto(tvPoster.getResourceId(i, -1));
            tvShow.setOverview(tvOverview[i]);
            tvList.add(tvShow);
        }

    }

    private void prepare(){
        tvTitle = getResources().getStringArray(R.array.tvTitle);
        tvRating = getResources().getStringArray(R.array.tvRating);
        tvReleaseDate = getResources().getStringArray(R.array.tvPremiere);
        tvPoster = getResources().obtainTypedArray(R.array.tvPoster);
        tvOverview = getResources().getStringArray(R.array.tvOverview);
    }
}
