package com.example.themovieapiwork.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.themovieapiwork.model.Movie.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDb extends RoomDatabase {
    private static final String DB_NAME = "favorite_db";
    private static MovieDb sInstance;

    static MovieDb getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (MovieDb.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDb.class, DB_NAME).build();
                }

            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
