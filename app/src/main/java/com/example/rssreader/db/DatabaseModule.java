package com.example.rssreader.db;

import android.content.Context;

import com.example.rssreader.dao.CategoryDao;
import com.example.rssreader.dao.ChannelDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {

    @Singleton
    @Provides
    CategoryDao provideCategoryDao(AppDatabase database) {
        return database.categoryDao();
    }

    @Singleton
    @Provides
    ChannelDao provideChannelDao(AppDatabase database) {
        return database.channelDao();
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "RssReader").build();
    }
}
