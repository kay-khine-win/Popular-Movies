package com.example.popularmovies2;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    public static final String LOG_TAG = MovieLoader.class.getSimpleName();

    private String mQueryString;
    LiveData<List<Movie>> liveMovies;
    ArrayList<Movie> mm;
    private MovieRoomDatabase mDb;


    public MovieLoader(@NonNull Context context, String mQueryString) {
        super(context);
        this.mQueryString = mQueryString;
        Log.d(LOG_TAG, "ON Loader Constructor");

    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
        Log.d(LOG_TAG, "On Start Loading");

    }

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
            ArrayList<Movie> movies = Utils.fetchMovieData(mQueryString);
            Log.d("Load in Background", String.valueOf(movies.size()));
            return movies;

        }
    }



