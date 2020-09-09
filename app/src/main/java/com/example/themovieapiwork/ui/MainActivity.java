package com.example.themovieapiwork.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieapiwork.R;
import com.example.themovieapiwork.model.Movie.Movie;
import com.example.themovieapiwork.apis.ApiConstants;
import com.example.themovieapiwork.ui.adapter.MovieAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String PREF = "pref";
    private MovieAdapter movieAdapter;
    private SharedPreferences preferences;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private String popular = ApiConstants.POPULAR_MOVIES;
    private String topRated = ApiConstants.TOP_RATED;
    @BindView(R.id.movieRecyclerView)
    RecyclerView movieRecyclerView;

    private String apikey = ApiConstants.API_KEY;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    private MainViewModel mainViewModel;
    public final static String LIST_STATE_KEY = "recycler_list_state";
    private Parcelable listState;
    private GridLayoutManager gridLayoutManager;
    boolean doubleBackToExitPressedOnce = false;
    ChipNavigationBar buttomNavgition;
    int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews(savedInstanceState);
        checkOrientation();

        buttomNavgition = findViewById(R.id.bottom_nvg);

        if(number == 1 || number == 0)
            buttomNavgition.setItemSelected(R.id.movie_home,true);
        else if(number == 2)
            buttomNavgition.setItemSelected(R.id.top,true);
        else if(number == 3)
            buttomNavgition.setItemSelected(R.id.fav,true);
        else
            buttomNavgition.setItemSelected(R.id.more,true);

        buttomNavgition.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.movie_home:
                        //Toast.makeText(MainActivity.this, "Popular CLick", Toast.LENGTH_SHORT).show();
                        setKey(getString(R.string.popular));
                        break;

                    case R.id.top:
                        //Toast.makeText(MainActivity.this, "Top CLick", Toast.LENGTH_SHORT).show();
                        setKey(getString(R.string.top_rated));
                        return;

                    case R.id.fav:
                        //Toast.makeText(MainActivity.this, "Fav CLick", Toast.LENGTH_SHORT).show();
                        setKey("favorites");
                        return;

                    case R.id.more:
                        Toast.makeText(MainActivity.this, "Yeah!!\n The End", Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        });
    }

    private void checkOrientation() {
        if (this.movieRecyclerView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(this, 2);
            movieRecyclerView.setLayoutManager(gridLayoutManager);
        } else if (this.movieRecyclerView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(this, 4);
            movieRecyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    private void initViews(Bundle s) {
        gridLayoutManager = new GridLayoutManager(this, 2);
        movieRecyclerView.setLayoutManager(gridLayoutManager);
        movieRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(getApplicationContext(), this);
        //setSupportActionBar(toolbar);
        movieRecyclerView.setAdapter(movieAdapter);
        preferences = getApplicationContext().getSharedPreferences(PREF, Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(this);
        if (isConnected()) {
            onSharedPreferenceChanged(preferences, getString(R.string.sort_by));
        } else {
            Snackbar.make(coordinatorLayout, "Check your network connection", Snackbar.LENGTH_LONG).show();
            //toolbar.setTitle("Favorite movies");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LIST_STATE_KEY, gridLayoutManager.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
    }

    // press back twice to exit
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        setKey(getString(R.string.popular));
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.double_press_back, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    private void loadMovies(String sort, String apiKey) {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.loadAllMovies(sort, apiKey).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieAdapter.setMovieItem(movies);
                if (listState != null) {
                    gridLayoutManager.onRestoreInstanceState(listState);
                }
            }
        });
    }

    private void loadFavorites() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getFavMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieAdapter.setMovieItem(movies);
                progressBar.setVisibility(View.GONE);
                if (listState != null) {
                    gridLayoutManager.onRestoreInstanceState(listState);
                }
            }
        });
    }

    private void getPreference() {
        String key = getOrderValue();
        if (key.equals(getString(R.string.popular))) {
            number = 1;
            loadMovies(popular, apikey);
        } else if (key.equals(getString(R.string.top_rated))) {
            number = 2;
            loadMovies(topRated, apikey);
        } else if(key.equals(getString(R.string.favorite))){
            number = 3;
            loadFavorites();
        }else{
            number = 4;
            Toast.makeText(this, "Error Preference", Toast.LENGTH_SHORT).show();
        }

    }

    private String getOrderValue() {
        String key = getString(R.string.sort_by);
        String value = getString(R.string.popular);
        return preferences.getString(key, value);
    }

    private void setKey(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.sort_by), value);
        editor.apply();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.sort_by)))
            getPreference();
    }

    @Override
    public void onListClick(Movie movie) {
        Intent movieIntent = new Intent(this.getApplicationContext(), DetailActivity.class);
        movieIntent.putExtra(DetailActivity.DETAIL_MOVIE, movie);
        startActivity(movieIntent);
    }

    @Override
    protected void onResume() {
        if (!isConnected()) {
            loadFavorites();
        } else {
            onSharedPreferenceChanged(preferences, getString(R.string.sort_by));
        }
        super.onResume();
    }
}
