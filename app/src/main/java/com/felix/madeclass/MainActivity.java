package com.felix.madeclass;

import android.content.Intent;
import android.content.res.TypedArray;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.felix.madeclass.adapter.MovieAdapter;
import com.felix.madeclass.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()){
                case R.id.title_movies:
                    fragment = new MoviesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.title_tv_show:
                    fragment = new TvFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.change_language){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        Fragment fragment;
        fragment = new MoviesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment , fragment.getClass().getSimpleName()).commit();

    }


}
