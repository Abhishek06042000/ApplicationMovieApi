package com.example.themovieapiwork.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import androidx.annotation.NonNull;

import com.example.themovieapiwork.database.MovieRepository;
import com.example.themovieapiwork.model.Movie.Movie;
import com.example.themovieapiwork.model.Review.Review;
import com.example.themovieapiwork.model.Trailer.Trailer;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private MovieRepository mRepository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
    }

    public LiveData<List<Review>> getReviews(int id, String apiKey) {
        return mRepository.getReviews(id, apiKey);
    }

    public LiveData<List<Trailer>> getTrailers(int id, String apiKey) {
        return mRepository.getTrailers(id, apiKey);
    }

    public void saveMovie(Movie movie) {
        mRepository.insert(movie);
    }

    public void deleteMovie(Movie movie) {
        mRepository.delete(movie);
    }

    public LiveData<Movie> loadFavById(int id) {
        return mRepository.getMovieById(id);
    }
}
