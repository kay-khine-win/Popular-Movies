package com.example.popularmovies2;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

private LiveData<List<Movie>> movies;
private static final String TAG = MainViewModel.class.getSimpleName();


    public MainViewModel(Application application) {
        super(application);

        MovieRoomDatabase database = MovieRoomDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        movies = database.movieDao().getAllMovies();
    }

    public LiveData<List<Movie>> getTasks() {
        return movies;
    }
}
