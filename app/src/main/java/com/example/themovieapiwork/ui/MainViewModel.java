package com.example.themovieapiwork.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import androidx.annotation.NonNull;

import com.example.themovieapiwork.database.MovieRepository;
import com.example.themovieapiwork.model.Movie.Movie;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MovieRepository mRepository;
    private LiveData<List<Movie>> mAllMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
    }

    LiveData<List<Movie>> loadAllMovies(String sort, String apiKey) {
        return mAllMovies = mRepository.getMoviesFromNetwork(sort, apiKey);
    }

    LiveData<List<Movie>> getFavMovies() {
        return mRepository.getFavMovies();
    }

    public LiveData<Movie> loadFavById(int id) {
        return mRepository.getMovieById(id);
    }
}
