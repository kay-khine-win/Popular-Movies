package com.example.popularmovies2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.ListItemClickListener, FavoriteMovieAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static int MOVIE_LOADER = 0;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String MOVIE_REQUEST_URL = "https://api.themoviedb.org/3/movie/popular?api_key=";
    //public static final String MOVIE_REQUEST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private static final String MOVIE_REQUEST_URL1 = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private FavoriteMovieAdapter fmovieAdapter;

    private ArrayList<Movie> movies;
    private LiveData<List<Movie>> moviesDB;
    GridLayoutManager layoutManager;
    LoaderManager loaderManager;

    private MovieRoomDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "On Create");

        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mDb = MovieRoomDatabase.getInstance(getApplicationContext());

        if (isOnline()) {

            layoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);

            if(MOVIE_LOADER == 2){
                setupViewModel();


            } else {
                loaderManager = getSupportLoaderManager();
                loaderManager.initLoader(MOVIE_LOADER, null, this);
            }

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(LOG_TAG, "On Create Loader");

        if (id == 0) {
            return new MovieLoader(this, MOVIE_REQUEST_URL);

        }
        else {
            return new MovieLoader(this, MOVIE_REQUEST_URL1);

        }


    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        Log.d(LOG_TAG, "On Load Finished");

        if (data != null && !data.isEmpty()) {
            movies = data;
            movieAdapter = new MovieAdapter(this);

            movieAdapter.setMovieData(data);
            mRecyclerView.setAdapter(movieAdapter);

        } else {
            Log.d(LOG_TAG, "On Finish Loader No Data");

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {

    }


    @Override
    public void onListItemClickFav(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailsActivity.class);
        if(moviesDB == null){
            Log.d(TAG, "NULL MOVIE ON CLICK FAV");

        }else {
            intent.putExtra("KEY", moviesDB.getValue().get(clickedItemIndex));
            startActivity(intent);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailsActivity.class);
        if(movies == null){
            Log.d(TAG, "NULL MOVIE ON CLICK POPULAR");

        }else {
            intent.putExtra("KEY", movies.get(clickedItemIndex));
            startActivity(intent);
        }
    }


    private LiveData<List<Movie>> setupViewModel() {
    fmovieAdapter = new FavoriteMovieAdapter(this);
    Log.d(TAG, "Actively retrieving the movies from the DataBase");
    MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
   // LiveData<List<Movie>> moviesDB = viewModel.getTasks();
    moviesDB = viewModel.getTasks();

        viewModel.getTasks().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesDB) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                fmovieAdapter.setMovieData(moviesDB);
                mRecyclerView.setAdapter(fmovieAdapter);

            }
        });


    return moviesDB;
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular_item:
                MOVIE_LOADER =0;
                LoaderManager loaderManager = getSupportLoaderManager();
                loaderManager.initLoader(MOVIE_LOADER, null, this);
                return true;
            case R.id.highest_rated_item:
                Log.d(LOG_TAG, "On Option 2");
                MOVIE_LOADER =1;
                LoaderManager loaderManager1 = getSupportLoaderManager();
                loaderManager1.initLoader(MOVIE_LOADER, null, this);
                return true;
            case R.id.favorite_item:
                Log.d(LOG_TAG, "On Option 3");
                MOVIE_LOADER =2;
                setupViewModel();
            return true;
            default:
        }
        return super.onOptionsItemSelected(item);
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
