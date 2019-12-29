package com.example.popularmovies2;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
    private static MovieRoomDatabase INSTANCE;
    private static final String LOG_TAG = MovieRoomDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "movie";

    static MovieRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                Log.d(LOG_TAG, "Creating new database instance");

                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();

            }
        }
        else {
            Log.d(LOG_TAG, "INSTance is null");

        }
        Log.d(LOG_TAG, "Getting the database instance");
        return INSTANCE;
    }

}
