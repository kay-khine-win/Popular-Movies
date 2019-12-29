package com.example.popularmovies2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity
    implements FavoriteMovieAdapter.ListItemClickListener
    {
        @Override
        public void onListItemClickFav(int clickedItemIndex) {

        }

        private static final String TAG = MainActivity.class.getSimpleName();

        public static final String LOG_TAG = MainActivity.class.getSimpleName();

        private RecyclerView mRecyclerView;
        private FavoriteMovieAdapter fmovieAdapter;

        GridLayoutManager layoutManager;
        private MovieRoomDatabase mDb;

        LiveData<List<Movie>> movieFromDB;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d(LOG_TAG, "On Create");

            setContentView(R.layout.activity_main);
            mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
            mDb = MovieRoomDatabase.getInstance(getApplicationContext());


                layoutManager = new GridLayoutManager(this, 2);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);



        }

        @Override
        protected void onResume() {
            super.onResume();

        }




        private LiveData<List<Movie>> retrieveTasks() {
            fmovieAdapter = new FavoriteMovieAdapter(this);

            Log.d(TAG, "Actively retrieving the movies from the DataBase");
            final LiveData<List<Movie>> movies = mDb.movieDao().getAllMovies();
            if(movies == null){
                Log.d(TAG, "NULL LiveData");
            }
            else{
                Log.d(TAG, "NOT NULL");
                movies.observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        Log.d(TAG, "Receiving database update from LiveData");
                        fmovieAdapter.setMovieData(movies);
                        mRecyclerView.setAdapter(fmovieAdapter);

                    }
                });
            }
            return movies;

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.option_menu, menu);
            return true;
        }


        public boolean isOnline() {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                int exitValue = ipProcess.waitFor();
                return (exitValue == 0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return false;
        }
    }
