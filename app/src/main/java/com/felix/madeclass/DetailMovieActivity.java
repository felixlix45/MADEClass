package com.felix.madeclass;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.felix.madeclass.model.Movie;

public class DetailMovieActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE = "extra_movie";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        TextView txtTitle, txtOverview, txtRating, txtReleaseDate;
        ImageView imgPoster;

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitle = findViewById(R.id.tvMovieTitle);
        txtOverview = findViewById(R.id.tvMovieOverview);
        txtRating = findViewById(R.id.tvMovieRating);
        txtReleaseDate = findViewById(R.id.tvMovieReleaseDate);
        imgPoster = findViewById(R.id.ivMoviePoster);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String title = movie.getTitle();
        String overview = movie.getOverview();
        String rating  = movie.getRating();
        String releaseDate = movie.getRelease_date();
        int poster = movie.getPhoto();

        txtTitle.setText(title);
        txtOverview.setText(overview);
        txtRating.setText(rating);
        txtReleaseDate.setText(releaseDate);
        imgPoster.setImageResource(poster);

        getSupportActionBar().setTitle(title);
    }
}
