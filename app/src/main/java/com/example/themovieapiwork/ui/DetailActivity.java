package com.example.themovieapiwork.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;
import com.example.themovieapiwork.R;
import com.example.themovieapiwork.model.Movie.Movie;
import com.example.themovieapiwork.model.Review.Review;
import com.example.themovieapiwork.model.Trailer.Trailer;
import com.example.themovieapiwork.apis.ApiConstants;
import com.example.themovieapiwork.ui.adapter.ReviewAdapter;
import com.example.themovieapiwork.ui.adapter.TrailerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ItemClickListener {

    public final static String DETAIL_MOVIE = "detailMovie";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.release_date)
    TextView dateTv;
    @BindView(R.id.overview)
    TextView overviewTv;
    @BindView(R.id.main_backdrop)
    ImageView backdropIv;
    @BindView(R.id.poster)
    ImageView posterIv;
    @BindView(R.id.movie_rating)
    RatingBar ratingBar;
    @BindView(R.id.review_rv)
    RecyclerView reviewView;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.trailer_rv)
    RecyclerView trailerView;
    @BindView(R.id.detail_coordinator)
    CoordinatorLayout coordinatorLayout;
    String title, date, overview, backdrop, poster;
    Movie movie;
    double rating;
    private LikeButton likeButton;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private String apiKey = ApiConstants.API_KEY;
    private DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("");
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        if (intent.hasExtra(DETAIL_MOVIE)) {
            movie = getIntent().getParcelableExtra(DETAIL_MOVIE);
            populateUI(movie);
        }

        initViews();
        loadTrailers();
        loadReviews();
        checkIfFavorite();

    }

    private void initViews() {
        likeButton = findViewById(R.id.fav_button);
        addFavorite();
        reviewView.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter = new ReviewAdapter(this);
        reviewView.setAdapter(reviewAdapter);
        trailerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailerAdapter = new TrailerAdapter(this, this);
        trailerView.setAdapter(trailerAdapter);
        trailerView.setHasFixedSize(false);
    }

    private void addFavorite() {
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                detailViewModel.saveMovie(movie);
                Snackbar.make(coordinatorLayout, "Added " + movie.getOriginalTitle() + " to favorites", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                detailViewModel.deleteMovie(movie);
                Snackbar.make(coordinatorLayout, "Removed " + movie.getOriginalTitle() + " from favorites", Snackbar.LENGTH_LONG).show();
            }

        });
    }

    void checkIfFavorite() {
        int id = movie.getId();
        detailViewModel.loadFavById(id).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if (movie == null) {
                    likeButton.setLiked(false);
                } else {
                    likeButton.setLiked(true);
                }
            }
        });
    }

    void populateUI(Movie movie) {
        title = movie.getOriginalTitle();
        date = movie.getReleaseDate();
        overview = movie.getOverview();
        poster = movie.getMovieImagePath();
        backdrop = movie.getBackdropPath();
        rating = movie.getVoteAverage();
        //set movie detail on respective views
        titleTv.setText(title);
        dateTv.setText(date);
        overviewTv.setText(overview);
        //Load image
        String posterUrl = ApiConstants.MOVIES_DETAIL_BASE_URL;
        String backdropUrl = ApiConstants.MOVIES_BACKDROP_BASE_URL;
        //call for imageload
        loadImage(posterIv, poster, posterUrl);
        loadImage(backdropIv, backdrop, backdropUrl);
        //Convert out of 10 rating into out of 5
        float rated = (((float) rating) / 2);
        ratingBar.setRating(rated);
        setTitle(movie.getOriginalTitle());
    }

    private void loadImage(ImageView imageView, String imageUrl, String posterUrl) {
        Picasso.get()
                .load(posterUrl + imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    private void loadTrailers() {
        detailViewModel.getTrailers(movie.getId(), apiKey).observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                trailerAdapter.setTrailer(trailers);
            }
        });
    }

    private void loadReviews() {
        detailViewModel.getReviews(movie.getId(), apiKey).observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });
    }

    @Override
    public void onItemClick(Trailer trailer) {
        final String url = "https://www.youtube.com/watch?v=";
        final String key = trailer.getKey();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url + key));
        startActivity(intent);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.movie_data_unavailable, Toast.LENGTH_SHORT).show();
    }

}