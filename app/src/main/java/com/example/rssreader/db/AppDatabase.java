package com.example.rssreader.db;

import com.example.rssreader.dao.CategoryDao;
import com.example.rssreader.dao.ChannelDao;
import com.example.rssreader.db.converter.DateConverter;
import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.entities.ChannelData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters(DateConverter.class)
@Database(entities = {CategoryData.class, ChannelData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract ChannelDao channelDao();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
}
