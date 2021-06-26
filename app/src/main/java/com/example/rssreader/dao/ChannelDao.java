package com.example.rssreader.dao;

import com.example.rssreader.db.AppDatabase;
import com.example.rssreader.entities.ChannelData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Qualifier;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ChannelDao {

    @Query("SELECT * FROM channel")
    Flowable<List<ChannelData>> selectAll();

    @Query("SELECT * FROM channel WHERE category_id == (:categoryId)")
    Flowable<List<ChannelData>> selectAllByCategory(int categoryId);

    @Insert
    void insert(ChannelData... data);

    @Delete
    void delete(ChannelData data);

    @Query("DELETE FROM channel WHERE category_id == (:cateoryId)")
    void delete(int cateoryId);

    @Update
    void update(ChannelData value);

    @Query("SELECT * FROM channel WHERE channel_id == (:id)")
    Flowable<ChannelData> select(int id);
}
