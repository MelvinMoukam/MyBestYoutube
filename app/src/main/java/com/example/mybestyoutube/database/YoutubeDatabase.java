package com.example.mybestyoutube.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mybestyoutube.dao.YoutubeDao;
import com.example.mybestyoutube.pojos.YoutubeVideo;

@Database(entities = {YoutubeVideo.class}, version = 1)
public abstract class YoutubeDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "youtubevideo";

    public static YoutubeDatabase getDb(Context context) {
        return Room.databaseBuilder(context, YoutubeDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
    }

    public abstract YoutubeDao youtubeDao();

}