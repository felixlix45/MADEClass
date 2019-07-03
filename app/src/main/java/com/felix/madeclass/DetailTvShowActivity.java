package com.felix.madeclass;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.felix.madeclass.model.TvShow;

public class DetailTvShowActivity extends AppCompatActivity {

    private static final String EXTRA_TV = "extra_tv";

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
        setContentView(R.layout.activity_detail_tv_show);

        TextView txtTitle, txtOverview, txtRating, txtReleaseDate;
        ImageView imgPoster;

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitle = findViewById(R.id.tvTvTitle);
        txtOverview = findViewById(R.id.tvTvOverview);
        txtRating = findViewById(R.id.tvTvRating);
        txtReleaseDate = findViewById(R.id.tvTvReleaseDate);
        imgPoster = findViewById(R.id.ivTvPoster);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV);
        String title = tvShow.getTitle();
        String overview = tvShow.getOverview();
        String rating  = tvShow.getRating();
        String releaseDate = tvShow.getRelease_date();
        int poster = tvShow.getPhoto();

        txtTitle.setText(title);
        txtOverview.setText(overview);
        txtRating.setText(rating);
        txtReleaseDate.setText(releaseDate);
        imgPoster.setImageResource(poster);

        getSupportActionBar().setTitle(title);
    }
}
