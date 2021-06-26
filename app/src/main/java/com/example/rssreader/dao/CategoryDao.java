package com.example.rssreader.dao;

import com.example.rssreader.db.AppDatabase;
import com.example.rssreader.entities.CategoryData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public abstract class CategoryDao {
    private final AppDatabase database;

    CategoryDao(AppDatabase database) {
        this.database = database;
    }

    @Query("SELECT * FROM category")
    public abstract Flowable<List<CategoryData>> selectAll();

    @Query("SELECT * FROM category WHERE category_id == (:id)")
    public abstract Flowable<CategoryData> loadAllById(int id);

    @Insert
    public abstract void insertAll(CategoryData... data);

    @Delete
    protected abstract void deleteInternal(CategoryData data);

    @Transaction
    public void delete(CategoryData data) {
        deleteInternal(data);
        database.channelDao().delete(data.id);
    }

    @Transaction
    public void delete(int categoryId) {
        deleteInternal(categoryId);
        database.channelDao().delete(categoryId);
    }

    @Update
    public abstract void update(CategoryData data);

    @Query("DELETE FROM category WHERE category_id == (:categoryId)")
    protected abstract void deleteInternal(int categoryId);
}
