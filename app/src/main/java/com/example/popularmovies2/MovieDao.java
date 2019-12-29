package com.example.popularmovies2;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * from movie_table ORDER BY id ASC")
    LiveData<List<Movie>> getAllMovies();

    @Query("DELETE FROM movie_table")
    void deleteAll();

    @Insert
    void insert(Movie movie);

    @Delete
    void deleteTask(Movie movie);

    @Query("SELECT * FROM movie_table WHERE id = :id")
    Movie loadMoviekById(int id);


}
